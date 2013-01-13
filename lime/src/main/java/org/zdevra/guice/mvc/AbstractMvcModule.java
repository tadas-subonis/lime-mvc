package org.zdevra.guice.mvc;

import com.google.inject.servlet.ServletModule;
import org.zdevra.guice.mvc.parameters.ParamProcessorFactory;
import org.zdevra.guice.mvc.security.WebPrincipalProvider;

abstract class AbstractMvcModule extends ServletModule {
    protected MultibinderBuilder<ConversionService.ConverterFactory> conversionServiceBuilder;
    protected ExceptionResolverBuilder exceptionResolverBuilder;
    protected ControllerModuleBuilder controllerModuleBuilder;
    protected MultibinderBuilder<ParamProcessorFactory> paramProcessorBuilder;
    protected MultibinderBuilder<ViewScanner> viewScannerBuilder;
    protected MultibinderBuilder<InterceptorHandler> interceptorHandlersBuilder;
    protected NamedViewBuilder namedViewBudiler;
    protected MultibinderBuilder<WebPrincipalProvider> webPrincipalProviderBuilder;

    /**
     * Method bind to view's name some view.
     */
    protected final MvcModule.NamedViewBindingBuilder bindViewName(String viewName) {
        return this.namedViewBudiler.bindViewName(viewName);
    }

    /**
     * The method registers a custom converter which converts strings to the
     * concrete types. These converters are used for conversions from a HTTP request
     * to the method's parameters.
     *
     * The all predefined default converters are placed in the 'converters' sub-package.
     */
    protected final void registerConverter(ConversionService.ConverterFactory converterFactory) {
        this.conversionServiceBuilder.registerInstance(converterFactory);
    }

    /**
     * The method registers a custom converter which converts strings to the
     * concrete types. These converters are used for conversions from a HTTP request
     * to the method's parameters.
     *
     * The all predefined default convertors are placed in the 'converters' sub-package.
     */
    protected final void registerConverter(Class<? extends ConversionService.ConverterFactory> convertorFactoryClazz) {
        this.conversionServiceBuilder.registerClass(convertorFactoryClazz);
    }

    /**
     * The method register into {@link org.zdevra.guice.mvc.ViewScannerService} a custom
     * view scanner as a class
     *
     * @see org.zdevra.guice.mvc.ViewScannerService
     */
    protected final void registerViewScanner(Class<? extends ViewScanner> scannerClass) {
        this.viewScannerBuilder.registerClass(scannerClass);
    }

    /**
     * The method register into {@link org.zdevra.guice.mvc.ViewScannerService} a custom
     * view scanner as a instance.
     *
     * @see org.zdevra.guice.mvc.ViewScannerService
     */
    protected final void registerViewScanner(ViewScanner scannerInstance) {
        this.viewScannerBuilder.registerInstance(scannerInstance);
    }

    /**
     * The method registers a custom parameter processor. The parameter processors
     * converts/prepares/fills the values into invoked method's parameters.
     * All predefined processors are placed in 'parameters' sub-package.
     *
     * @param paramProcFactory
     *
     * @see org.zdevra.guice.mvc.parameters.ParamProcessorFactory
     * @see org.zdevra.guice.mvc.parameters.ParamProcessor
     */
    protected final void registerParameterProc(Class<? extends ParamProcessorFactory> paramProcFactory) {
        paramProcessorBuilder.registerClass(paramProcFactory);
    }

    /**
     * Method registers a global interceptor class as a singleton. These global interceptors do pre/post
     * processing for every request/response.
     *
     *
     * @param interceptorHandlerClass
     */
    protected final void registerGlobalInterceptor(Class<? extends InterceptorHandler> interceptorHandlerClass) {
        interceptorHandlersBuilder.registerClass(interceptorHandlerClass);
    }

    /**
     * Method registers a global interceptor instance. These global interceptors do pre/post
     * processing for every request/response.
     *
     * @param interceptorHandlerInstance
     */
    protected final void registerGlobalInterceptorInstance(InterceptorHandler interceptorHandlerInstance) {
        interceptorHandlersBuilder.registerInstance(interceptorHandlerInstance);
    }

    /**
     * @param webPrincipalProvider
     */
    protected final void registerWebPrincipalProviderInstance(WebPrincipalProvider webPrincipalProvider) {
        webPrincipalProviderBuilder.registerInstance(webPrincipalProvider);
    }

    protected final void registerWebPrincipalProvider(Class<? extends WebPrincipalProvider> webPrincipalProvider) {
        webPrincipalProviderBuilder.registerClass(webPrincipalProvider);
    }

    /**
     * Method binds the exception handler to concrete exception type
     * @param exceptionClazz
     * @return
     */
    protected final MvcModule.ExceptionResolverBindingBuilder bindException(Class<? extends Throwable> exceptionClazz) {
        return this.exceptionResolverBuilder.bindException(exceptionClazz);
    }

    /**
     * Method bind controller class to the concrete url.
     * @param urlPattern
     * @return
     */
    protected final MvcModule.ControllerAndViewBindingBuilder control(String urlPattern) {
        return this.controllerModuleBuilder.control(urlPattern);
    }
}
