<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="sign_up.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/common.css" type="text/css"/>
</head>
<body class="random-pic">
<jsp:include page="main/header.jsp"/>
<form action="controller">
    <div class="form_registr">
        <input type="hidden" name="command" value="register"/>
        <div class="align_center">
            <label>
                <input class="input-info" type="text" required="required" name="firstname"
                       placeholder="<fmt:message key="sign_up.name.placeholder"/>" value=""
                       pattern="[А-ЯA-Z][а-яa-z]{1,20}"/>
                <fmt:message key="sign_up.name.title"/>
            </label>
            <br/>
            <label>
                <input class="input-info" type="text" required="required" name="lastname"
                       placeholder="<fmt:message key="sign_up.surname.placeholder"/>" value=""
                       pattern="[А-ЯA-Z][а-яa-z]{1,20}"/>
                <fmt:message key="sign_up.surname.title"/>
            </label>
            <br/>
            <label>
                <input class="input-info" type="text" required="required" name="email"
                       placeholder="<fmt:message key="sign_up.email.placeholder"/>"
                       value="" pattern="(([A-Za-z\d._]+){5,25}@([A-Za-z]+){3,7}\.([a-z]+){2,3})"/>
                <fmt:message key="sign_up.email.title"/>
            </label>
            <br/>
            <label>
                <input class="input-info" type="text" required="required" name="birth_date"
                       placeholder="<fmt:message key="sign_up.birth.placeholder"/>" value=""
                       pattern="[0-9]{4}/-[0-9]{2}\-[0-9]{2}"/>
                <fmt:message key="sign_up.birth.title"/>
            </label>
            <br/>
            <label>
                <input class="input-info" type="text" required="required" name="login"
                       placeholder="<fmt:message key="sign_in.login.placeholder"/> " value=""
                       pattern="[a-zA-Z][A-Za-z0-9]{4,29}"/>
                <fmt:message key="sign_in.login.title"/>
            </label>
            <br/>
            <label>
                <input class="input-info" type="password" required="required" name="password"
                       placeholder="<fmt:message key="sign_in.password.placeholder"/> " value=""
                       pattern="[a-zA-Z]*[A-Za-z0-9]{7,29}">
                <fmt:message key="sign_in.password.title"/>
            </label>
            <br/>
            <input class="input-text" type="submit" name="sub" value="<fmt:message key="sign_up.submit"/>"/>
            <br/>
        </div>
    </div>
</form>
</body>
<jsp:include page="main/footer.jsp"/>
</html>
