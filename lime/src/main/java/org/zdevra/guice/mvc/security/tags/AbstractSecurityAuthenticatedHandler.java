package org.zdevra.guice.mvc.security.tags;

import org.zdevra.guice.mvc.security.WebPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.security.Principal;

abstract class AbstractSecurityAuthenticatedHandler extends BodyTagSupport {
    protected boolean isUserLogged() {

        HttpServletRequest httpServletRequest = (HttpServletRequest) pageContext.getRequest();
        Principal userPrincipal = httpServletRequest.getUserPrincipal();

        if (userPrincipal == null) {
            return false;
        }

        if (!(userPrincipal instanceof WebPrincipal)) {
            return false;
        }

        WebPrincipal webPrincipal = (WebPrincipal) userPrincipal;

        return webPrincipal.isAuthenticated();

    }
}
