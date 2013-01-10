package org.zdevra.guice.mvc.security;

import java.security.Principal;
import java.util.Set;

/**
 *
 * @author Tadas Subonis <tadas.subonis@gmail.com>
 */
public interface WebPrincipal extends Principal {

    boolean isAuthenticated();

    Set<String> getRoles();
}
