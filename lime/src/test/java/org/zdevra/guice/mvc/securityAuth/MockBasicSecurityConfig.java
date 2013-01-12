package org.zdevra.guice.mvc.securityAuth;

import org.zdevra.guice.mvc.security.SecurityConfig;

class MockBasicSecurityConfig implements SecurityConfig {

    @Override
    public String getLoginUrl(String originalRequestUrl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAccessDeniedUrl() {
        return "/securityAuth/errorPage";
    }
}
