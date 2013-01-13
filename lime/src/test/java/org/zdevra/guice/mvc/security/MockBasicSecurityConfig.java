package org.zdevra.guice.mvc.security;

import org.zdevra.guice.mvc.security.SecurityConfig;

import javax.servlet.http.HttpServletRequest;

public class MockBasicSecurityConfig implements SecurityConfig {

    @Override
    public String getLoginUrl(HttpServletRequest request) {
        return "/auth/login";
    }

    @Override
    public String getAccessDeniedUrl() {
        return "/secure/errorPage";
    }
}
