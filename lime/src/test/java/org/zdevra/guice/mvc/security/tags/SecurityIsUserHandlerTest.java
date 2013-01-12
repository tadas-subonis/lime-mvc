/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zdevra.guice.mvc.security.tags;

import org.junit.Before;
import org.junit.Test;
import org.zdevra.guice.mvc.security.WebPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Tadas
 */
public class SecurityIsUserHandlerTest {

    private SecurityIsUserHandler instance;

    @Before
    public void setUp() {
        instance = new SecurityIsUserHandler();
        final WebPrincipal user = createMock(WebPrincipal.class);
        final PageContext pageContext = createMock(PageContext.class);
        final HttpServletRequest httpServletRequest = createMock(HttpServletRequest.class);


        expect(user.getName()).andReturn("mommy").anyTimes();
        expect(pageContext.getRequest()).andReturn(httpServletRequest).anyTimes();
        expect(httpServletRequest.getUserPrincipal()).andReturn(user).anyTimes();
        replay(user);
        replay(pageContext);
        replay(httpServletRequest);

        instance.setPageContext(pageContext);


    }

    /**
     * Test of doStartTag method, of class SecurityIsUserHandler.
     */
    @Test
    public void testDoStartTag() throws Exception {
        System.out.println("doStartTag");


        instance.setUsers("go,no");

        int doStartTag = instance.doStartTag();

        assertEquals(Tag.SKIP_BODY, doStartTag);


        instance.setUsers("user,mommy");
        doStartTag = instance.doStartTag();
        assertEquals(BodyTag.EVAL_BODY_BUFFERED, doStartTag);

        instance.setUsers("mommy");
        doStartTag = instance.doStartTag();
        assertEquals(BodyTag.EVAL_BODY_BUFFERED, doStartTag);
    }

    /**
     * Test of doEndTag method, of class SecurityIsUserHandler.
     */
    @Test
    public void testDoEndTag() throws Exception {
        System.out.println("doEndTag");

        instance.setUsers("go,no");

        int doEndTag = instance.doEndTag();

        assertEquals(Tag.EVAL_PAGE, doEndTag);

        instance.setUsers("go,no");
        doEndTag = instance.doEndTag();
        assertEquals(BodyTag.EVAL_PAGE, doEndTag);

        instance.setUsers("mommy,data");
        doEndTag = instance.doEndTag();
        assertEquals(BodyTag.EVAL_PAGE, doEndTag);
    }

    /**
     * Test of doAfterBody method, of class SecurityIsUserHandler.
     */
    @Test
    public void testDoAfterBody() throws Exception {
        System.out.println("doAfterBody");
        final BodyContent bodyContent = createNiceMock(BodyContent.class);
        replay(bodyContent);

        instance.setBodyContent(bodyContent);
        instance.setUsers("go,no");

        int doAfterBody = instance.doAfterBody();

        assertEquals(Tag.SKIP_BODY, doAfterBody);
    }
}
