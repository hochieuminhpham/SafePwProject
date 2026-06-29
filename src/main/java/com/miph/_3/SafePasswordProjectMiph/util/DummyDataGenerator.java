package com.miph._3.SafePasswordProjectMiph.util;

import com.miph._3.SafePasswordProjectMiph.service.EncryptionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class DummyDataGenerator {

    @Autowired
    private EncryptionService encryptionService;

    @PostConstruct
    public void generateRealEncryptedSql() {
        System.out.println("--- COPY THE SQL BELOW ---");

        String rawPassword = "password123";

        String[] usernames = {
                "alex_g", "bella_b", "charlie_k", "daniela_m", "ethan_w",
                "fiona_r", "gavin_t", "hanna_s", "ian_p", "julia_v",
                "kevin_d", "lara_f", "marcus_l", "nina_h", "oliver_j",
                "paula_c", "quinn_e", "rachel_y", "samuel_x", "tanya_q",
                "umar_u", "valerie_o", "william_n", "xenia_i", "yousef_l",
                "zoe_p", "aaron_z", "brooke_x", "connor_v", "daisy_w"
        };

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Konvertierung in Base64 zur einfachen Speicherung
        String saltString = Base64.getEncoder().encodeToString(salt);
        System.out.println(saltString);


        for (String user : usernames) {
            try {
                String encrypted = encryptionService.encrypt(rawPassword);

                System.out.println("UPDATE account SET password_encoded = '" + encrypted
                        + "' WHERE username = '" + user + "';");
            } catch (Exception e) {
                System.err.println("Encryption failed: " + e.getMessage());
            }
        }
        System.out.println("--------------------------");
    }
}
