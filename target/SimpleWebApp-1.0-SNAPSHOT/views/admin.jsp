<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Records</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand fs-3 ps-3" href="admin">
                    Welcome, <%= session.getAttribute("username") %>
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarText">
                    <!-- Right Side -->
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0 pe-3">
                        <li class="nav-item">
                            <a class="nav-link fs-4" href="${pageContext.request.contextPath}/logout">Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

                
        <div class="container mt-5">
            <h1 class="text-center mb-4">User Records</h1>

            <div class="mx-auto" style="width: 600px;">
                <table class="table table-striped text-center">
                    <thead class="table-dark">
                        <tr>
                            <th>Username</th>
                            <th>Role</th>
                            <th>Name</th>
                            <th>Age</th>
                            <th>Country</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            ResultSet results = (ResultSet) request.getAttribute("results");
                            if (results != null) {
                                while (results.next()) {
                        %>
                        <tr>
                            <td><%= results.getString("user_name") %></td>
                            <td><%= results.getString("user_role") %></td>
                            <td><%= results.getString("name") %></td>
                            <td><%= results.getInt("age") %></td>
                            <td><%= results.getString("country") %></td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="3" class="text-center">No records found</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
                    
            <div class="text-center mt-4">
                <a href="${pageContext.request.contextPath}/AdminReportDownload" class="btn btn-primary">
                    Download PDF Report
                </a>
            </div>
        </div>
    </body>
</html>