/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *****************************************************************************/
package org.zdevra.guice.mvc;

import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import org.zdevra.guice.mvc.ConversionService.ConverterFactory;
import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.converters.*;
import org.zdevra.guice.mvc.parameters.*;
import org.zdevra.guice.mvc.security.ResourceDeniedException;
import org.zdevra.guice.mvc.security.WebPrincipalProvider;
import org.zdevra.guice.mvc.security.internal.*;
import org.zdevra.guice.mvc.views.NamedViewScanner;
import org.zdevra.guice.mvc.views.RedirectViewScanner;

import javax.servlet.http.HttpServlet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <i>MVC module</i> for GUICE.
 * <p>
 *
 * If you are fammiliar with the GUICE servlets, then usage of the Lime MVC is pretty straight forward.
 * MvcModule is basically extended version of Guice's ServletModule and you can use all ServletModule's
 * methods in configureControllers() method implementation (like serve() etc..).
 * <p>
 *
 * In your web application, put new MvcModule with implemented configureControllers() method
 * into GuiceServletContextListener implementation.
 *
 * <p>example:
 * <pre class="prettyprint">
 * public class WebAppConfiguration extends GuiceServletContextListener {
 * ...
 *   protected Injector getInjector() {
 *     Injector injector =  Guice.createInjector(
 *        new MvcModule() {
 *           protected void configureControllers() {
 *
 *             control("/someController/*")
 *                .withController(SomeController.class)
 *              ...
 *           }
 *        }
 *     );
 *     return injector;
 *   }
 * }
 * </pre>
 * Example shows the basic usage and registers the simple controller class.
 * All requests starting with '/someController/' will be processed by the SomeController.
 *
 * <p>
 * It is possible to have more controllers for one URL registration and then all controllers
 * will be invoked. This is good when you have diplayed several independent informations in the view.
 *
 * <p>example:
 * <pre class="prettyprint">
 * public class WebAppConfiguration extends GuiceServletContextListener {
 * ...
 *   protected Injector getInjector() {
 *     Injector injector =  Guice.createInjector(
 *        new MvcModule() {
 *           protected void configureControllers() {
 *
 *             control("/someControllers/*")
 *                .withController(SomeController.class)
 *                .withController(AnotherController.class);
 *              ...
 *           }
 *        }
 *     );
 *     return injector;
 *   }
 * }
 * </pre>
 *
 * In both controllers can be defined the same path. Let's assume 2 controllers:
 * <p>
 * <pre class="prettyprint">
 * {@literal @}Controller
 * {@literal @}View("some_view")
 * public class SomeController {
 *    ...
 *    {@literal @}Path("/get") {@literal}Model("user")
 *    public User getUser() {
 *        ...
 *    }
 * }
 *
 * {@literal @}Controller
 * {@literal @}View("some_view")
 * public class AnotherController {
 *    ...
 *    {@literal @}Path("/get") {@literal}Model("goods")
 *    public List<Product> getGoods() {
 *        ...
 *    }
 * }
 * </pre>
 *
 * In that case, both methods 'getUser' and 'getGoods' will be invoked. Be carrefull when you're invoking 2 or more methods. It may
 * cause problems with multiple views when only first view is choosen.
 * <p>
 *
 * @see Controller
 * @see ModelMap
 * @see ModelAndView
 * @see ViewPoint
 * @see ViewResolver
 * @see ExceptionResolver
 * @see ViewScannerService
 *
 */
public abstract class MvcModule extends AbstractMvcModule {

// ------------------------------------------------------------------------
    private static final Logger logger = Logger.getLogger(MvcModule.class.getName());
    private List<HttpServlet> servlets;

// ------------------------------------------------------------------------
    /**
     * Put into this method your controllers configuration
     */
    protected abstract void configureControllers();

    /**
     * This method initializate controller servlets
     */
    @Override
    protected final void configureServlets() {

        install(new ConfiguringMvcModule());

        if (controllerModuleBuilder != null) {
            throw new IllegalStateException("Re-entry is not allowed.");
        }

        conversionServiceBuilder = new MultibinderBuilder<ConverterFactory>(binder(), ConverterFactory.class);
        webPrincipalProviderBuilder = new MultibinderBuilder<WebPrincipalProvider>(binder(), WebPrincipalProvider.class);
        controllerModuleBuilder = new ControllerModuleBuilder();
        exceptionResolverBuilder = new ExceptionResolverBuilder(binder());
        paramProcessorBuilder = new MultibinderBuilder<ParamProcessorFactory>(binder(), ParamProcessorFactory.class);
        viewScannerBuilder = new MultibinderBuilder<ViewScanner>(binder(), ViewScanner.class);
        interceptorHandlersBuilder = new MultibinderBuilder<InterceptorHandler>(binder(), InterceptorHandler.class);
        namedViewBudiler = new NamedViewBuilder(binder());
        servlets = new LinkedList<HttpServlet>();

        try {


            configureControllers();

            //register MVC controllers
            List<ServletDefinition> defs = controllerModuleBuilder.getControllerDefinitions();
            if (defs.isEmpty()) {
                logger.log(Level.WARNING, "None controller has been defined in the MVC module");
            }

            for (ServletDefinition def : defs) {
                String pattern = def.getUrlPattern();
                HttpServlet servlet = def.createServlet(binder());
                requestInjection(servlet);
                serve(pattern).with(servlet);
                servlets.add(servlet);
            }

        } finally {
            exceptionResolverBuilder = null;
            controllerModuleBuilder = null;
            viewScannerBuilder = null;
            paramProcessorBuilder = null;
            namedViewBudiler = null;
            conversionServiceBuilder = null;
            interceptorHandlersBuilder = null;
            webPrincipalProviderBuilder = null;
        }

    }

// ------------------------------------------------------------------------
    /**
     * This method can be used only for testing purpose
     */
    public final List<HttpServlet> getServlets() {
        return this.servlets;
    }

    // ------------------------------------------------------------------------
    public static interface ControllerBindingBuilder {

        public ControllerBindingBuilder withController(Class<?> controller);

        public ControllerBindingBuilder interceptor(Class<? extends InterceptorHandler> handlerClass);
    }

    public static interface ControllerAndViewBindingBuilder {

        public ControllerBindingBuilder withController(Class<?> controller);

        public void withView(String name);

        public void withView(ViewPoint viewInstance);
    }

    public static interface ExceptionResolverBindingBuilder {

        public void toHandler(Class<? extends ExceptionHandler> handlerClass);

        public void toHandlerInstance(ExceptionHandler handler);

        public void toErrorView(String viewName);

        public void toErrorView(ViewPoint errorView);
    }

    public static interface NamedViewBindingBuilder {

        public void toView(Class<? extends ViewPoint> viewCLass);

        public void toViewInstance(ViewPoint view);

        public void toJsp(String pathToFile);
    }
// ------------------------------------------------------------------------
}
