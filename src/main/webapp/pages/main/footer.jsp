<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<footer>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/common.css" type="text/css"/>
    <div class="footer">
        <fmt:message key="title"/>
        <fmt:message key="footer.copyright"/>
    </div>
</footer>
