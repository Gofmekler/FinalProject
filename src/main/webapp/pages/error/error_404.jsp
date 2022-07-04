<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css" type="text/css"/>
    <title>Error 404</title>
</head>
<body>
<main>
    <div id="error">
        <div id="number">
            <div>404</div>
            <div>Not Found</div>
        </div>
        <div><fmt:message key="error.404.message"/></div>
        <input type="button" value="<fmt:message key="error.back"/>"
               onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
    </div>
</main>
</body>
</html>