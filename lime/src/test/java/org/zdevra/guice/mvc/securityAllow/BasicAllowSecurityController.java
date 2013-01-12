package org.zdevra.guice.mvc.securityAllow;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.View;
import org.zdevra.guice.mvc.security.annotations.RequireAuthenticated;
import org.zdevra.guice.mvc.security.annotations.RequireRole;

@Controller
public class BasicAllowSecurityController {

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

    @Path("/denyRole")
    @Model("other")
    @View("/views/main.html.jsp")
    @RequireRole("BOGUS")
    public String roleDeny() {
        return "value1";
    }

    @Path("/errorPage")
    @Model("param1")
    @View("/views/main.html.jsp")
    public String errorPage() {
        return "value1";
    }
}
