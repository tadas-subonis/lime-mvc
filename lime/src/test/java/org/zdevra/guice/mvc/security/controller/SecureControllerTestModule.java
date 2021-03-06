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
package org.zdevra.guice.mvc.security.controller;

import com.google.inject.servlet.GuiceFilter;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.TestView;
import org.zdevra.guice.mvc.security.MockSessionWebPrincipalProvider;
import org.zdevra.guice.mvc.security.SecurityConfig;

import javax.servlet.http.HttpServletRequest;

public class SecureControllerTestModule extends MvcModule {

    @Override
    protected void configureControllers() {
        filter("/*").through(GuiceFilter.class);
        bindViewName("default").toViewInstance(new TestView("0"));
        control("/somepath").withView("someview.jsp");
        bind(SecurityConfig.class).to(BasicSecurityConfig.class);
    }


    private static class BasicSecurityConfig implements SecurityConfig {

        @Override
        public String getLoginUrl(HttpServletRequest request) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getAccessDeniedUrl() {
            return "/test/errorPage";
        }
    }
}
