package com.miph._3.SafePasswordProjectMiph.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class HashingService {

    // nicht ideal, in db speichern ist besser
    protected final String saltBase64 = "ulEj+IwtVlr2r+BNGC1+WA==";

    public String hashPw(String pw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");


            byte[] saltBytes = Base64.getDecoder().decode(saltBase64);
            digest.update(saltBytes);

            byte[] encodedhash = digest.digest(pw.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing SHA-256 hashing algorithm", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid Base64 salt provided", e);
        }
    }
}
