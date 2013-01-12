package org.zdevra.guice.mvc.security.tags;

import org.zdevra.guice.mvc.security.WebPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.security.Principal;

/**
 * @author Tadas
 */
public class SecurityIsAuthenticatedHandler extends AbstractSecurityAuthenticatedHandler {


    @Override
    public int doStartTag() throws JspException {

        if (!isUserLogged()) {
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
}
