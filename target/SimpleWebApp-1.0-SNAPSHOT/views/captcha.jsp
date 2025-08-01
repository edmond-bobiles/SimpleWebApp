<%@ page contentType="text/html; charset=UTF-8" %>
<%
    // Set cache control headers
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>reCAPTCHA Verification</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body class="d-flex justify-content-center align-items-center vh-100 bg-light">
    <div class="card shadow-lg p-4" style="width: 400px;">
        <div class="text-center mb-4">
            <h3>Verification</h3>
        </div>
        
        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <div class="alert alert-danger text-center mb-3">
                <%= error %>
            </div>
        <% } %>
        
        <form id="captcha-form" action="${pageContext.request.contextPath}/captcha" method="post">
            <!-- Google reCAPTCHA widget -->
            <div class="mb-4 d-flex justify-content-center">
                <div class="g-recaptcha" data-sitekey="6LdZwZYrAAAAAIERVbmC5c9z0fkvHBUiGt4hZhPC"></div>
            </div>
            
            <div class="d-flex justify-content-center gap-3">
                <button type="submit" id="submit-btn" class="btn btn-primary px-4">Verify & Continue</button>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger px-4">Logout</a>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
