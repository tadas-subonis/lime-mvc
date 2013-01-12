package org.zdevra.guice.mvc.securityAuth;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.security.SecurityConfig;
import org.zdevra.guice.mvc.security.WebPrincipal;
import org.zdevra.guice.mvc.security.WebPrincipalProvider;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SecurityContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new MvcModule() {
            @Override
            protected void configureControllers() {
                control("/securityAuth/*").withController(AuthSecurityController.class);
                bind(SecurityConfig.class).to(BasicSecurityConfig.class);
                registerWebPrincipalProvider(BasicWebPrincipalProvider.class);
            }
        });
    }

    private static class BasicWebPrincipalProvider implements WebPrincipalProvider {

        @Inject
        private Injector injector;

        @Override
        public WebPrincipal get() {
            HttpServletRequest instance = injector.getInstance(HttpServletRequest.class);
            WebPrincipal user = (WebPrincipal) instance.getSession().getAttribute("USER");
            return user;
        }
    }

    private static class BasicSecurityConfig implements SecurityConfig {

        @Override
        public String getLoginUrl(String originalRequestUrl) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getAccessDeniedUrl() {
            return "/securityAuth/errorPage";
        }
    }
}
