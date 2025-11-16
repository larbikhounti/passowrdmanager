package com.password.manager.auth.services;

import com.password.manager.auth.passwordHandler.PasswordStorage;
import com.password.manager.utils.Helpers;

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
        System.out.printf("Changing password from %s to %s " ,oldPassword,  newPassword);
        return true;
    }

    private boolean checkPassword(String password) {
        try {
            return PasswordStorage.verifyPassword(password);
        } catch (Exception e) {
            Helpers.Logger("Password verification error: " + e.getMessage(), "ERROR");
            throw new RuntimeException(e);
        }
    }

    private boolean checkAttempts() {
        // TODO Check login attempts logic
        return true;
    }
}
