<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f5f8fa;
                padding-bottom: 50px;
            }
            .container {
                max-width: 800px;
                margin-top: 20px;
            }
            .user-box {
                background: white;
                padding: 15px;
                border-radius: 10px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                margin-bottom: 10px;
                transition: transform 0.2s;
            }
            .user-box:hover {
                transform: translateY(-3px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.15);
            }
            .user-name {
                font-weight: bold;
                color: #333;
                font-size: 1.2rem;
            }
            .user-detail {
                margin-top: 8px;
                color: #555;
            }
            h2 {
                text-align: center;
                margin-top: 30px;
                margin-bottom: 25px;
            }
            .user-age {
                color: #6c757d;
            }
            .user-country {
                color: #28a745;
                font-style: italic;
            }
            .debug-info {
                background-color: #f8f9fa;
                padding: 15px;
                border-radius: 5px;
                margin-top: 30px;
                font-family: monospace;
                font-size: 0.9rem;
            }
            .record-count {
                background-color: #e9ecef;
                padding: 5px 10px;
                border-radius: 20px;
                font-size: 0.8rem;
                margin-left: 10px;
            }
        </style>
    </head>
    <body>
        <h2>All User Records <span class="record-count" id="recordCount"></span></h2>
        <div class="container">
            <%
                ResultSet users = (ResultSet) request.getAttribute("users");
                int recordCount = 0;
                
                if (users != null) {
                    // First pass: count records
                    users.beforeFirst();
                    while (users.next()) {
                        recordCount++;
                    }
                    
                    // Second pass: display records
                    users.beforeFirst();
                    if (recordCount > 0) {
                        while (users.next()) {
            %>
            <div class="user-box">
                <div class="user-name"><%= users.getString("name") %></div>
                <div class="user-detail">
                    <span class="user-age">Age: <%= users.getInt("age") %></span> | 
                    <span class="user-country">Country: <%= users.getString("country") %></span>
                </div>
            </div>
            <%
                        }
                    } else {
            %>
            <div class="alert alert-warning">
                No user records found in database.
            </div>
            <%
                    }
                } else {
            %>
            <div class="alert alert-danger">
                Error: Could not retrieve user data.
            </div>
            <%
                }
            %>
            
            <!-- Debug Information Section -->
            <div class="debug-info mt-4">
                <h5>Debug Information</h5>
                <div><strong>Total Records:</strong> <%= recordCount %></div>
                <div><strong>ResultSet Status:</strong> <%= users != null ? "Available" : "Not Available" %></div>
                <div><strong>Current Time:</strong> <%= new java.util.Date() %></div>
            </div>
        </div>

        <script>
            // Update record count badge
            document.getElementById('recordCount').textContent = '<%= recordCount %> records';
            
            // Console log for verification
            console.log('Admin page loaded with <%= recordCount %> records');
        </script>
    </body>
</html>