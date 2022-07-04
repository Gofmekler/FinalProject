<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css" type="text/css"/>
    <title>Error 500</title>
</head>
<body>
<main>
    <div id="error">
        <div id="number">
            <div>500</div>
            <div>Internal Server Error</div>
        </div>
        <div><fmt:message key="error.500.message"/></div>
        <input type="button" value="<fmt:message key="error.back"/>"
               onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
    </div>
    ${pageContext.errorData.requestURI}
    ${pageContext.errorData.servletName}
    ${pageContext.errorData.statusCode}
    ${pageContext.errorData.exception}
</main>
</body>
</html>