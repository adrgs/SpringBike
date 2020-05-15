package site.springbike.crypto;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class SBCrypt {
    private static int workload = 12;

    public static String hashPassword(String passwordPlaintext) {
        String salt = BCrypt.gensalt(workload);
        String hashedPassword = BCrypt.hashpw(passwordPlaintext, salt);

        return (hashedPassword);
    }

    public static boolean checkPassword(String passwordPlaintext, String storedHash) {
        boolean passwordVerified = false;

        if (null == storedHash || !storedHash.startsWith("$2a$"))
            return false;

        passwordVerified = BCrypt.checkpw(passwordPlaintext, storedHash);

        return passwordVerified;
    }
}