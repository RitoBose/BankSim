package com.rbose.onlinebanking.reset;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordReset {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPassword = "admin";  // Enter your new plain text password here
        String hashedPassword = encoder.encode(newPassword);
        System.out.println("New Hashed Password: " + hashedPassword);
    }
}
