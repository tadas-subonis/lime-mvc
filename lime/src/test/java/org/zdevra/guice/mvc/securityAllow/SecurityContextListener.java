package org.zdevra.guice.mvc.securityAllow;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.security.SecurityConfig;
import org.zdevra.guice.mvc.security.WebPrincipal;
import org.zdevra.guice.mvc.security.WebPrincipalProvider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
            final String[] roles = new String[]{"USER", "MANAGER"};
            return new WebPrincipal() {
                @Override
                public boolean isAuthenticated() {
                    return true;
                }

                @Override
                public Set<String> getRoles() {
                    return new HashSet<String>(Arrays.asList(roles));
                }

                @Override
                public String getName() {
                    return "SimpleName";
                }
            };
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
