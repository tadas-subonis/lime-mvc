package org.zdevra.guice.mvc.security.loginRedirect;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.WebTest;

import java.io.File;
import java.io.IOException;

public class SecurityLoginRedirectControllerTest extends WebTest {

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
        addWebapp("src/test/resources/cases/security/loginRedirect/webapp", "/");
    }

    //------------------------------------------------------------------------------------
    // tests
    //------------------------------------------------------------------------------------
    @Test
    public void shouldRedirectToLoginPageIfLoginRedirectAnnotationIsPresentOnMethod() throws HttpException, IOException {
        HttpMethod method = doRequest("http://localhost:9191/secureMethod/requireAuthenticated");

        int code = method.getStatusCode();
        Header location = method.getResponseHeader("Location");

        System.out.println("code:" + code);
        Assert.assertEquals(code, 302);
        Assert.assertEquals(location.getValue(), "http://localhost:9191/auth/login");
    }

    @Test
    public void shouldRedirectToLoginPageIfLoginRedirectAnnotationIsPresentOnController() throws HttpException, IOException {
        HttpMethod method = doRequest("http://localhost:9191/secureController/requireAuthenticated");

        int code = method.getStatusCode();
        Header location = method.getResponseHeader("Location");

        System.out.println("code:" + code);
        Assert.assertEquals(code, 302);
        Assert.assertEquals(location.getValue(), "http://localhost:9191/auth/login");
    }


}
