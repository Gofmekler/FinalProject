<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="header.events"/></title>
</head>
<body>
<div>
    <c:forEach items="${sessionScope.events_by_name}" var="event">
        <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'BOOKMAKER'}">
            <fmt:message key="events.event_id"/>: <c:out value="${event.id}"/><br/>
        </c:if>
        <fmt:message key="events.first_team"/>: <c:out value="${event.firstTeam}"/><br/>
        <fmt:message key="events.second_team"/>: <c:out value="${event.secondTeam}"/><br/>
        <fmt:message key="events.first_team_ratio"/>: <c:out value="${event.firstTeamRatio}"/><br/>
        <fmt:message key="events.second_team_ratio"/>: <c:out value="${event.secondTeamRatio}"/><br/>
        <fmt:message key="events.event_type"/>: <c:out value="${event.eventType}"/><br/>
        <fmt:message key="events.event_result"/>: <c:out value="${event.eventResult}"/><br/>
        <form action="controller">
            <input type="hidden" name="command" value="go_to_event_info"/>
            <br/>
            <input type="hidden" name="event_id" value="${event.id}"/>
            <br/>
            <input type="submit" name="sub" value="<fmt:message key="events.event_info"/>"/>
        </form>
    </c:forEach>
</div>
<c:out value="${requestScope.event_search_message}"/>
<br/>
<input type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
</html>
