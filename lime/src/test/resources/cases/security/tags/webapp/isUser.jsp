<%@ taglib prefix="sec" uri="/WEB-INF/security.tld" %>

<sec:isUser users="SimpleName">
    PASS1
</sec:isUser>

<sec:isUser users="ADMIN">
    ADMIN
</sec:isUser>

<sec:isUser users="FOO,SimpleName">
    PASS2
</sec:isUser>

<sec:isUser users="ADMIN,MANAGER">
    ADMIN
</sec:isUser>