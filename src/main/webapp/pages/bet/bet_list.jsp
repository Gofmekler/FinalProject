<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><fmt:message key="bets.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/users.css" type="text/css"/>
</head>
<body>
<jsp:include page="../main/header.jsp"/>
<div class="list1a">
    <c:forEach items="${sessionScope.all_bets}" var="bet">
        <fmt:message key="bets.bet"/><li>: <c:out value="${bet.id}"/><li/>
        <fmt:message key="bets.event_id"/>: <c:out value="${bet.sportEventId}"/>
        <fmt:message key="bets.login"/>: <c:out value="${bet.userLogin}"/>
        <fmt:message key="bets.bet_amount"/>: <c:out value="${bet.betAmount}"/>
        <fmt:message key="bets.chosen_team"/>: <c:out value="${bet.chosenTeam}"/>
    </c:forEach>
    <br/>
</div>
<input type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>
