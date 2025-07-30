
<%-- 
    Document   : errorCaptcha
    Created on : 07 29, 25, 8:50:49 PM
    Author     : Sydney Padua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Captcha</title>
    </head>
    <body>
        <h1>Incorrect Captcha</h1>
         <form action="views/captcha.jsp" method="post">
            <input type="submit" value="Return">
        </form>
    </body>
</html>
