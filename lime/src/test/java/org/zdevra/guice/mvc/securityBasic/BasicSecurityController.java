package org.zdevra.guice.mvc.securityBasic;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.security.annotations.RequireAuthenticated;
import org.zdevra.guice.mvc.security.annotations.RequireRole;

@Controller
public class BasicSecurityController {

    @Path("/requireAuthenticated")
    @Model("param1")
    @RequireAuthenticated
    public String requireAuthenticated() {
        return "value1";
    }

    @Path("/requireRole")
    @Model("param1")
    @RequireRole({"user"})
    public String requireRole() {
        return "value1";
    }

    @Path("/errorPage")
    @Model("param1")
    public String errorPage() {
        return "value1";
    }
}
