<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="header.users"/></title>
</head>
<body>
<div>
    <fmt:message key="users.name"/> <c:out
        value="${requestScope.user_info.firstname} ${sessionScope.user_by_name.lastname}"/><br/>
    <fmt:message key="users.role"/> <c:out value="${sessionScope.user_by_name.role}"/><br/>
    <fmt:message key="users.email"/> <c:out value="${sessionScope.user_by_name.email}"/><br/>
    <fmt:message key="users.login"/> <c:out value="${sessionScope.user_by_name.login}"/><br/>
    <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'BOOKMAKER'}">
        <fmt:message key="users.balance"/> <c:out value="${sessionScope.user_by_name.balance}"/><br/>
    </c:if>
    <form action="controller">
    <input type="hidden" name="command" value="go_to_user_info"/>
    <br/>
    <input type="hidden" name="login" value="${user.login}"/>
    <br/>
    <input type="submit" name="sub" value="<fmt:message key="learn_more.title"/>"/>
</form>
</div>
<c:out value="${sessionScope.user_search_message}"/>
<br/>
<input type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
</html>
