<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="users.title"/></title>
</head>
<body>
<div class="list1a">
    <li><fmt:message key="users.name"/> <c:out
        value="${requestScope.user_info.firstname} ${requestScope.user_info.lastname}"/>
    <fmt:message key="users.role"/> <c:out value="${requestScope.user_info.role}"/>
    <fmt:message key="users.email"/> <c:out value="${requestScope.user_info.email}"/>
    <fmt:message key="users.login"/> <c:out value="${requestScope.user_info.login}"/><li/>
    <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'BOOKMAKER'}">
    <li> <fmt:message key="users.balance"/> <c:out value="${requestScope.user_info.balance}"/><li/>
    </c:if>
</div>
<c:if test="${sessionScope.user.role eq 'ADMIN'}">
    <form action="controller">
        <input type="hidden" name="command" value="delete_user"/>
        <input type="text" required="required" name="login" placeholder="<fmt:message key="users.login"/>"
               value="" pattern="[a-zA-Z][A-Za-z0-9]{4,29}"/>
        <input type="submit" name="sub" value="<fmt:message key="users.delete"/>"/>
    </form>
    <form action="controller">
        <input type="hidden" name="command" value="update_user_info"/>
        <select name="role" size="1">
            <option value="CLIENT"><fmt:message key="users.client"/></option>
            <option selected="selected" value="ADMIN"><fmt:message key="users.admin"/></option>
            <option value="BOOKMAKER"><fmt:message key="users.bookmaker"/></option>
        </select>
        <br/>
        <input type="text" placeholder="<fmt:message key="users.login"/>"
               name="login" value="" pattern="[a-zA-Z][A-Za-z0-9]{4,29}"/>
        <br/>
        <input type="text" placeholder="<fmt:message key="sign_up.name.placeholder"/>"
               name="name" value="" pattern="[А-ЯA-Z][а-яa-z]{1,20}"/>
        <br/>
        <input type="text" placeholder="<fmt:message key="sign_up.surname.placeholder"/>"
               name="lastname" value="" pattern="[А-ЯA-Z][а-яa-z]{1,20}"/>
        <br/>
        <input type="text" placeholder="<fmt:message key="users.email"/>"
               name="email" value="" pattern="(([A-Za-z\d._]+){5,25}@([A-Za-z]+){3,7}\.([a-z]+){2,3})"/>
        <br/>
        <input type="text" placeholder="<fmt:message key="sign_up.birth.placeholder"/>"
               name="birth_date" pattern="[0-9]{4}/-[0-9]{2}\-[0-9]{2}"
               value=""/>
        <br/>
        <input type="submit" name="sub" value="<fmt:message key="users.save"/>"/>
    </form>
</c:if>
</div>
<br/>
<input type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
</html>
