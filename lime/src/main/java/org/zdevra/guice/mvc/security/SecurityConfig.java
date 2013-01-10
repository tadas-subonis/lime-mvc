package org.zdevra.guice.mvc.security;

/**
 *
 * @author Tadas Subonis <tadas.subonis@gmail.com>
 */
public interface SecurityConfig {

    String getLoginUrl(String originalRequestUrl);

    String getAccessDeniedUrl();
}
