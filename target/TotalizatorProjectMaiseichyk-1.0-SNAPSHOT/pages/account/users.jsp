<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><fmt:message key="users.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/common.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/users.css" type="text/css"/>
</head>
<body>
<c:if test="${sessionScope.user.role eq 'ADMIN'}">
    <form action="controller">
        <input type="hidden" name="command" value="add_user"/>
        <select name="role" size="1">
            <option value="CLIENT"><fmt:message key="users.client"/></option>
            <option selected="selected" value="ADMIN"><fmt:message key="users.admin"/></option>
            <option value="BOOKMAKER"><fmt:message key="users.bookmaker"/></option>
        </select>
        <br/>
        <input class="input-info" type="text" required="required" placeholder="<fmt:message key="users.login"/>"
               name="login" value="" pattern="[a-zA-Z][A-Za-z0-9]{4,29}"/>
        <br/>
        <input class="input-info" type="password" required="required" name="password"
               placeholder="<fmt:message key="sign_in.password.placeholder"/> " value=""
               pattern="[a-zA-Z]*[A-Za-z0-9]{7,29}">
        <input class="input-info" type="text" required="required"
               placeholder="<fmt:message key="sign_up.name.placeholder"/>"
               name="name" value="" pattern="[А-ЯA-Z][а-яa-z]{1,20}"/>
        <br/>
        <input class="input-info" type="text" required="required"
               placeholder="<fmt:message key="sign_up.surname.placeholder"/>"
               name="lastname" value="" pattern="[А-ЯA-Z][а-яa-z]{1,20}"/>
        <br/>
        <input class="input-info" type="text" required="required" placeholder="<fmt:message key="users.email"/>"
               name="email" value="" pattern="(([A-Za-z\d._]+){5,25}@([A-Za-z]+){3,7}\.([a-z]+){2,3})"/>
        <br/>
        <input class="input-info" type="text" required="required"
               placeholder="<fmt:message key="sign_up.birth.placeholder"/>"
               name="birth_date" pattern="[0-9]{4}/-[0-9]{2}\-[0-9]{2}"
               value=""/>
        <br/>
        <input class="input-text" type="submit" name="sub" value="<fmt:message key="users.save"/>"/>
    </form>
</c:if>
<table>
    <div class="list1a">
        <c:forEach items="${sessionScope.users}" var="user">
            <fmt:message key="users.name"/>
            <li>
                    <c:out value="${user.firstname} ${user.lastname}"/>
            <li/>
            <fmt:message key="users.role"/>
            <c:out value="${user.role}"/>
            <fmt:message key="users.email"/>
            <c:out value="${user.email}"/>
            <fmt:message key="users.login"/>
            <c:out value="${user.login}"/>
            <fmt:message key="users.balance"/>
            <c:out value="${user.balance}"/>
            <form action="controller">
                <input type="hidden" name="command" value="go_to_user_info"/>
                <br/>
                <input type="hidden" name="login" value="${user.login}"/>
                <br/>
                <input type="submit" name="sub" value="<fmt:message key="learn_more.title"/>"/>
            </form>
        </c:forEach>
    </div>
</table>
<br/>
<input type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
</html>
