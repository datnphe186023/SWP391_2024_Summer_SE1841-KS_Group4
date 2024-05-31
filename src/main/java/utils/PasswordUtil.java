package utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    public static String hashPassword(final char[] password, final byte[] salt) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyPassword(final char[] originalPassword, final String storedHash, final byte[] salt) {
        String newHash = hashPassword(originalPassword, salt);
        return storedHash.equals(newHash);
    }

    public static byte[] getSalt() {
        // Securely generate a random salt here, for simplicity we'll use a fixed value.
        // In practice, you should use SecureRandom to generate a new salt for each password.
        return "12345678".getBytes();
    }

    public static void main(String[] args) {
        byte[] salt = getSalt();
        String hashedPassword = hashPassword("password".toCharArray(), salt);
        System.out.println("Hashed Password: " + hashedPassword);

        boolean isPasswordCorrect = verifyPassword("password".toCharArray(), hashedPassword, salt);
        System.out.println("Password verification: " + isPasswordCorrect);
    }
}
