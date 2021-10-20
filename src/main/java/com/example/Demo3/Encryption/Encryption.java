package com.example.Demo3.Encryption;

import com.example.Demo3.Log.LogHolder;
import com.example.Demo3.Log.LogKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static net.logstash.logback.marker.Markers.appendEntries;

public class Encryption {

    private static final Logger log = LoggerFactory.getLogger(Encryption.class);
    private static final String INIT_VECTOR = "gZJE27GkxzBnDLnD";

    public static String encrypt(String value, String privateKey) {
        try {
            //for logging
            LogHolder logData = new LogHolder();
            logData.put(LogKeys.DECRYPTED_RESPONSE, value);
            log.info(appendEntries(logData.getAttributes()), "before encryption");
//            MDC.put(LogKeys.DECRYPTED_RESPONSE, value);
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new
                    SecretKeySpec(privateKey
                    .getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            byte[] original = Base64.getEncoder().encode(encrypted);
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encrypted, String privateKey) {
        try {
            LogHolder logHolder = new LogHolder();
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR
                    .getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(privateKey.
                    getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            LogHolder logData = new LogHolder();
            logData.put(LogKeys.DECRYPTED_REQUEST, new String(original));
            log.info(appendEntries(logData.getAttributes()), "after decrypt");
//            MDC.put(LogKeys.DECRYPTED_REQUEST, new String(original));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
