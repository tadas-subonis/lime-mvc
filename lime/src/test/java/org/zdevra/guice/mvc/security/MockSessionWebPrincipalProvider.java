package org.zdevra.guice.mvc.security;

import com.google.inject.Injector;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class MockSessionWebPrincipalProvider implements WebPrincipalProvider {

    private static final Logger LOGGER = Logger.getLogger(MockSessionWebPrincipalProvider.class.getName());

    @Inject
    private Injector injector;

    @Override
    public WebPrincipal get() {
        HttpServletRequest instance = injector.getInstance(HttpServletRequest.class);
        WebPrincipal user = (WebPrincipal) instance.getSession().getAttribute("USER");
        LOGGER.info("Found principal: " + user);
        return user;
    }
}
