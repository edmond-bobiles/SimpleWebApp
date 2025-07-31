<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ page import="java.util.Random" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    // Generates captcha
    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    StringBuilder captcha = new StringBuilder();
    Random random = new Random();

    // Generated CAPTCHA is of length 6
    for (int i = 0; i < 6; i++) {
        captcha.append(chars.charAt(random.nextInt(chars.length())));
    }

    session.setAttribute("captcha", captcha.toString());
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CAPTCHA Page</title>
    </head>
    <body>
        <h1>Captcha</h1>
        <b><%= session.getAttribute("captcha")    %> </b>
        
        <br><br>
        <form action="${pageContext.request.contextPath}/captcha" method="post">
            Enter CAPTCHA <input type="text" name="userInput"> <br><br>
            <input type="submit" value="Verify">
        </form>
    </body>
</html>