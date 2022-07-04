<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css" type="text/css"/>
    <title><fmt:message key ="learn_more.title"/></title>
</head>
<body>
<jsp:include page="main/header.jsp"/>
<div><fmt:message key="learn_more.title_info"/></div>
    <br/>
    <input id="error" type="button" value="<fmt:message key="error.back"/>"
           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
    <br/>
</body>
<jsp:include page="main/footer.jsp"/>
</html>
