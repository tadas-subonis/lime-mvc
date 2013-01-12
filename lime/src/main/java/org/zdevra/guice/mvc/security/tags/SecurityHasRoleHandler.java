package org.zdevra.guice.mvc.security.tags;

import org.zdevra.guice.mvc.security.WebPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

/**
 * @author Tadas
 */
public class SecurityHasRoleHandler extends BodyTagSupport {

    private String[] roles;

    private boolean hasValidUser() {

        HttpServletRequest httpServletRequest = (HttpServletRequest) pageContext.getRequest();
        Principal userPrincipal = httpServletRequest.getUserPrincipal();

        if (userPrincipal == null) {
            return false;
        }

        if (!(userPrincipal instanceof WebPrincipal)) {
            return false;
        }

        WebPrincipal user = (WebPrincipal) userPrincipal;

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return false;
        }

        for (String role : user.getRoles()) {
            if (Arrays.binarySearch(roles, role) >= 0) {
                return true;
            }
        }


        return false;

    }

    @Override
    public int doStartTag() throws JspException {

        if (!hasValidUser()) {
            return SKIP_BODY;
        }

        return EVAL_BODY_BUFFERED;

    }

    @Override
    public int doEndTag() throws JspException {

        return EVAL_PAGE;

    }

    @Override
    public int doAfterBody() throws JspException {

        BodyContent bodyCont = getBodyContent();
        JspWriter out = bodyCont.getEnclosingWriter();
        try {
            bodyCont.writeOut(out);
        } catch (IOException ex) {
            throw new JspException(ex);
        }

        bodyCont.clearBody();

        return SKIP_BODY;

    }

    public void setRoles(String roles) {

        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("Names cannot be empty");
        }

        String[] split = roles.split(",");

        Arrays.sort(split);

        this.roles = split;
    }
}
