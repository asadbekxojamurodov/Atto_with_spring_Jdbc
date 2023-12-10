package code.uz.utils;

import java.security.NoSuchAlgorithmException;

public class MD5Util {


    public static String encode(String password) {

        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes());
            StringBuffer sb  = new StringBuffer();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {

        }
        return null;
    }
}
