/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import model.JDBC;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
public class Try {
    
    // Simple test in your main method or another test class
public static void main(String[] args) {
    JDBC jdbc = new JDBC("1527", "simplewebdb", "app", "app");
    try {
        ResultSet rs = jdbc.getAllUsers();
        while (rs.next()) {
            System.out.println(
                "Name: " + rs.getString("name") + 
                ", Age: " + rs.getInt("age") + 
                ", Country: " + rs.getString("country")
            );
        }
        rs.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
}
