package org.zdevra.guice.mvc.securityAllow;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.WebTest;

import java.io.File;
import java.io.IOException;

public class SecurityBasicAllowMvcTest extends WebTest {

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
        addWebapp("src/test/resources/cases/securityAllow/webapp", "/");
    }

    //------------------------------------------------------------------------------------
    // tests
    //------------------------------------------------------------------------------------
    @Test
    public void shouldAllowAccessForAuthorized() throws HttpException, IOException {
        HttpMethod method = doRequest("http://localhost:9191/securityAllow/requireAuthenticated");

        int code = method.getStatusCode();
        String path = method.getPath();

        System.out.println("response:" + method.getResponseBodyAsString());
        System.out.println("code:" + code);

        Assert.assertEquals(code, 200);
        Assert.assertEquals(path, "/securityAllow/requireAuthenticated");
    }

    @Test
    public void shouldAllowAccessByRole() throws HttpException, IOException {
        HttpMethod method = doRequest("http://localhost:9191/securityAllow/requireRole");

        int code = method.getStatusCode();
        String path = method.getPath();

        System.out.println("response:" + method.getResponseBodyAsString());
        System.out.println("code:" + code);
        Assert.assertEquals(code, 200);
        Assert.assertEquals(path, "/securityAllow/requireRole");
    }

    @Test
    public void shouldNotAllowAccessWrongRole() throws HttpException, IOException {
        HttpMethod method = doRequest("http://localhost:9191/securityAllow/denyRole");

        int code = method.getStatusCode();
        String path = method.getPath();
        Header location = method.getResponseHeader("Location");

        System.out.println("response:" + method.getResponseBodyAsString());
        System.out.println("code:" + code);
        Assert.assertEquals(code, 302);
        Assert.assertEquals(location.getValue(), "http://localhost:9191/securityAllow/errorPage");
    }

}
