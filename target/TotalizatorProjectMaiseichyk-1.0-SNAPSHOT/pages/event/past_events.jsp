<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="events.past_event"/></title>
</head>
<body>
<jsp:include page="../main/header.jsp"/>
<c:forEach items="${sessionScope.past_events}" var="event">
    <tr><br/>
        <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'BOOKMAKER'}">
            <fmt:message key="events.event_id"/>: <c:out value="${event.id}"/><br/>
        </c:if>
        <fmt:message key="events.first_team"/>: <c:out value="${event.firstTeam}"/><br/>
        <fmt:message key="events.second_team"/>: <c:out value="${event.secondTeam}"/><br/>
        <fmt:message key="events.first_team_ratio"/>: <c:out value="${event.firstTeamRatio}"/><br/>
        <fmt:message key="events.second_team_ratio"/>: <c:out value="${event.secondTeamRatio}"/><br/>
        <fmt:message key="events.event_type"/>: <c:out value="${event.eventType}"/><br/>
        <fmt:message key="events.event_result"/>: <c:out value="${event.eventResult}"/><br/>
    </tr>
</c:forEach>
<br/>
<input type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>
