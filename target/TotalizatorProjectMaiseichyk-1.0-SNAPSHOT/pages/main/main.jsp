<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<input type="button" value="<fmt:message key="header.sign_out"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=logout'">
<input type="button" value="<fmt:message key="account.update.navigation.personal"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_account_info'">
<br/>
<input type="button" value="<fmt:message key="header.users"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_user_list'">
<br/>
<input type="button" value="<fmt:message key="header.events"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_event'">
<br/>
<input type="button" value="<fmt:message key="events.past_event"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_past_event'">
<br/>
<input type="button" value="<fmt:message key="bets.title"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_bet_list'">
<br/>
</body>
<jsp:include page="footer.jsp"/>
</html>