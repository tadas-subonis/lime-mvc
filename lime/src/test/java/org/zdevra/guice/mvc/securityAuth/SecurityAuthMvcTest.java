package org.zdevra.guice.mvc.securityAuth;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.WebTest;

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
        HttpMethod method = doRequest("http://localhost:9191/securityAuth/invalidate");
        method = doRequest("http://localhost:9191/securityAuth/requireAuthenticated");

        int code = method.getStatusCode();
        Header location = method.getResponseHeader("Location");

        System.out.println("code:" + code);
        System.out.println("location:" + location.getValue());
        Assert.assertEquals(code, 302);
        Assert.assertEquals(location.getValue(), "http://localhost:9191/securityAuth/errorPage");

        method = doRequest("http://localhost:9191/securityAuth/authenticate");

        method = doRequest("http://localhost:9191/securityAuth/requireAuthenticated");
        code = method.getStatusCode();
        Assert.assertEquals(code, 200);
        Assert.assertEquals(method.getPath(), "/securityAuth/requireAuthenticated");
        System.out.println("code:" + code);
    }

    @Test
    public void shouldDenyBeforeLoginAndAllowAfterWithRoles() throws HttpException, IOException {
        HttpMethod method = doRequest("http://localhost:9191/securityAuth/invalidate");
        method = doRequest("http://localhost:9191/securityAuth/requireRole");

        int code = method.getStatusCode();
        Header location = method.getResponseHeader("Location");

        System.out.println("code:" + code);
        System.out.println("location:" + location.getValue());
        Assert.assertEquals(code, 302);
        Assert.assertEquals(location.getValue(), "http://localhost:9191/securityAuth/errorPage");

        method = doRequest("http://localhost:9191/securityAuth/authenticate");

        method = doRequest("http://localhost:9191/securityAuth/requireRole");
        code = method.getStatusCode();
        Assert.assertEquals(code, 200);
        Assert.assertEquals(method.getPath(), "/securityAuth/requireRole");
        System.out.println("code:" + code);
    }


}
