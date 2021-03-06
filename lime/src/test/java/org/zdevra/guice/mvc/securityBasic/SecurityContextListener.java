package org.zdevra.guice.mvc.securityBasic;

import org.zdevra.guice.mvc.MvcModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.zdevra.guice.mvc.security.MockBasicSecurityConfig;
import org.zdevra.guice.mvc.security.SecurityConfig;

import javax.servlet.http.HttpServletRequest;

public class SecurityContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new MvcModule() {
            @Override
            protected void configureControllers() {
                control("/secure/*").withController(BasicSecurityController.class);
                bind(SecurityConfig.class).to(MockBasicSecurityConfig.class);
            }
        });
    }


}
