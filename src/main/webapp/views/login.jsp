<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="views/css/style.css">
    <title>Login | Snailbob</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<!-- Main Container -->
<div class="container d-flex justify-content-center align-items-center min-vh-100">

    <!-- Login Card Container -->
    <div class="row border rounded-5 p-3 bg-white shadow box-area" style="max-width: 850px; width: 100%;">

        <!-- Left Box with Image -->
        <div class="col-md-6 rounded-4 d-flex justify-content-center align-items-center left-box">
            <img src="views/images/snailbob.png" class="img-fluid rounded-4" style="max-width: 100%; max-height: 100%;">
        </div>

        <!-- Right Box (Login Form) -->
        <div class="col-md-6 right-box">
            <div class="row align-items-center px-4">

                <div class="header-text mb-4 text-center">
                    <h2>Login</h2>
                    <p>Snail Bob says hi!</p>
                </div>

                <% String error = (String) request.getAttribute("error"); %>
                <% if (error != null) { %>
                    <div class="alert alert-danger text-center">
                        <%= error %>
                    </div>
                <% } %>

                <form action="${pageContext.request.contextPath}/login" method="POST">
                    <div class="input-group mb-3">
                        <input type="text"
                               class="form-control form-control-lg bg-light fs-6"
                               placeholder="Username"
                               name="username"
                               required>
                    </div>
                    <div class="input-group mb-3">
                        <input type="password"
                               class="form-control form-control-lg bg-light fs-6"
                               placeholder="Password"
                               name="password"
                               required>
                    </div>
                    <div class="input-group mb-3 justify-content-center">
                        <button type="submit" class="btn btn-lg btn-primary w-50 fs-6">Login</button>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>

<!-- Bootstrap Bundle JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
