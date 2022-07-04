<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><fmt:message key="events.title"/></title>
</head>
<body>
<jsp:include page="../main/header.jsp"/>
<c:if test="${sessionScope.user.role eq 'ADMIN'}">
    <form action="controller">
        <input type="hidden" name="command" value="delete_sport_event"/>
        <input type="text" required="required" name="event_id" placeholder="<fmt:message key="events.event_id"/>"
               value=""/>
        <input type="submit" name="sub" value="<fmt:message key="events.delete"/>"/>
    </form>
    <br/>
    <form action="controller">
        <input type="hidden" name="command" value="add_sport_event"/>
        <select name="event_type" size="1">
            <option value="CYBER"><fmt:message key="events.type.cyber"/></option>
            <option selected="selected" value="FOOTBALL"><fmt:message key="events.type.football"/></option>
            <option value="BASKETBALL"><fmt:message key="events.type.basketball"/></option>
            <option value="VOLLEYBALL"><fmt:message key="events.type.volleyball"/></option>
        </select>
        <br/>
        <input type="text" required="required" placeholder="<fmt:message key="events.first_team"/>"
               name="first_team_name" value="" pattern="[A-Z][a-z]{2,10}"/>
        <br/>
        <input type="text" required="required" placeholder="<fmt:message key="events.first_team_ratio"/>"
               name="first_team_ratio" value="" pattern="\d+\.*\d*"/>
        <br/>
        <input type="text" required="required" placeholder="<fmt:message key="events.second_team"/>"
               name="second_team_name" value="" pattern="[A-Z][a-z]{2,10}"/>
        <br/>
        <input type="text" required="required" placeholder="<fmt:message key="events.second_team_ratio"/>"
               name="second_team_ratio" value="" pattern="\d+\.*\d*"/>
        <br/>
        <input type="text" required="required" placeholder="<fmt:message key="events.event_date"/>" name="event_date"
               value="" pattern="\d{4}\-\d{2}\-\d{2}"/>
        <br/>
        <input type="submit" name="sub" value="<fmt:message key="events.save"/>"/>
    </form>
</c:if>
<br/>
<c:forEach items="${sessionScope.events}" var="event">
    <br/>
    <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'BOOKMAKER'}">
        <fmt:message key="events.event_id"/>: ${event.id}<br/>
    </c:if>
    <fmt:message key="events.first_team"/>: <c:out value="${event.firstTeam}"/><br/>
    <fmt:message key="events.second_team"/>: <c:out value="${event.secondTeam}"/><br/>
    <fmt:message key="events.first_team_ratio"/>: <c:out value="${event.firstTeamRatio}"/><br/>
    <fmt:message key="events.second_team_ratio"/>: <c:out value="${event.secondTeamRatio}"/><br/>
    <fmt:message key="events.event_type"/>: <c:out value="${event.eventType}"/><br/>
    <form action="controller">
        <input type="hidden" name="command" value="go_to_event_info"/>
        <br/>
        <input type="hidden" name="event_id" value="${event.id}"/>
        <br/>
        <input type="submit" name="sub" value="<fmt:message key="events.event_info"/>"/>
    </form>
    <c:if test="${sessionScope.user.role eq 'ADMIN'}">
        <form action="controller">
            <input type="hidden" name="command" value="event_release"/>
            <br/>
            <input type="hidden" name="event_id" value="${event.id}"/>
            <br/>
            <input type="submit" name="sub" value="<fmt:message key="events.release"/>"/>
        </form>
    </c:if>
    <c:out value="${sessionScope.bet_error}"/>
    <c:out value="${sessionScope.balance_error}"/>
    <br/>
</c:forEach>
<br/>
<input type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>