package controllers;

import java.sql.*;

public class TestJDBCConnection {
    public static void main(String[] args) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/simplewebdb", "app", "app"
            );
            System.out.println("✅ Successfully connected to Derby database!");
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver class not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ SQL Exception!");
            e.printStackTrace();
        }
    }
}
