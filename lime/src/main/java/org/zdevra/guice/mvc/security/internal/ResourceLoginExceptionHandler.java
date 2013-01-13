package org.zdevra.guice.mvc.security.internal;

import com.google.inject.Inject;
import org.zdevra.guice.mvc.ExceptionHandler;
import org.zdevra.guice.mvc.security.SecurityConfig;

import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class ResourceLoginExceptionHandler implements ExceptionHandler {
    @Inject
    private SecurityConfig securityConfig;

    @Override
    public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect(securityConfig.getLoginUrl(req));
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }
}
