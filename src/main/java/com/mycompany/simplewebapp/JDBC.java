package com.mycompany.simplewebapp;

import java.sql.*;

public class JDBC {
    
    private Connection conn;

    public JDBC(String port, String databaseName, String userName, String password) {
        String jdbcUrl = "jdbc:derby://localhost:" + port + "/" + databaseName
               + ";create=true;user=" + userName
               + ";password="   + password;
        System.out.println("JDBC URL = " + jdbcUrl);


        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            conn = DriverManager.getConnection(jdbcUrl);
            System.err.println("Connected to database: " + databaseName);

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to MySQL database!");
            e.printStackTrace();
        }
    }
    
    public ResultSet getUser(String username, String password) throws SQLException {
        String sqlStr = "SELECT * FROM account WHERE user_name = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        return pstmt.executeQuery();
    }
    
    public ResultSet getAllUsers() throws SQLException {
        Statement stmt = conn.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        );
        return stmt.executeQuery("SELECT name, age, country FROM account");
    }

}
