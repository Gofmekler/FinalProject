<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="header.bookmakers"/></title>
</head>
<body>
<jsp:include page="main/header.jsp"/>
<c:forEach items="${sessionScope.bookmakers}" var="user">
    <tr><br/>
        <fmt:message key="users.name"/>: <c:out value="${user.firstname}"/><br/>
        <fmt:message key="users.email"/>: <c:out value="${user.email}"/><br/>
    </tr>
</c:forEach>
<c:out value="${requestScope.users_list_messager}"/>
<br/>
<input type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
<jsp:include page="main/footer.jsp"/>
</html>
