package model;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class Security {

    // 16-byte AES key
    private static final byte[] key = {
        'c','h','r','i','s','t','i','a',
        'n','a','b','i','h','a','s','a'
    };

    public static String encrypt(String strToEncrypt) {
        String encryptedString = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
            encryptedString = Base64.encodeBase64String(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptedString;
    }

    public static String decrypt(String codeDecrypt) {
        String decryptedString = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(codeDecrypt));
            decryptedString = new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decryptedString;
    }
}
