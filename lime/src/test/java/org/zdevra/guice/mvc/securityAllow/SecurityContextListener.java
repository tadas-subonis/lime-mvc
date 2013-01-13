package org.zdevra.guice.mvc.securityAllow;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.security.*;

import javax.servlet.http.HttpServletRequest;

public class SecurityContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new MvcModule() {
            @Override
            protected void configureControllers() {
                control("/secure/*").withController(BasicAllowSecurityController.class);
                bind(SecurityConfig.class).to(MockBasicSecurityConfig.class);
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


}
