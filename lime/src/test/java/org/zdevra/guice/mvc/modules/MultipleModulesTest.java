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
package org.zdevra.guice.mvc.modules;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletUnitClient;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.AbstractTest;
import org.zdevra.guice.mvc.TestServlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This case tests the basic functionality like invoking right method,
 * manipulation with session, exception handling etc..
 */
@Test
public class MultipleModulesTest extends AbstractTest {

    public static class Case1Servlet extends TestServlet {

        public Case1Servlet() {
            super(new Class[]{FirstController.class, SecondController.class}, new FirstModule(), new SecondModule());
        }
    }

    public MultipleModulesTest() {
        super(Case1Servlet.class);
    }

    @Test
    public void testSimpleRequest() throws IOException, ServletException {
        //prepare request
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new GetMethodWebRequest("http://www.bookstore.com/test/nothing");
        InvocationContext ic = sc.newInvocation(request);

        //invoke request
        Servlet ss = ic.getServlet();
        ss.service(ic.getRequest(), ic.getResponse());
        WebResponse response = ic.getServletResponse();

        //process response
        String out = response.getText();
        assertThat(response.getResponseCode(), is(HttpServletResponse.SC_MOVED_TEMPORARILY));
    }


}
