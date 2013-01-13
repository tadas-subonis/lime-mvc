package org.zdevra.guice.mvc.security;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tadas Subonis <tadas.subonis@gmail.com>
 */
public interface SecurityConfig {

    String getLoginUrl(HttpServletRequest request);

    String getAccessDeniedUrl();
}
