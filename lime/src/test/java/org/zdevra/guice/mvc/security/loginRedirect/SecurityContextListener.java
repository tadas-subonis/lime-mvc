package org.zdevra.guice.mvc.security.loginRedirect;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.security.AuthController;
import org.zdevra.guice.mvc.security.MockBasicSecurityConfig;
import org.zdevra.guice.mvc.security.SecurityConfig;

public class SecurityContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new MvcModule() {
            @Override
            protected void configureControllers() {
                control("/auth/*").withController(AuthController.class);
                control("/secureMethod/*").withController(RedirectMethodSecurityController.class);
                control("/secureController/*").withController(RedirectSecurityController.class);
                bind(SecurityConfig.class).to(MockBasicSecurityConfig.class);
            }
        });
    }


}
