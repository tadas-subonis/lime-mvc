package org.zdevra.guice.mvc.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MockWebPrincipal implements WebPrincipal {

    final String[] roles = new String[]{"USER", "MANAGER"};

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Set<String> getRoles() {
        return new HashSet<String>(Arrays.asList(roles));
    }

    @Override
    public String getName() {
        return "SimpleName";
    }
}
