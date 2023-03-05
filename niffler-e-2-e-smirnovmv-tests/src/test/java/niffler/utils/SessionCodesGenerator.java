package niffler.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SessionCodesGenerator {
    public static String getCodeVerifier() {
        //return base64Url(crypto.enc.Base64.stringify(crypto.lib.WordArray.random(32)))
        byte[] secureRandomKeyBytes = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(secureRandomKeyBytes);
        return Base64.getUrlEncoder().encodeToString(secureRandomKeyBytes);
    }

    public static String getCodeChallenge(String codeVerifier) {
        //const codeVerifier = sessionStorage.getItem('codeVerifier');
        //return base64Url(sha256(codeVerifier));
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hashbytes = digest.digest(
                codeVerifier.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().encodeToString(hashbytes);
    }
}