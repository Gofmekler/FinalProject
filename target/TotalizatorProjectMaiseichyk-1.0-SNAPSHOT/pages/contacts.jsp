<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<html>
<head>
    <title><ftm:message key="header.contacts"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css" type="text/css"/>
</head>
<body>
<jsp:include page="main/header.jsp"/>
<div>
    <fmt:message key="contacts.mail"/>: gofmekler@gmail.com<br/>
    <fmt:message key="contacts.support"/>: supportBet@gmail.com<br/>
    <fmt:message key="contacts.work"/><br/>
</div>
<br/>
<input class="input-text" type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
<jsp:include page="main/footer.jsp"/>
</html>
