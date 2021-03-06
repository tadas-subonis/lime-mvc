package org.zdevra.guice.mvc.securityAuth;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.WebTest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class SecurityAuthMvcTest extends WebTest {

    //------------------------------------------------------------------------------------
    // setup
    //------------------------------------------------------------------------------------
    @Override
    protected void setupWebserver() {
        setPort(9191);
        try {
            System.out.println(new File(".").getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addWebapp("src/test/resources/cases/securityAuth/webapp", "/");
    }

    //------------------------------------------------------------------------------------
    // tests
    //------------------------------------------------------------------------------------
    @Test
    public void shouldDenyBeforeLoginAndAllowAfter() throws HttpException, IOException {
        HttpMethod method = doRequest("http://localhost:9191/auth/invalidate");
        method = doRequest("http://localhost:9191/secure/requireAuthenticated");

        int code = method.getStatusCode();
        Header location = method.getResponseHeader("Location");

        System.out.println("code:" + code);
        System.out.println("location:" + location.getValue());
        Assert.assertEquals(code, 302);
        Assert.assertEquals(location.getValue(), "http://localhost:9191/secure/errorPage");

        method = doRequest("http://localhost:9191/auth/authenticate");

        method = doRequest("http://localhost:9191/secure/requireAuthenticated");
        code = method.getStatusCode();
        Assert.assertEquals(code, 200);
        Assert.assertEquals(method.getPath(), "/secure/requireAuthenticated");
        System.out.println("code:" + code);
    }

    @Test
    public void shouldDenyBeforeLoginAndAllowAfterWithRoles() throws HttpException, IOException {
        HttpMethod method = doRequest("http://localhost:9191/auth/invalidate");
        method = doRequest("http://localhost:9191/secure/requireRole");

        int code = method.getStatusCode();
        Header location = method.getResponseHeader("Location");

        System.out.println("code:" + code);
        System.out.println("location:" + location.getValue());
        Assert.assertEquals(code, 302);
        Assert.assertEquals(location.getValue(), "http://localhost:9191/secure/errorPage");

        method = doRequest("http://localhost:9191/auth/authenticate");

        method = doRequest("http://localhost:9191/secure/requireRole");
        code = method.getStatusCode();
        Assert.assertEquals(code, 200);
        Assert.assertEquals(method.getPath(), "/secure/requireRole");
        System.out.println("code:" + code);
    }


    @Test
    public void whileOneUserIsAuthenticatedAnotherShouldNotBe() throws HttpException, IOException {
        int code;
        HttpMethod method = doRequest("http://localhost:9191/auth/invalidate");
        method = doRequest("http://localhost:9191/auth/authenticate");

        method = doRequest("http://localhost:9191/secure/requireRole");
        code = method.getStatusCode();
        Assert.assertEquals(code, 200);
        Assert.assertEquals(method.getPath(), "/secure/requireRole");
        System.out.println("code:" + code);

        method = doNewClientRequest("http://localhost:9191/secure/requireRole");
        code = method.getStatusCode();
        Assert.assertEquals(code, HttpServletResponse.SC_MOVED_TEMPORARILY);
        Header location = method.getResponseHeader("Location");
        Assert.assertEquals(location.getValue(), "http://localhost:9191/secure/errorPage");

    }


}
