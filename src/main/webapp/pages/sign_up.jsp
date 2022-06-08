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
    REGISTER
    <br/>
    <input type="hidden" name="command" value="register"/>
    First Name: <input type="text" required= "required" name="first_name" value=""/>
    <br/>
    Last Name: <input type="text" required= "required" name="last_name" value=""/>
    <br/>
    Email: <input type="text" required= "required" name="email" value=""/>
    <br/>
<%--    Date of birth: <input type="text" required= "required" name="date_of_birth" value=""/>--%>
<%--    <br/>--%>
    Login: <input type="text" required= "required" name="login" value=""/>
    <br/>
    Password: <input type="password" required="required" name="password" value="">
    <br/>
    <input type="submit" name="sub" value="Submit"/>
    <br/>
    ${register_msg}
    <br/>
</form>
</body>
</html>
