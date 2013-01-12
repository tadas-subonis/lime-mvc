package org.zdevra.guice.mvc.securityBasic;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.WebTest;

public class SecurityBasicDenyMvcTest extends WebTest {

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
        addWebapp("src/test/resources/cases/securityBasic/webapp", "/");
    }

    //------------------------------------------------------------------------------------
    // tests
    //------------------------------------------------------------------------------------
    @Test
    public void testRequestAuthenticated() throws HttpException, IOException {
        HttpMethod method = doRequest("http://localhost:9191/securityBasic/requireAuthenticated");

        int code = method.getStatusCode();
        Header location = method.getResponseHeader("Location");

        System.out.println("code:" + code);
        System.out.println("location:" + location.getValue());
        Assert.assertEquals(code, 302);
        Assert.assertEquals(location.getValue(), "http://localhost:9191/securityBasic/errorPage");
    }

    @Test
    public void testRequiredRole() throws HttpException, IOException {
        HttpMethod method = doRequest("http://localhost:9191/securityBasic/requireRole");

        int code = method.getStatusCode();
        Header location = method.getResponseHeader("Location");

        System.out.println("code:" + code);
        System.out.println("location:" + location.getValue());
        Assert.assertEquals(code, 302);
        Assert.assertEquals(location.getValue(), "http://localhost:9191/securityBasic/errorPage");
    }

}
