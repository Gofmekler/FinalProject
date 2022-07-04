<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="home.title"/></title>
</head>
<body>
<jsp:include page="main/header.jsp"/>
<div id="event">
    <c:forEach items="${sessionScope.events}" var="event">
    <tr><br/>
            <fmt:message key="events.first_team"/>: <c:out value="${event.firstTeam}"/><br/>
            <fmt:message key="events.second_team"/>: <c:out value="${event.secondTeam}"/><br/>
            <fmt:message key="events.first_team_ratio"/>: <c:out value="${event.firstTeamRatio}"/><br/>
            <fmt:message key="events.second_team_ratio"/>: <c:out value="${event.secondTeamRatio}"/><br/>
            <fmt:message key="events.event_type"/>: <c:out value="${event.eventType}"/><br/>
        </c:forEach>
</div>
<div>
    <form method="get" action="${pageContext.request.contextPath}/index.jsp">
        <input type="submit" value="<fmt:message key="error.back"/>"/>
    </form>
</div>
</body>
<jsp:include page="main/footer.jsp"/>
</html>
