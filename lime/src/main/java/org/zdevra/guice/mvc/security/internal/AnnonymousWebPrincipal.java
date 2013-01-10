package org.zdevra.guice.mvc.security.internal;

import java.util.Collections;
import java.util.Set;
import org.zdevra.guice.mvc.security.WebPrincipal;

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
