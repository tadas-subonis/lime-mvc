package org.zdevra.guice.mvc.security.internal;

import org.zdevra.guice.mvc.security.WebPrincipal;

import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Tadas Subonis <tadas.subonis@gmail.com>
 */
class AnnonymousWebPrincipal implements WebPrincipal {

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public Set<String> getRoles() {
        return Collections.EMPTY_SET;
    }

    @Override
    public String getName() {
        return "Annonymous";
    }
}
