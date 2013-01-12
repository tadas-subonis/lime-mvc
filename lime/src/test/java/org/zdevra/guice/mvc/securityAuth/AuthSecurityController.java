package org.zdevra.guice.mvc.securityAuth;

import org.zdevra.guice.mvc.annotations.*;
import org.zdevra.guice.mvc.security.MockWebPrincipal;
import org.zdevra.guice.mvc.security.WebPrincipal;
import org.zdevra.guice.mvc.security.annotations.RequireAuthenticated;
import org.zdevra.guice.mvc.security.annotations.RequireRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

@Controller
public class AuthSecurityController {

    @Path("/requireAuthenticated")
    @Model("param")
    @View("/views/main.html.jsp")
    @RequireAuthenticated
    public String requireAuthenticated() {
        return "value1";
    }

    @Path("/requireRole")
    @Model("other")
    @View("/views/main.html.jsp")
    @RequireRole("USER")
    public String requireRole() {
        return "value1";
    }


    @Path("/invalidate")
    @RedirectView("/")
    public void invalidate(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("USER", null);
    }

    @Path("/authenticate")
    @View("/views/main.html.jsp")
    public void authenticate(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        WebPrincipal webPrincipal = new MockWebPrincipal();
        session.setAttribute("USER", webPrincipal);
    }


    @Path("/errorPage")
    @Model("param1")
    @View("/views/main.html.jsp")
    public String errorPage() {
        return "value1";
    }
}
