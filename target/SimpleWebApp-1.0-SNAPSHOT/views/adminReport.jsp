<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin Report</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
            body {
                background-color: #f5f8fa;
            }
            .container {
                max-width: 600px;
                margin-top: 20px;
            }
    </style>
</head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid"> 
                <a class="navbar-brand fs-3 ps-3" href="admin">Welcome, <%= session.getAttribute("username")%></a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarText">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0"> 
                        <li class="nav-item">
                            <a class="nav-link fs-4" href="admin">Records</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link fs-4" href="adminReport">Report</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link fs-4" href="signout">Sign Out</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        
        <%-- Code here --%> 
        <div class="container mt-5">  
            <%-- Code here --%> 
            <h1 class="text-center mb-4">Report</h1>
        </div>
    </body>
</html>