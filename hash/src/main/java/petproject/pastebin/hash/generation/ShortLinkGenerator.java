package petproject.pastebin.hash.generation;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ShortLinkGenerator {

    private final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private final int LINK_LENGTH = 6;

    private final SecureRandom secureRandom = new SecureRandom();

    public String generateRandomLink() {
        StringBuilder sb = new StringBuilder(LINK_LENGTH);
        for (int i = 0; i < LINK_LENGTH; i++) {
            sb.append(BASE62.charAt(secureRandom.nextInt(BASE62.length())));
        }
        return sb.toString();
    }
}
