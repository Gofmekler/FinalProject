<%--
  Created by IntelliJ IDEA.
  User: Maksim_Maiseichyk
  Date: 18.04.2022
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404</title>
</head>
<body>
404
${pageContext.errorData.requestURI}
${pageContext.errorData.servletName}
${pageContext.errorData.statusCode}
${pageContext.errorData.exception}
</body>
</html>