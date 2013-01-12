package org.zdevra.guice.mvc.securityAllow;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.security.MockWebPrincipal;
import org.zdevra.guice.mvc.security.SecurityConfig;
import org.zdevra.guice.mvc.security.WebPrincipal;
import org.zdevra.guice.mvc.security.WebPrincipalProvider;

public class SecurityContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new MvcModule() {
            @Override
            protected void configureControllers() {
                control("/securityAllow/*").withController(BasicAllowSecurityController.class);
                bind(SecurityConfig.class).to(BasicSecurityConfig.class);
                registerWebPrincipalProvider(BasicWebPrincipalProvider.class);
            }
        });
    }

    private static class BasicWebPrincipalProvider implements WebPrincipalProvider {

        @Override
        public WebPrincipal get() {
            return new MockWebPrincipal();
        }

    }

    private static class BasicSecurityConfig implements SecurityConfig {

        @Override
        public String getLoginUrl(String originalRequestUrl) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getAccessDeniedUrl() {
            return "/securityAllow/errorPage";
        }
    }

}
