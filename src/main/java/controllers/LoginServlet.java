package controllers;

import model.JDBC;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("1527", "simplewebdb", "app", "app");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/login.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    try {
        ResultSet rs = jdbc.getUserCredentials(username, password);

        if (rs.next()) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("user_role", rs.getString("user_role"));
            
            // Checks whether admin or guest
            if ("admin".equals(rs.getString("user_role"))) {
                ResultSet users = jdbc.getAllUsersRecords();
                request.setAttribute("results", users);
                request.getRequestDispatcher("/views/admin.jsp").forward(request, response);
            } else {
                ResultSet guestRecord = jdbc.getUserRecords(username);
                request.setAttribute("results", guestRecord);
                request.getRequestDispatcher("/views/guest.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        request.setAttribute("error", "Database error: " + e.getMessage());
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }
}
}