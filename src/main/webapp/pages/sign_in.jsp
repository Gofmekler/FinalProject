<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title><ftm:message key="sign_in.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/common.css" type="text/css"/>
</head>
<body class="random-pic">
<jsp:include page="main/header.jsp"/>
<br/>
<form action="controller">
    <div class="form_input">
        <h3 class="align_center"><fmt:message key="sign_in.title"/></h3>
        <br/>
        <input type="hidden" name="command" value="login"/>
        <div class="align_center">
            <input class="input-info" type="text" required="required" name="login"
                   title="<fmt:message key="sign_in.login.title"/>"
                   placeholder="<fmt:message key="sign_in.login.placeholder"/>" value=""
                   pattern="[a-zA-Z][A-Za-z0-9]{2,29}"/>
            <br/>
            <input class="input-info" type="password" required="required" name="password" value=""
                   title="<fmt:message key="sign_in.password.title"/>"
                   placeholder="<fmt:message key="sign_in.password.placeholder"/>" pattern="[a-zA-Z]*[A-Za-z0-9]{7,29}">
            <br/>
        </div>
        <div class="padding-top">
            <input class="input-text" type="submit" name="sub" value="<fmt:message key="sign_in.submit"/>"/>
        </div>
        <br/>
    </div>
</form>
</body>
<jsp:include page="main/footer.jsp"/>
</html>