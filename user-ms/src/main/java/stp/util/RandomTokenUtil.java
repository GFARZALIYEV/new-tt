package stp.util;

import java.util.UUID;

public final class RandomTokenUtil {

    private RandomTokenUtil() {
        // instans alınmasın deyə
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
