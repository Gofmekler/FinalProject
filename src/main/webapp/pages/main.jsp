<%--
  Created by IntelliJ IDEA.
  User: Maksim_Maiseichyk
  Date: 18.04.2022
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
    Hello, ${user_name}
    <hr/>
    <form action="controller">
        <input type="hidden" name="command" value="logout"/>
        <input type="submit" value="Logout"/>
    </form>
    <input type="button" value="Account info"
           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_account_info'">
<br/>
    <input type="button" value="Users"
           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_user_list'">
<br/>
    <input type="button" value="Events"
           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_event'">
    <br/>
${err_msg}
<br/>
</body>
</html>