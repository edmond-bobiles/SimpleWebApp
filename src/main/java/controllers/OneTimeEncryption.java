package controllers;

import model.JDBC;
import model.Security;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OneTimeEncryption {
    public static void main(String[] args) throws SQLException {
        JDBC jdbc = new JDBC("1527", "simplewebdb", "app", "app");

        ResultSet rs = jdbc.getAllUnencryptedUsers();
        while (rs.next()) {
            String username = rs.getString("user_name");
            String oldPass = rs.getString("password");
            System.out.println("Decrypted: " + oldPass);

            String encryptedPass = Security.encrypt(oldPass);
            jdbc.changePass(username, encryptedPass);

            System.out.println("Encrypted: " + encryptedPass);
        }
    }
}
