/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zdevra.guice.mvc.security.tags;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zdevra.guice.mvc.security.WebPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Tadas
 */
public class SecurityRoleHandlerTest {


    private SecurityRoleHandler instance;

    @Before
    public void setUp() {
        instance = new SecurityRoleHandler();
        final WebPrincipal user = createMock(WebPrincipal.class);
        final PageContext pageContext = createMock(PageContext.class);
        final HttpSession httpSession = createMock(HttpSession.class);
        final HttpServletRequest httpServletRequest = createMock(HttpServletRequest.class);
        final BodyContent bodyContent = createMock(BodyContent.class);
        final Set<String> roles = new HashSet<String>(Arrays.asList(new String[]{"user", "dad"}));


        expect(user.getName()).andStubReturn("");
        expect(user.getRoles()).andReturn(roles).anyTimes();
        expect(pageContext.getRequest()).andReturn(httpServletRequest).anyTimes();
        expect(httpServletRequest.getUserPrincipal()).andReturn(user).anyTimes();


        replay(user);
        replay(pageContext);
        replay(httpServletRequest);

        instance.setPageContext(pageContext);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of doStartTag method, of class SecurityRoleHandler.
     */
    @Test
    public void testDoStartTag() throws Exception {
        System.out.println("doStartTag");


        instance.setRoles("go,no");


        int doStartTag = instance.doStartTag();

        assertEquals(Tag.SKIP_BODY, doStartTag);


        instance.setRoles("user,no");
        doStartTag = instance.doStartTag();
        assertEquals(BodyTag.EVAL_BODY_BUFFERED, doStartTag);

        instance.setRoles("dad");
        doStartTag = instance.doStartTag();
        assertEquals(BodyTag.EVAL_BODY_BUFFERED, doStartTag);


    }

    /**
     * Test of doEndTag method, of class SecurityRoleHandler.
     */
    @Test
    public void testDoEndTag() throws Exception {
        System.out.println("doEndTag");

        instance.setRoles("go,no");

        int doEndTag = instance.doEndTag();

        assertEquals(Tag.EVAL_PAGE, doEndTag);

        instance.setRoles("go,no");
        doEndTag = instance.doEndTag();
        assertEquals(BodyTag.EVAL_PAGE, doEndTag);

        instance.setRoles("dad");
        doEndTag = instance.doEndTag();
        assertEquals(BodyTag.EVAL_PAGE, doEndTag);
    }

    /**
     * Test of doAfterBody method, of class SecurityRoleHandler.
     */
    @Test
    public void testDoAfterBody() throws Exception {
        System.out.println("testDoAfterBody");
        final BodyContent bodyContent = createNiceMock(BodyContent.class);
        replay(bodyContent);

        instance.setBodyContent(bodyContent);
        instance.setRoles("go,no");

        int doAfterBody = instance.doAfterBody();

        assertEquals(Tag.SKIP_BODY, doAfterBody);
    }
}
