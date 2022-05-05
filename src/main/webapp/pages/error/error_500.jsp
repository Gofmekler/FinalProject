<%--
  Created by IntelliJ IDEA.
  User: Maksim_Maiseichyk
  Date: 18.04.2022
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>500</title>
</head>
<body>
500
${error}
${pageContext.errorData.requestURI}
${pageContext.errorData.servletName}
${pageContext.errorData.statusCode}
${pageContext.errorData.exception}

</body>
</html>
