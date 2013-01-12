package org.zdevra.guice.mvc.security.internal;

import org.zdevra.guice.mvc.security.WebPrincipal;
import org.zdevra.guice.mvc.security.WebPrincipalProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class DefaultFinalWebPrincipalProvider implements FinalWebPrincipalProvider {

    private final Set<WebPrincipalProvider> webPrincipalProviders;

    @Inject
    DefaultFinalWebPrincipalProvider(Set<WebPrincipalProvider> webPrincipalProviders) {
        this.webPrincipalProviders = webPrincipalProviders;
    }

    @Override
    public WebPrincipal get() {
        WebPrincipal webPrincipal = null;
        for (WebPrincipalProvider provider : webPrincipalProviders) {
            if (provider.get() == null) {
                continue;
            }
            webPrincipal = provider.get();
            if (webPrincipal.isAuthenticated()) {
                return webPrincipal;
            }
        }

        if (webPrincipal == null) {
            throw new IllegalStateException("It seems that we don't have valid providers...");
        }

        return webPrincipal;

    }
}
