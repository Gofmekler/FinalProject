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
${events}
<br/>
<form action="controller">
    <input type="hidden" name="command" value="delete_sport_event_command"/>
    Id: <input type="text" required= "required" name="unique_event_id" value=""/>
    <input type="submit" name="sub" value="Delete" />
</form>
<br/>
<input type="button" value="Back to home page"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
</html>