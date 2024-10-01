package petproject.pastebin.hash.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class Base64FromTimeHashGenerationService implements HashGenerationService {
    public String getHash() {
        try {
            // Create a SHA-256 digest
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hash the input
            byte[] hashBytes = digest.digest(Long.toString(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8));

            // Encode the hash as a Base64 string
            String encodedHash = Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);

            // Return the first 8 characters to keep it short
            return encodedHash.substring(0, 8);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not generate hash", e);
        }
    }
}
