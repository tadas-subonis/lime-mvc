package org.zdevra.guice.mvc.security;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.RedirectView;
import org.zdevra.guice.mvc.annotations.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Path("/invalidate")
    @RedirectView("/")
    public void invalidate(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("USER", null);
    }

    @Path("/authenticate")
    @RedirectView("/")
    public void authenticate(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        WebPrincipal webPrincipal = new MockWebPrincipal();
        session.setAttribute("USER", webPrincipal);
    }

    @Path("/login")
    @View("mock.jsp")
    public void login(HttpServletRequest httpServletRequest) {
    }
}
