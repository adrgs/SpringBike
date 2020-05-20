package site.springbike.session;

import com.google.gson.Gson;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.apache.commons.codec.binary.Hex;
import site.springbike.model.User;

public class SessionUtils {
    private byte[] sessionKey;
    private byte[] sessionIV;
    private byte[] hmacKey;

    private SessionUtils() {
        SecureRandom secureRandom = new SecureRandom();
        sessionKey = new byte[16];
        sessionIV = new byte[16];
        hmacKey = new byte[16];
        secureRandom.nextBytes(sessionKey);
        secureRandom.nextBytes(sessionIV);
        secureRandom.nextBytes(hmacKey);
    }

    public static SessionUtils getInstance() {
        return Loader.INSTANCE;
    }

    private static class Loader {
        static final SessionUtils INSTANCE = new SessionUtils();
    }

    public UserSession getUserSession(String session) {
        byte[] encryptedBytes = Base64.getDecoder().decode(session);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec sKeySpec = new SecretKeySpec(this.sessionKey, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(this.sessionIV);
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String jsonString = new String(decryptedBytes, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            UserSession userSession = gson.fromJson(jsonString, UserSession.class);

            String hmac = getHmac(userSession.id.toString());
            if (!hmac.equals(userSession.getHmac())) {
                return null;
            }

            return userSession;
        } catch (Exception ex) {
            return null;
        }
    }

    private String getHmac(String message) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Mac sha512Hmac = Mac.getInstance("HmacSHA512");
        SecretKeySpec hmacKeySpec = new SecretKeySpec(this.hmacKey, "HmacSHA512");
        sha512Hmac.init(hmacKeySpec);
        byte[] macData = sha512Hmac.doFinal(message.getBytes("UTF-8"));
        return new String(Hex.encodeHex(macData, true));
    }

    public String encryptSession(Integer id) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec sKeySpec = new SecretKeySpec(this.sessionKey, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(this.sessionIV);
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParameterSpec);

        UserSession userSession = new UserSession(id);
        userSession.setHmac(getHmac(id.toString()));

        Gson gson = new Gson();
        String jsonSession = gson.toJson(userSession);
        return Base64.getEncoder().encodeToString(cipher.doFinal(jsonSession.getBytes("UTF-8")));
    }

}
