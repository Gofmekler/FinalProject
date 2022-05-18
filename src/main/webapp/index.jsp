<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
    <html>
        <head>
            <title>Welcome Page</title>
        </head>
    <body>
    <input type="button" value="Sign In"
           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_sign_in'">
    <br/>
    <input type="button" value="Sign Up"
           onclick="location.href='${pageContext.request.contextPath}/controller?command=go_to_sign_up'">
    ${login_msg}
    <br/>
    ${register_msg}
    <br/>
    ${error}
    <br/>
</form>
</body>
</html>