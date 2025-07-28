package model;

import java.sql.*;

public class JDBC {
    private Connection conn;

    public JDBC(String port, String dbName, String username, String password) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url = "jdbc:derby://localhost:" + port + "/" + dbName;
            this.conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    public ResultSet getAllUsers() throws SQLException {
        String sql = "SELECT name, age, country FROM account ORDER BY name";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    public ResultSet getUser(String username, String password) throws SQLException {
        String sqlStr = "SELECT * FROM account WHERE user_name = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        return pstmt.executeQuery();
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}