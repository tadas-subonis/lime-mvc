package org.zdevra.guice.mvc.security.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zdevra.guice.mvc.ExceptionHandler;
import org.zdevra.guice.mvc.security.SecurityConfig;

/**
 *
 * @author Tadas Subonis <tadas.subonis@gmail.com>
 */
@Singleton
public class ResourceDeniedExceptionHandler implements ExceptionHandler {

    @Inject
    private SecurityConfig securityConfig;

    @Override
    public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect(securityConfig.getAccessDeniedUrl());
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }
}
