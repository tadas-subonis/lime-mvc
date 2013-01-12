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

import org.apache.commons.httpclient.HttpMethod;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.WebTest;

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
public class WebPrincipalControllerTest extends WebTest {

    //------------------------------------------------------------------------------------
    // setup
    //------------------------------------------------------------------------------------
    @Override
    protected void setupWebserver() {
        setPort(9191);
        addWebapp("src/test/resources/cases/security/webprincipal/webapp", "/");
    }


    @Test
    public void shouldShowAnnonymousUserName() throws IOException, ServletException {

        HttpMethod method = doRequest("http://localhost:9191/auth/invalidate");
        method = doRequest("http://localhost:9191/secure/anonPrincipal");

        int code = method.getStatusCode();
        System.out.println("code:" + code);
        assertThat(code, is(HttpServletResponse.SC_OK));

        String responseBodyAsString = method.getResponseBodyAsString();
        assertThat(responseBodyAsString, is("Annonymous"));
    }

    @Test
    public void shouldShowAuthenticatedUserName() throws IOException, ServletException {

        HttpMethod method = doRequest("http://localhost:9191/auth/invalidate");
        method = doRequest("http://localhost:9191/auth/authenticate");
        method = doRequest("http://localhost:9191/secure/authPrincipal");

        int code = method.getStatusCode();
        System.out.println("code:" + code);
        assertThat(code, is(HttpServletResponse.SC_OK));

        String responseBodyAsString = method.getResponseBodyAsString();
        assertThat(responseBodyAsString, is("SimpleName"));
    }

    @Test
    public void shouldProviderWebPrincipalAsRequestParameter() throws IOException, ServletException {

        HttpMethod method = doRequest("http://localhost:9191/auth/invalidate");
        method = doRequest("http://localhost:9191/auth/authenticate");
        method = doRequest("http://localhost:9191/secure/authPrincipalParam");

        int code = method.getStatusCode();
        System.out.println("code:" + code);
        assertThat(code, is(HttpServletResponse.SC_OK));

        String responseBodyAsString = method.getResponseBodyAsString();
        assertThat(responseBodyAsString, is("SimpleName"));
    }


}
