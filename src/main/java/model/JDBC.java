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

    public ResultSet getAllUsersRecords() throws SQLException {
        String sql = "SELECT name, age, country FROM account ORDER BY name";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }
    
    public ResultSet getAllUserRolesRecords() throws SQLException {
        String sql = "SELECT * FROM account ORDER BY name";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }
    
    public ResultSet getAllUnencryptedUsers() throws SQLException {
        String sql = "SELECT user_name, password FROM account WHERE encrypted = 0";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }
    
    public void changePass(String user_name, String newPassword) throws SQLException {
        String sql = "UPDATE account SET password = ?, encrypted = 1 WHERE user_name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newPassword);
        pstmt.setString(2, user_name);
        pstmt.executeUpdate();
    }

    public ResultSet getUserCredentials(String username, String password) throws SQLException {
        String sql = "SELECT * FROM account WHERE user_name = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        return pstmt.executeQuery();
    }

    public ResultSet getUserRecords(String username) throws SQLException {
        String sql = "SELECT name, age, country FROM account WHERE user_name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);  // Bind the username parameter
        return pstmt.executeQuery();
    }

    public String getEncryptedPassword(String username) throws SQLException {
        String sql = "SELECT password FROM account WHERE user_name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        return rs.next() ? rs.getString("password") : null;
    }

    public String getUserRole(String username) throws SQLException {
        String sql = "SELECT user_role FROM account WHERE user_name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        return rs.next() ? rs.getString("user_role") : null;
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