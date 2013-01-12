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

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletUnitClient;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.AbstractTest;
import org.zdevra.guice.mvc.TestServlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * This case tests the basic functionality like invoking right method,
 * manipulation with session, exception handling etc..
 */
@Test
public class SecureControllerTest extends AbstractTest {

    public static class InnerTestServlet extends TestServlet {

        public InnerTestServlet() {
            super(SecureController.class, new SecureControllerTestModule());
        }
    }

    public SecureControllerTest() {
        super(InnerTestServlet.class);
    }

    @Test
    public void testSimpleRequest() throws IOException, ServletException {


        WebResponse response = executeSimpleUrl("http://www.test.com/test/something");


        //process response
        String out = response.getText();
        int code = response.getResponseCode();


        assertThat(code, is(HttpServletResponse.SC_MOVED_TEMPORARILY));
    }

}
