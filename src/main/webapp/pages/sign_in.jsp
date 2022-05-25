<%--
  Created by IntelliJ IDEA.
  User: Maksim_Maiseichyk
  Date: 04.05.2022
  Time: 23:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="controller">
    LOGIN
    <br/>
    <input type="hidden" name="command" value="login"/>
    Login: <input type="text" required= "required" name="login" value="" pattern="[a-zA-Z][A-Za-z0-9]{2,29}"/>
    <br/>
    Password: <input type="password" required="required" name="password" value="">
    <br/>
    <input type="submit" name="sub" value="Submit"/>
    <br/>
    ${login_msg}
</body>
</html>
