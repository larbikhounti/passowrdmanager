package com.password.manager.auth.services;

public class AuthenticationService {
    //
    public boolean login(String password) {
        return checkAttempts() && checkPassword(password);
    }

    public boolean logout() {
        // TODO Logout logic
        System.out.println("Logging out user");
        return true;
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        // TODO Change password logic
        System.out.println("Changing password from " + oldPassword + " to " + newPassword);
        return true;
    }

    private boolean checkPassword(String password) {
        String temp = "password"; // Placeholder for actual password check

        // TODO Check password logic
        return password.equals(temp);
    }

    private boolean checkAttempts() {
        // TODO Check login attempts logic
        return true;
    }
}
