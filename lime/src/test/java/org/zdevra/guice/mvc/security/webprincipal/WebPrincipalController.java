/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *****************************************************************************/
package org.zdevra.guice.mvc.security.webprincipal;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.View;
import org.zdevra.guice.mvc.security.WebPrincipal;
import org.zdevra.guice.mvc.security.annotations.RequireRole;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WebPrincipalController {

    @Path("/anonPrincipal")
    @Model("name")
    @View("webuser.jsp")
    public String anonPrincipal(HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        return name;
    }

    @Path("/authPrincipal")
    @Model("name")
    @View("webuser.jsp")
    public String authPrincipal(HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        return name;
    }

    @Path("/authPrincipalParam")
    @Model("name")
    @View("webuser.jsp")
    public String authPrincipalParam(WebPrincipal webPrincipal) {
        String name = webPrincipal.getName();
        return name;
    }

    @Path("/errorPage")
    @View("mock.jsp")
    public void throwException() {

    }


}
