package edu.codespring.bibliospring.backend.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypter {
    public static String generateHashPassword(String password, String salt){

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] input = (password+salt).getBytes();
            messageDigest.reset();
            messageDigest.update(input);
            byte[] output = messageDigest.digest();

            StringBuffer stringBuffer = new StringBuffer();

            for(byte b : output){
                stringBuffer.append(String.format("%02x",b));
            }

            return stringBuffer.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
