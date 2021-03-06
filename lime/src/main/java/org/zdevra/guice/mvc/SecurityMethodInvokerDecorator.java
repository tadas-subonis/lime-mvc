package org.zdevra.guice.mvc;

import org.zdevra.guice.mvc.security.ResourceDeniedException;
import org.zdevra.guice.mvc.security.ResourceLoginException;
import org.zdevra.guice.mvc.security.WebPrincipal;
import org.zdevra.guice.mvc.security.annotations.LoginRedirect;
import org.zdevra.guice.mvc.security.annotations.RequireAuthenticated;
import org.zdevra.guice.mvc.security.annotations.RequireRole;
import org.zdevra.guice.mvc.security.internal.FinalWebPrincipalProvider;

/**
 * @author Tadas Subonis <tadas.subonis@gmail.com>
 */
class SecurityMethodInvokerDecorator extends MethodInvokerDecorator {

    private RequireAuthenticated requireAuthenticated;
    private RequireRole requireRole;
    private RequireAuthenticated requireAuthenticatedMethod;
    private RequireRole requireRoleMethod;
    private final FinalWebPrincipalProvider webPrincipalProvider;
    private final LoginRedirect doLoginRedirectMethod;
    private final LoginRedirect doLoginRedirect;

    public SecurityMethodInvokerDecorator(MappingData reqMappingData, MethodInvoker filteredInvoker) {
        super(filteredInvoker);
        requireRoleMethod = reqMappingData.getMethod().getAnnotation(RequireRole.class);
        requireAuthenticatedMethod = reqMappingData.getMethod().getAnnotation(RequireAuthenticated.class);
        doLoginRedirectMethod = reqMappingData.getMethod().getAnnotation(LoginRedirect.class);
        requireRole = reqMappingData.getControllerClass().getAnnotation(RequireRole.class);
        requireAuthenticated = reqMappingData.getControllerClass().getAnnotation(RequireAuthenticated.class);
        doLoginRedirect = reqMappingData.getControllerClass().getAnnotation(LoginRedirect.class);
        webPrincipalProvider = reqMappingData.getInjector().getInstance(FinalWebPrincipalProvider.class);
    }

    @Override
    public ModelAndView invoke(InvokeData data) {

        SecurityHandler securityHandler = new SecurityHandler(webPrincipalProvider.get());
        try {
            securityHandler.handle(requireAuthenticated);
            securityHandler.handle(requireRole);
            securityHandler.handle(requireAuthenticatedMethod);
            securityHandler.handle(requireRoleMethod);
        } catch (ResourceDeniedException ex) {
            if (doLoginRedirect == null && doLoginRedirectMethod == null) {
                throw ex;
            }
            throw new ResourceLoginException();
        }

        return decoratedInvoker.invoke(data);
    }

    private class SecurityHandler {
        private final WebPrincipal webPrincipal;

        public SecurityHandler(WebPrincipal webPrincipal) {
            this.webPrincipal = webPrincipal;
        }

        public void handle(RequireAuthenticated requireAuthenticated) {
            if (requireAuthenticated == null) {
                return;
            }

            if (!webPrincipal.isAuthenticated()) {
                throw new ResourceDeniedException();
            }
        }

        private void handle(RequireRole requireRole) {
            if (requireRole == null) {
                return;
            }

            for (String role : requireRole.value()) {
                if (webPrincipal.getRoles().contains(role)) {
                    return;
                }
            }
            throw new ResourceDeniedException();
        }
    }
}
