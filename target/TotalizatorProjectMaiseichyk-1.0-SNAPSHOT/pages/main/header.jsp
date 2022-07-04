<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<head>
    <meta charset="utf8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/header.css" type="text/css"/>
    <title><fmt:message key="title"/></title>
</head>
<header>
    <div class="language">
        <ln>${sessionScope.language}</ln>
        <ln><a href="${pageContext.request.contextPath}/controller?command=change_locale&language=EN">EN</a></ln>
        <ln><a href="${pageContext.request.contextPath}/controller?command=change_locale&language=RU">RU</a></ln>
        <ln><a href="${pageContext.request.contextPath}/controller?command=go_to_admin"><fmt:message
                key="header.admins"/></a></ln>
        <ln><a href="${pageContext.request.contextPath}/controller?command=go_to_bookmaker"><fmt:message
                key="header.bookmakers"/></a></ln>
        <ln><a href="${pageContext.request.contextPath}/controller?command=go_to_contacts"><fmt:message
                key="header.contacts"/></a></ln>
        <ln><a href="${pageContext.request.contextPath}/controller?command=go_to_demo"><fmt:message
                key="header.demo"/></a></ln>
    </div>
</header>
