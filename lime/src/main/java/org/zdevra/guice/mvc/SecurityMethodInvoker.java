package org.zdevra.guice.mvc;

import org.zdevra.guice.mvc.security.ResourceDeniedException;
import org.zdevra.guice.mvc.security.WebPrincipal;
import org.zdevra.guice.mvc.security.WebPrincipalProvider;
import org.zdevra.guice.mvc.security.annotations.RequireAuthenticated;
import org.zdevra.guice.mvc.security.annotations.RequireRole;

/**
 *
 * @author Tadas Subonis <tadas.subonis@gmail.com>
 */
class SecurityMethodInvoker extends MethodInvokerDecorator {

    private final SecurityHandler securityHandler = new SecurityHandler();
    private final WebPrincipalProvider webPrincipalProvider;
    private RequireAuthenticated requireAuthenticated;
    private RequireRole requireRole;
    private RequireAuthenticated requireAuthenticatedMethod;
    private RequireRole requireRoleMethod;

    public SecurityMethodInvoker(MappingData reqMappingData, MethodInvoker filteredInvoker) {
        super(filteredInvoker);
        requireRoleMethod = reqMappingData.getMethod().getAnnotation(RequireRole.class);
        requireAuthenticatedMethod = reqMappingData.getMethod().getAnnotation(RequireAuthenticated.class);
        requireRole = reqMappingData.getControllerClass().getAnnotation(RequireRole.class);
        requireAuthenticated = reqMappingData.getControllerClass().getAnnotation(RequireAuthenticated.class);
        webPrincipalProvider = reqMappingData.getInjector().getInstance(WebPrincipalProvider.class);
    }

    @Override
    public ModelAndView invoke(InvokeData data) {
        securityHandler.handle(requireAuthenticated);
        securityHandler.handle(requireRole);
        securityHandler.handle(requireAuthenticatedMethod);
        securityHandler.handle(requireRoleMethod);

        return decoratedInvoker.invoke(data);
    }

    private class SecurityHandler {

        public SecurityHandler() {
        }

        public void handle(RequireAuthenticated requireAuthenticated) {
            if (requireAuthenticated == null) {
                return;
            }

            WebPrincipal webPrincipal = webPrincipalProvider.get();
            if (!webPrincipal.isAuthenticated()) {
                throw new ResourceDeniedException();
            }
        }

        private void handle(RequireRole requireRole) {
            if (requireRole == null) {
                return;
            }

            WebPrincipal webPrincipal = webPrincipalProvider.get();
            for (String role : requireRole.value()) {
                if (webPrincipal.getRoles().contains(role)) {
                    return;
                }
            }
            throw new ResourceDeniedException();
        }
    }
}
