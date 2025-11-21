package com.password.manager.auth.passwordHandler;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordStorage {

    // Store master password file in user's home directory
    private static final String PASSWORD_FILE = System.getProperty("user.home") +
                                                File.separator + ".passowrdmanager" +
                                                File.separator + "master.dat";
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    public static void savePassword(String password) throws Exception {
        // Generate random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Hash password with salt
        byte[] hash = hashPassword(password, salt);

        // Save salt and hash to file
        Files.createDirectories(Paths.get(PASSWORD_FILE).getParent());
        try (FileOutputStream fos = new FileOutputStream(PASSWORD_FILE)) {
            fos.write(salt);
            fos.write(hash);
        }
    }

    public static boolean verifyPassword(String password) throws Exception {
        File file = new File(PASSWORD_FILE);
        if (!file.exists()) {
            return false;
        }

        // Read salt and hash from file
        try (FileInputStream fis = new FileInputStream(PASSWORD_FILE)) {
            byte[] salt = new byte[16];
            byte[] storedHash = new byte[32];
            fis.read(salt);
            fis.read(storedHash);

            // Hash provided password with stored salt
            byte[] providedHash = hashPassword(password, salt);

            // Compare hashes
            return java.util.Arrays.equals(storedHash, providedHash);
        }
    }

    private static byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return factory.generateSecret(spec).getEncoded();
    }

    public static boolean isMasterPasswordSet() {
        return new File(PASSWORD_FILE).exists();
    }
}