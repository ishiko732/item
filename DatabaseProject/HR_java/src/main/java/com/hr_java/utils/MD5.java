package com.hr_java.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MD5 {
    public static String md5(String origin) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] bytes = md.digest(origin.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
