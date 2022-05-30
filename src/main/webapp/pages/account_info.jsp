<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Maksim_Maiseichyk
  Date: 27.04.2022
  Time: 22:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Personal Account</title>
</head>
<body>
Hi, ${user_name}
<br/>
${requestScope.user.getLastName}
<br/>
Name: ${user.firstName}
<br/>
Surname: ${user.lastName}
<br/>
Email: ${user.email}
<br/>
Role: ${user.role}
<br/>
Nickname: ${user.login}
<br/>
Balance: ${user.balance}

<c:forEach items="${bets}" var="bet">
    <tr><br/>
        Bet Amount: ${bet.betAmount}<br/>
        Bet Status: ${bet.betStatus}<br/>
    </tr><br/>
</c:forEach>
</body>
</html>
