package org.zdevra.guice.mvc.security.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.security.AuthController;
import org.zdevra.guice.mvc.security.MockSessionWebPrincipalProvider;
import org.zdevra.guice.mvc.security.SecurityConfig;
import org.zdevra.guice.mvc.securityAuth.AuthSecurityController;

import javax.servlet.http.HttpServletRequest;

public class SecurityContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new MvcModule() {
            @Override
            protected void configureControllers() {
                control("/secure/*").withController(SecureRoleController.class);
                control("/auth/*").withController(AuthController.class);
                bind(SecurityConfig.class).to(BasicSecurityConfig.class);
                registerWebPrincipalProvider(MockSessionWebPrincipalProvider.class);
            }
        });
    }

    static class BasicSecurityConfig implements SecurityConfig {

        @Override
        public String getLoginUrl(HttpServletRequest request) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getAccessDeniedUrl() {
            return "/security/errorPage";
        }
    }

}
