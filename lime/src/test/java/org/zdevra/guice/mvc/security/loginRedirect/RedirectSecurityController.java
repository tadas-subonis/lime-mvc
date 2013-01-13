package org.zdevra.guice.mvc.security.loginRedirect;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.security.annotations.LoginRedirect;
import org.zdevra.guice.mvc.security.annotations.RequireAuthenticated;

@Controller
@LoginRedirect
public class RedirectSecurityController {

    @Path("/requireAuthenticated")
    @RequireAuthenticated
    public void requireAuthenticated() {
    }


}
