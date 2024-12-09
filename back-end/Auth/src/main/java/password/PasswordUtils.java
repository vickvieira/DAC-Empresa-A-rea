package password;

import java.security.MessageDigest;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

public class PasswordUtils {

    // Gera um SALT aleatório
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    // Gera o hash SHA-256
    public static String hashPassword(String password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] hashedPassword = md.digest(password.getBytes());
        return bytesToHex(hashedPassword);
    }

    // Converte bytes para hexadecimal
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    public static void geraSenha(String password) throws Exception {
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

    }
}