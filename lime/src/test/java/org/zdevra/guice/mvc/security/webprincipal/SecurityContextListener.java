package org.zdevra.guice.mvc.security.webprincipal;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.security.AuthController;
import org.zdevra.guice.mvc.security.MockSessionWebPrincipalProvider;
import org.zdevra.guice.mvc.security.SecurityConfig;

public class SecurityContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new MvcModule() {
            @Override
            protected void configureControllers() {
                control("/secure/*").withController(WebPrincipalController.class);
                control("/auth/*").withController(AuthController.class);
                bind(SecurityConfig.class).to(BasicSecurityConfig.class);
                registerWebPrincipalProvider(MockSessionWebPrincipalProvider.class);
            }
        });
    }

    static class BasicSecurityConfig implements SecurityConfig {

        @Override
        public String getLoginUrl(String originalRequestUrl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getAccessDeniedUrl() {
            return "/secure/errorPage";
        }
    }

}
