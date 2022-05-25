<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Maksim_Maiseichyk
  Date: 05.05.2022
  Time: 13:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Users</title>
</head>
<body>
<table>
<c:forEach items="${users}" var="user">
    <tr><br/>
       Name: ${user.firstName}<br/>
       Surname: ${user.lastName}<br/>
       Role: ${user.role}<br/>
       Email: ${user.email}<br/>
       Login: ${user.login}<br/>
       Balance: ${user.balance}<br/>
    </tr><br/>
</c:forEach>
</table>
</body>
</html>
