package org.zdevra.guice.mvc.securityAuth;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.security.AuthController;
import org.zdevra.guice.mvc.security.MockBasicSecurityConfig;
import org.zdevra.guice.mvc.security.MockSessionWebPrincipalProvider;
import org.zdevra.guice.mvc.security.SecurityConfig;

public class SecurityContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new MvcModule() {
            @Override
            protected void configureControllers() {
                control("/auth/*").withController(AuthController.class);
                control("/secure/*").withController(AuthSecurityController.class);
                bind(SecurityConfig.class).to(MockBasicSecurityConfig.class);
                registerWebPrincipalProvider(MockSessionWebPrincipalProvider.class);
            }
        });
    }

}
