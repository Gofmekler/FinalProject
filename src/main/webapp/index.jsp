<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/common.css" type="text/css"/>
    <title><fmt:message key="title"/></title>
</head>
<body class="random-pic">
<div class="padding-top">
    <jsp:include page="pages/main/header.jsp"/>
    <input class="input-text" type="button" value="<fmt:message key="sign_in.title"/>"
           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_sign_in'">
    <br/>
    <input class="input-text" type="button" value="<fmt:message key="sign_up.title"/>"
           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_sign_up'">
</div>
<div class="padding-top">
    <input class="input-text" type="button" value="<fmt:message key="learn_more.title"/>"
           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_learn_more'">
</div>
</body>
<jsp:include page="pages/main/footer.jsp"/>
</html>