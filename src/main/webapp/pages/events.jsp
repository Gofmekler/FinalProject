<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Maksim_Maiseichyk
  Date: 05.05.2022
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EVENTS</title>
</head>
<body>
<c:if test="${user.role eq 'ADMIN'}">
    <form action="controller">
        <input type="hidden" name="command" value="delete_sport_event"/>
        Id: <input type="text" required="required" name="unique_event_id" value=""/>
        <input type="submit" name="sub" value="Delete"/>
    </form>
    ${command_sport_event_msg}
    <br/>
    <form action="controller">
        <input type="hidden" name="command" value="add_sport_event"/>
<%--        Id: <input type="text" required="required" name="unique_event_id" value=""/>--%>
<%--        <br/>--%>
        Event Type: <select name="event_type" size="1">
        <option value="CYBER">CYBER</option>
        <option selected="selected" value="FOOTBALL">FOOTBALL</option>
        <option value="BASKETBALL">BASKETBALL</option>
        <option value="VOLLEYBALL">VOLLEYBALL</option>
    </select>
        <br/>
        First Team: <input type="text" required="required" name="first_team" value=""/>
        <br/>
        First Team Ratio: <input type="text" required="required" name="first_team_ratio" value=""/>
        <br/>
        Second Team: <input type="text" required="required" name="second_team" value=""/>
        <br/>
        Second Team Ratio: <input type="text" required="required" name="second_team_ratio" value=""/>
        <br/>
        Date: <input type="text" required="required" name="event_date" value=""/>
        <br/>
        <input type="submit" name="sub" value="Insert new event"/>
    </form>
</c:if>
${bet_msg}
<br/>
<c:forEach items="${events}" var="event">
    <tr><br/>
        <c:if test="${user.role eq 'ADMIN' or user.role eq 'BOOKMAKER'}">
            Event ID: ${event.uniqueEventId}<br/>
        </c:if>
        1st Team: ${event.firstTeam}<br/>
        2nd Team: ${event.secondTeam}<br/>
        1st Ratio: ${event.firstTeamRatio}<br/>
        2nd Ratio: ${event.secondTeamRatio}<br/>
        Event Type: ${event.eventType}<br/>
        <form action="controller">
            <input type="hidden" name="command" value="bet"/>
            Bet amount: <input type="number" required="required" name="bet_amount">
            <select name="bet_chosen_team" size="1">
                <option selected="selected" value="${event.firstTeam}">Bet for ${event.firstTeam}
                        <%--                <input type="hidden" name="win_coefficient" value="${event.firstTeamRatio}"/>--%>
                </option>
                <option value="${event.secondTeam}">Bet for ${event.secondTeam}
                        <%--                <input type="hidden" name="win_coefficient" value="${event.secondTeamRatio}"/>--%>
                </option>
            </select>
            <input type="hidden" name="user_login" value="${user.login}"/>
            <br/>
            <input type="hidden" name="event_id" value="${event.uniqueEventId}"/>
            <br/>
            <input type="submit" name="sub" value="BET"/>
        </form>
        <c:if test="${user.role eq 'ADMIN' or user.role eq 'BOOKMAKER'}">
            <form action="controller">
                <input type="hidden" name="command" value="event_release"/>
                <br/>
                <input type="hidden" name="event_id" value="${event.uniqueEventId}"/>
                <br/>
                <input type="submit" name="sub" value="Release Event"/>
            </form>
        </c:if>
    </tr>
    <br/>
</c:forEach>
<br/>
<input type="button" value="Back to home page"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
</html>