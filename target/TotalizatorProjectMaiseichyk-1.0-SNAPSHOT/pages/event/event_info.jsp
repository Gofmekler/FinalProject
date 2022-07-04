<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="events.event_info"/></title>
</head>
<body>
<jsp:include page="../main/header.jsp"/>
<div>
    <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'BOOKMAKER'}">
        <fmt:message key="events.event_id"/>: <c:out value="${requestScope.event.id}"/><br/>
    </c:if>
    <fmt:message key="events.first_team"/>: <c:out value="${requestScope.event.firstTeam}"/><br/>
    <fmt:message key="events.second_team"/>: <c:out value="${requestScope.event.secondTeam}"/><br/>
    <fmt:message key="events.first_team_ratio"/>: <c:out value="${requestScope.event.firstTeamRatio}"/><br/>
    <fmt:message key="events.second_team_ratio"/>: <c:out value="${requestScope.event.secondTeamRatio}"/><br/>
    <fmt:message key="events.event_type"/>: <c:out value="${requestScope.event.eventType}"/><br/>
    <fmt:message key="events.event_result"/>: <c:out value="${requestScope.event.eventResult}"/><br/>
</div>
<c:out value="${requestScope.event_search_message}"/>
<c:if test="${sessionScope.user.role eq 'ADMIN'}">
    <form action="controller">
        <input type="hidden" name="command" value="delete_sport_event"/>
        <input type="text" required="required" name="event_id" placeholder="<fmt:message key="events.event_id"/>"
               value="" pattern="\d+"/>
        <input type="submit" name="sub" value="<fmt:message key="events.delete"/>"/>
    </form>
<%--    <form action="controller">--%>
<%--        <input type="hidden" name="command" value="update_sport_event"/>--%>
<%--        <select name="event_type" size="1">--%>
<%--            <option value="CYBER"><fmt:message key="events.type.cyber"/></option>--%>
<%--            <option selected="selected" value="FOOTBALL"><fmt:message key="events.type.football"/></option>--%>
<%--            <option value="BASKETBALL"><fmt:message key="events.type.basketball"/></option>--%>
<%--            <option value="VOLLEYBALL"><fmt:message key="events.type.volleyball"/></option>--%>
<%--        </select>--%>
<%--        <br/>--%>
<%--        <input type="text" required="required" placeholder="<fmt:message key="events.first_team"/>"--%>
<%--               name="first_team_name" value="" pattern="[A-Z][a-z]{2,10}"/>--%>
<%--        <br/>--%>
<%--        <input type="text" required="required" placeholder="<fmt:message key="events.first_team_ratio"/>"--%>
<%--               name="first_team_ratio" value="" pattern="\d+\.*\d*"/>--%>
<%--        <br/>--%>
<%--        <input type="text" required="required" placeholder="<fmt:message key="events.second_team"/>"--%>
<%--               name="second_team_name" value="" pattern="[A-Z][a-z]{2,10}"/>--%>
<%--        <br/>--%>
<%--        <input type="text" required="required" placeholder="<fmt:message key="events.second_team_ratio"/>"--%>
<%--               name="second_team_ratio" value="" pattern="\d+\.*\d*"/>--%>
<%--        <br/>--%>
<%--        <input type="text" required="required" placeholder="<fmt:message key="events.event_date"/>" name="event_date"--%>
<%--               value="" pattern="\d{4}\-\d{2}\-\d{2}"/>--%>
<%--        <br/>--%>
<%--        <input type="submit" name="sub" value="<fmt:message key="events.save"/>"/>--%>
<%--    </form>--%>
</c:if>
<form action="controller">
    <input type="hidden" name="command" value="bet"/>
    Bet amount: <input type="number" required="required" name="bet_amount">
    <select name="bet_chosen_team" size="1">
        <option selected="selected" value="${requestScope.event.firstTeam}">${requestScope.event.firstTeam}
        </option>
        <option value="${requestScope.event.secondTeam}">${requestScope.event.secondTeam}
        </option>
    </select>
    <input type="hidden" name="user_login" value="${sessionScope.user.login}"/>
    <br/>
    <input type="hidden" name="event_id" value="${requestScope.event.id}"/>
    <br/>
    <input type="submit" name="sub" value="<fmt:message key="bets.bet"/>"/>
</form>
<br/>
<input type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>
