package org.zdevra.guice.mvc;

import com.google.inject.name.Names;
import org.zdevra.guice.mvc.converters.*;
import org.zdevra.guice.mvc.parameters.*;
import org.zdevra.guice.mvc.security.ResourceDeniedException;
import org.zdevra.guice.mvc.security.WebPrincipalProvider;
import org.zdevra.guice.mvc.security.internal.*;
import org.zdevra.guice.mvc.views.NamedViewScanner;
import org.zdevra.guice.mvc.views.RedirectViewScanner;

class ConfiguringMvcModule extends AbstractMvcModule {

    @Override
    protected final void configureServlets() {
        if (controllerModuleBuilder != null) {
            throw new IllegalStateException("Re-entry is not allowed.");
        }

        conversionServiceBuilder = new MultibinderBuilder<ConversionService.ConverterFactory>(binder(), ConversionService.ConverterFactory.class);
        webPrincipalProviderBuilder = new MultibinderBuilder<WebPrincipalProvider>(binder(), WebPrincipalProvider.class);
        controllerModuleBuilder = new ControllerModuleBuilder();
        exceptionResolverBuilder = new ExceptionResolverBuilder(binder());
        paramProcessorBuilder = new MultibinderBuilder<ParamProcessorFactory>(binder(), ParamProcessorFactory.class);
        viewScannerBuilder = new MultibinderBuilder<ViewScanner>(binder(), ViewScanner.class);
        interceptorHandlersBuilder = new MultibinderBuilder<InterceptorHandler>(binder(), InterceptorHandler.class);
        namedViewBudiler = new NamedViewBuilder(binder());

        try {


            registerWebPrincipalProvider(DefaultWebPrincipalProvider.class);
            bind(FinalWebPrincipalProvider.class).to(DefaultFinalWebPrincipalProvider.class);
            filter("/*").through(WebPrincipalFilter.class);
            bindException(ResourceDeniedException.class).toHandler(ResourceDeniedExceptionHandler.class);

            //default registrations
            bind(ViewResolver.class).to(DefaultViewResolver.class);

            bind(ExceptionResolver.class)
                    .to(GuiceExceptionResolver.class);

            bind(ExceptionHandler.class)
                    .annotatedWith(Names.named(ExceptionResolver.DEFAULT_EXCEPTIONHANDLER_NAME))
                    .to(DefaultExceptionHandler.class);

            bind(ConversionService.class).asEagerSingleton();
            registerConverter(new BooleanConverterFactory());
            registerConverter(new DateConverterFactory());
            registerConverter(new DoubleConverterFactory());
            registerConverter(new LongConverterFactory());
            registerConverter(new FloatConverterFactory());
            registerConverter(new IntegerConverterFactory());
            registerConverter(new StringConverterFactory());

            bind(ParamProcessorsService.class);
            registerParameterProc(HttpPostParam.Factory.class);
            registerParameterProc(RequestScopedAttributeParam.Factory.class);
            registerParameterProc(UriParam.Factory.class);
            registerParameterProc(SessionAttributeParam.Factory.class);
            registerParameterProc(ModelParam.Factory.class);
            registerParameterProc(RequestParam.Factory.class);
            registerParameterProc(WebPrincipalParam.Factory.class);
            registerParameterProc(ResponseParam.Factory.class);
            registerParameterProc(HttpSessionParam.Factory.class);
            registerParameterProc(InjectorParam.Factory.class);

            bind(InterceptorService.class).asEagerSingleton();

            bind(ViewScannerService.class);
            registerViewScanner(NamedViewScanner.class);
            registerViewScanner(RedirectViewScanner.class);


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

    @Override
    public boolean equals(Object o) {
        return o != null && this.getClass().equals(o.getClass());
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
