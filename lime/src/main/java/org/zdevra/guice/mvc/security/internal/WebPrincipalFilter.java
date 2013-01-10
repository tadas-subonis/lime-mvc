package org.zdevra.guice.mvc.security.internal;

import com.google.inject.Singleton;
import java.io.IOException;
import java.security.Principal;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.zdevra.guice.mvc.security.WebPrincipal;
import org.zdevra.guice.mvc.security.WebPrincipalProvider;

/**
 *
 * @author Tadas Subonis <tadas.subonis@gmail.com>
 */
@Singleton
public class WebPrincipalFilter implements Filter {

    @Inject
    private WebPrincipalProvider webPrincipalProvider;

    public WebPrincipalFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hsr = (HttpServletRequest) request;
        HttpServletRequestWrapper principalRequest = new HttpServletRequestWrapper(hsr);
        chain.doFilter(principalRequest, response);
    }

    @Override
    public void destroy() {
    }

    class LocalHttpServletRequestWrapper extends HttpServletRequestWrapper {

        public LocalHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public boolean isUserInRole(String role) {
            WebPrincipal webPrincipal = webPrincipalProvider.get();
            return webPrincipal.getRoles().contains(role);
        }

        @Override
        public Principal getUserPrincipal() {
            WebPrincipal webPrincipal = webPrincipalProvider.get();
            return webPrincipal;
        }
    }
}
