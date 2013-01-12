package org.zdevra.guice.mvc.securityBasic;

import org.zdevra.guice.mvc.MvcModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.zdevra.guice.mvc.security.SecurityConfig;

public class SecurityContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new MvcModule() {
            @Override
            protected void configureControllers() {
                control("/securityBasic/*").withController(BasicSecurityController.class);

                bind(SecurityConfig.class).to(BasicSecurityConfig.class);
            }
        });
    }

    private static class BasicSecurityConfig implements SecurityConfig {

        @Override
        public String getLoginUrl(String originalRequestUrl) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getAccessDeniedUrl() {
            return "/securityBasic/errorPage";
        }
    }
}
