<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="account.operations"/></title>
</head>
<body>
<jsp:include page="../main/header.jsp"/>
<c:forEach items="${sessionScope.user_operations}" var="operation">
    <tr><br/>
        <c:out value="${operation.entrySet()}"/>
    </tr>
</c:forEach>
<c:out value="${requestScope.operation_list_message}"/>
<div>
    <form action="controller">
        <input type="hidden" name="command" value="add_bank_card"/>
        <input type="text" required="required" placeholder="<fmt:message key="card.number"/>"
               name="card_number" value="" pattern="\d{16}"/>
        <br/>
        <input type="text" required="required" placeholder="<fmt:message key="card.name"/>"
               name="owner_name" value="" pattern="[A-Z]{3,30}"/>
        <br/>
        <input type="text" required="required" placeholder="<fmt:message key="card.date"/>"
               name="expiration_date" value="" pattern="\d{4}\-\d{2}\-\d{2}"/>
        <br/>
        <input type="text" required="required" placeholder="<fmt:message key="card.cvv"/>"
               name="cvv_number" value="" pattern="\d{3}"/>
        <br/>
        <input type="submit" name="sub" value="<fmt:message key="card.save"/>"/>
    </form>
</div>
<div>
    <form action="controller">
        <input type="hidden" name="command" value="deposit"/>
        <input type="hidden" name="login" value="${sessionScope.user.login}"/>
        <br/>
        <input type="text" required="required" placeholder="<fmt:message key="card.number"/>"
               name="card_number" value="" pattern="\d{16}"/>
        <br/>
        <input type="text" required="required" placeholder="<fmt:message key="card.amount"/>"
               name="operation_amount" value="" pattern="[0-9]{1,4}\.*[0-9]*"/>
        <br/>
        <input type="submit" name="sub" value="<fmt:message key="card.deposit"/>"/>
    </form>
</div>
<div>
    <form action="controller">
        <input type="hidden" name="command" value="withdraw"/>
        <input type="hidden" name="login" value="${sessionScope.user.login}"/>
        <br/>
        <input type="text" required="required" placeholder="<fmt:message key="card.number"/>"
               name="card_number" value="" pattern="\d{16}"/>
        <br/>
        <input type="text" required="required" placeholder="<fmt:message key="card.amount"/>"
               name="operation_amount" value="" pattern="[0-9]{1,4}\.*[0-9]*"/>
        <br/>
        <input type="submit" name="sub" value="<fmt:message key="card.withdraw"/>"/>
    </form>
</div>
<br/>
<input type="button" value="<fmt:message key="error.back"/>"
       onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_home_page'">
<br/>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>
