<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="account.update.navigation.personal"/></title>
</head>
<body>
<c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'BOOKMAKER'}">
    <input class="input-text" type="button" value="<fmt:message key="operations.list"/>"
           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_card_operations'">
    <br/>
</c:if>
<div class="padding-top">
    <form action="controller">
        <input type="hidden" name="command" value="go_to_user_operations"/>
        <br/>
        <input type="hidden" name="login" value="${sessionScope.user.login}"/>
        <br/>
        <input type="submit" name="sub" value="<fmt:message key="account.operations"/>"/>
    </form>
</div>
<br/>
<div>
    <fmt:message key="users.name"/> <c:out
        value="${sessionScope.user.firstname} ${sessionScope.user.lastname}"/><br/>
    <fmt:message key="users.role"/> <c:out value="${sessionScope.user.role}"/><br/>
    <fmt:message key="users.email"/> <c:out value="${sessionScope.user.email}"/><br/>
    <fmt:message key="users.login"/> <c:out value="${sessionScope.user.login}"/><br/>
    <fmt:message key="users.balance"/> <c:out value="${sessionScope.user.balance}"/><br/>
</div>
<div>
    <c:forEach items="${sessionScope.bets}" var="bet">
        <tr><br/>
            <fmt:message key="bets.bet_amount"/>: <c:out value="${bet.betAmount}"/><br/>
            <fmt:message key="bets.bet_status"/>: <c:out value="${bet.betStatus}"/><br/>
            <fmt:message key="bets.win_coefficient"/>: <c:out value="${bet.winCoefficient}"/><br/>
        </tr>
        <br/>
    </c:forEach>
</div>
<div>
<%--    <form action="controller">--%>
<%--        <input type="hidden" name="command" value="update_user"/>--%>
<%--        <c:if test="${sessionScope.user.role eq 'ADMIN'}">--%>
<%--            <select name="role" size="1">--%>
<%--                <option value="CLIENT"><fmt:message key="users.client"/></option>--%>
<%--                <option selected="selected" value="ADMIN"><fmt:message key="users.admin"/></option>--%>
<%--                <option value="BOOKMAKER"><fmt:message key="users.bookmaker"/></option>--%>
<%--            </select>--%>
<%--        </c:if>--%>
<%--        <input type="text" required="required" placeholder="<fmt:message key="users.login"/>"--%>
<%--               name="login" value="" pattern="[a-zA-Z][A-Za-z0-9]{4,29}"/>--%>
<%--        <br/>--%>
<%--        <input type="text" required="required" placeholder="<fmt:message key="sign_up.name.placeholder"/>"--%>
<%--               name="name" value="" pattern="[А-ЯA-Z][а-яa-z]{1,20}"/>--%>
<%--        <br/>--%>
<%--        <input type="text" required="required" placeholder="<fmt:message key="sign_up.surname.placeholder"/>"--%>
<%--               name="lastname" value="" pattern="[А-ЯA-Z][а-яa-z]{1,20}"/>--%>
<%--        <br/>--%>
<%--        <input type="text" required="required" placeholder="<fmt:message key="users.email"/>"--%>
<%--               name="email" value="" pattern="(([A-Za-z\d._]+){5,25}@([A-Za-z]+){3,7}\.([a-z]+){2,3})"/>--%>
<%--        <br/>--%>
<%--        <input type="text" required="required" placeholder="<fmt:message key="sign_up.birth.placeholder"/>"--%>
<%--               name="birth_date" pattern="[0-9]{4}/-[0-9]{2}\-[0-9]{2}"--%>
<%--               value=""/>--%>
<%--        <br/>--%>
<%--        <input type="submit" name="sub" value="<fmt:message key="users.save"/>"/>--%>
<%--    </form>--%>
</div>
<br/>
<input class="input-text" type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
</html>
