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
package org.zdevra.guice.mvc.security.tags;

import org.apache.commons.httpclient.HttpMethod;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.WebTest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This case tests the basic functionality like invoking right method,
 * manipulation with session, exception handling etc..
 */
@Test
public class TagsTest extends WebTest {

    //------------------------------------------------------------------------------------
    // setup
    //------------------------------------------------------------------------------------
    @Override
    protected void setupWebserver() {
        setPort(9191);
        addWebapp("src/test/resources/cases/security/tags/webapp", "/");
    }


    @Test
    public void shouldShowContentsOfAuthenticatedTagWhenUserIsAuthenticated() throws IOException, ServletException {

        HttpMethod method = doRequest("http://localhost:9191/auth/invalidate");
        method = doRequest("http://localhost:9191/auth/authenticate");
        method = doRequest("http://localhost:9191/secure/isAuthenticated");

        int code = method.getStatusCode();
        System.out.println("code:" + code);
        assertThat(code, is(HttpServletResponse.SC_OK));

        String responseBodyAsString = method.getResponseBodyAsString();
        assertThat(responseBodyAsString, containsString("AUTHENTICATED"));
        assertThat(responseBodyAsString, not(containsString("NOT")));
    }

    @Test
    public void shouldSkipContentsOfAuthenticatedTagWhenUserIsNotAuthenticated() throws IOException, ServletException {

        HttpMethod method = doRequest("http://localhost:9191/auth/invalidate");
        method = doRequest("http://localhost:9191/secure/isAuthenticated");

        int code = method.getStatusCode();
        System.out.println("code:" + code);
        assertThat(code, is(HttpServletResponse.SC_OK));

        String responseBodyAsString = method.getResponseBodyAsString();

        assertThat(responseBodyAsString, not(containsString("AUTHENTICATED")));
        assertThat(responseBodyAsString, containsString("NOT"));
    }


    @Test
    public void shouldSkipContentsWhenPrincipalHasWrongRole() throws IOException, ServletException {
        HttpMethod method = doRequest("http://localhost:9191/auth/invalidate");
        method = doRequest("http://localhost:9191/auth/authenticate");
        method = doRequest("http://localhost:9191/secure/hasRole");

        int code = method.getStatusCode();
        System.out.println("code:" + code);
        assertThat(code, is(HttpServletResponse.SC_OK));

        String responseBodyAsString = method.getResponseBodyAsString();
        assertThat(responseBodyAsString, not(containsString("ADMIN")));
        assertThat(responseBodyAsString, (containsString("MANAGER")));
    }


    @Test
    public void shouldSkipAndShowUsersByIsUserTagByTheirNames() throws IOException, ServletException {
        HttpMethod method = doRequest("http://localhost:9191/auth/invalidate");
        method = doRequest("http://localhost:9191/auth/authenticate");
        method = doRequest("http://localhost:9191/secure/isUser");

        int code = method.getStatusCode();
        System.out.println("code:" + code);
        assertThat(code, is(HttpServletResponse.SC_OK));

        String responseBodyAsString = method.getResponseBodyAsString();
        assertThat(responseBodyAsString, not(containsString("ADMIN")));
        assertThat(responseBodyAsString, (containsString("PASS1")));
        assertThat(responseBodyAsString, (containsString("PASS2")));
    }
}
