<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="title"/></title>
</head>
<body>
<div><fmt:message key="operation.success"/></div>
<main>
    <input type="button" value="<fmt:message key="error.back"/>"
           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
</main>
</body>
</html>
