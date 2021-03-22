package com.sdrin.lib.hospital.util.encry;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.sdrin.lib.hospital.config.Constant.CHARSET;

/**
 * 用于C#与JAVA互通的AES加密工具, 兼容了c# 和 java，
 */
public class AESUtil {
    /**
     * 加密/解密算法名称
     */
    private static final String ALGORITHM = "AES";

    public static byte[] encryptToBytes(String plain_text, String key) {
        if (plain_text == null || key == null) return null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(CHARSET), ALGORITHM));
            return cipher.doFinal(plain_text.getBytes(CHARSET));
            /*return new BASE64Encoder().encode(bytes);*/
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String encrypt(String plain_text, String key) {
        return Hex.toHexString(encryptToBytes(plain_text, key));
        /*return Base64.toBase64String(encryptToBytes(plain_text, key));*/ //Base64方法
    }

    public static byte[] decryptToBytes(String cipherText, String key) {
        if (cipherText == null || key == null) return null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(CHARSET), ALGORITHM));
            byte[] bytes = Hex.decode(cipherText);
            /*byte[] bytes = Base64.decode(cipherText);*/ //Base64方法
            /*byte[] bytes = new BASE64Decoder().decodeBuffer(str);*/
            return cipher.doFinal(bytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String cipherText, String key) {
        return new String(decryptToBytes(cipherText, key));
    }

}
