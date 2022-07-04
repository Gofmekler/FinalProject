<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="bets.bet"/></title>
</head>
<body>
<div>
    <c:forEach items="${sessionScope.all_bets}" var="bet">
        <br/>
            <fmt:message key="bets.bet_amount"/>: <c:out value="${bet.betAmount}"/><br/>
            <fmt:message key="bets.bet_status"/>: <c:out value="${bet.betStatus}"/><br/>
    </c:forEach>
</div>
<c:out value="${requestScope.bet_search_message}"/>
<br/>
<input type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
</html>
