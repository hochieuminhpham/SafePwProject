package com.miph._3.SafePasswordProjectMiph.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class EncryptionService {

    // Hex Key nie hardcoden, aber ich mach es trotzdem
    private static final String HEX_KEY = "b64c2b5976275c3062aa772d04d69783b2a72c44f12921c81fd1ea28fb5dce41";

    public String encrypt(String plainTextPassword) {
        try {
            byte[] keyBytes = new byte[HEX_KEY.length() / 2];
            for (int i = 0; i < keyBytes.length; i++) {
                int index = i * 2;
                int j = Integer.parseInt(HEX_KEY.substring(index, index + 2), 16);
                keyBytes[i] = (byte) j;
            }

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainTextPassword.getBytes("UTF-8"));

            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    public String decrypt(String encryptedPasswordBase64) {
        try {
            byte[] keyBytes = new byte[HEX_KEY.length() / 2];
            for (int i = 0; i < keyBytes.length; i++) {
                int index = i * 2;
                int j = Integer.parseInt(HEX_KEY.substring(index, index + 2), 16);
                keyBytes[i] = (byte) j;
            }

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPasswordBase64);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes, "UTF-8");

        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e);
        }
    }
}