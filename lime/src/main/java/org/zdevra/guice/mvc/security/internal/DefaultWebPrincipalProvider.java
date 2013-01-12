package org.zdevra.guice.mvc.security.internal;

import org.zdevra.guice.mvc.security.WebPrincipal;
import org.zdevra.guice.mvc.security.WebPrincipalProvider;

import javax.inject.Singleton;

/**
 * @author Tadas Subonis <tadas.subonis@gmail.com>
 */
@Singleton
public class DefaultWebPrincipalProvider implements WebPrincipalProvider {

    private static final WebPrincipal ANNONYMOUS_PRINCIPAL = new AnnonymousWebPrincipal();

    @Override
    public WebPrincipal get() {

        return ANNONYMOUS_PRINCIPAL;


    }
}
