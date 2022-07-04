<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css" type="text/css"/>
    <title>Error 403</title>
</head>
<body>
<main>
    <div id="error">
        <div id="number">
            <div>403</div>
            <div>Forbidden</div>
        </div>
        <div><fmt:message key="error.403.message"/></div>
        <input type="button" value="<fmt:message key="error.back"/>"
               onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
    </div>
</main>
</body>
</html>