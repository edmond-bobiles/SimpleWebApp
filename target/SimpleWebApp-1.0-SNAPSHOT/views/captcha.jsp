<%@ page contentType="text/html; charset=UTF-8" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>
<%@ page import="java.util.Random" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    // Generate captcha string
    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    StringBuilder captcha = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < 6; i++) {
        captcha.append(chars.charAt(random.nextInt(chars.length())));
    }

    session.setAttribute("captcha", captcha.toString());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CAPTCHA Verification</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex justify-content-center align-items-center vh-100 bg-light">
    <div class="card shadow-lg p-4" style="width: 400px;">
        <h3 class="text-center mb-4">Verification</h3>
        
        <div class="alert alert-secondary text-center fs-4 fw-bold">
            <%= session.getAttribute("captcha") %>
        </div>

        <form action="${pageContext.request.contextPath}/captcha" method="post">
            <div class="mb-3">
                <input type="text" class="form-control" placeholder="Enter captcha" id="userInput" name="userInput" required>
            </div>
            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-primary w-40">Verify</button>
            </div>
        </form>
    </div>
</body>
</html>
