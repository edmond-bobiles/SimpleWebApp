<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
    <body class="d-flex justify-content-center align-items-center vh-100">
        <div class="card p-4 shadow-lg" style="width: 400px;">
            <h2 class="text-center text-danger mb-4">Error</h2>
            <div class="alert alert-danger text-center">
                <%= request.getAttribute("error") %>
            </div>
            <a href="${pageContext.request.contextPath}/login" class="btn btn-primary w-100">Return to Login</a>
        </div>
    </body>
</html>