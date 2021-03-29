package com.sdrin.lib.hospital.util.encry;

//import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
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
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static final int blockSize = 64;

//    public static byte[] encryptToBytes(String plain_text, String key) {
//        if (plain_text == null || key == null) return null;
//        try {
//            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
//            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(CHARSET), ALGORITHM));
//            return cipher.doFinal(plain_text.getBytes(CHARSET));
//            /*return new BASE64Encoder().encode(bytes);*/
//        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }

    public static String encrypt(String plain_text, String key) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        byte[] keyBytes = new byte[16];
        byte[] b = new byte[0];
        try {
            b = key.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        try {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        byte[] results = new byte[0];
        try {
            results = cipher.doFinal(plain_text.getBytes(CHARSET));
        } catch (IllegalBlockSizeException | UnsupportedEncodingException | BadPaddingException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(results, Base64.DEFAULT); // it returns the result as a String
    }

//    public static byte[] decryptToBytes(String cipherText, String key) {
//        if (cipherText == null || key == null) return null;
//        try {
//            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
//            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(CHARSET), ALGORITHM));
//            byte[] bytes = Base64.decode(cipherText, Base64.DEFAULT);
//            /*byte[] bytes = Base64.decode(cipherText);*/ //Base64方法
//            /*byte[] bytes = new BASE64Decoder().decodeBuffer(str);*/
//            return cipher.doFinal(bytes);
//        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static String decrypt(String cipherText, String key) {
        Cipher cipher = null; //this parameters should not be changed
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        byte[] keyBytes = new byte[16];
        byte[] b = new byte[0];
        try {
            b = key.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        try {
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        byte[] results = new byte[cipherText.length()];
        try {
            results = cipher.doFinal(Base64.decode(cipherText, Base64.DEFAULT));
        } catch (Exception e) {
        }
        try {
            return new String(results, CHARSET); // it returns the result as a String
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 对文件进行加密，解密

    /**
     * 对文件进行加密
     *
     * @param fis          传进来的文件
     * @param key          密钥
     * @param outputStream 写出文件
     */
    public static void encryptFile(InputStream fis, OutputStream outputStream, String key) {
        Cipher cipher;
        byte[] buffer = new byte[blockSize];
        int bytesRead;
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(CHARSET), ALGORITHM), new IvParameterSpec(new byte[16]));
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
            fis.close();
            outputStream.close();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

    }

    /**
     * 对文件进行解密
     *
     * @param fis          传进来的文件，加密过的文件
     * @param key          密钥
     * @param outputStream 写出文件
     */
    public static void decryptFile(InputStream fis, OutputStream outputStream, String key) {
        Cipher cipher;
        byte[] buffer = new byte[blockSize];
        int bytesRead;
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(CHARSET), ALGORITHM), new IvParameterSpec(new byte[16]));
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
            fis.close();
            outputStream.close();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }


    public static byte[] encryptByte(byte[] data, String key) throws Exception {
        Cipher cipher;
        cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(CHARSET), ALGORITHM), new IvParameterSpec(new byte[16]));
        byte[] encVal = cipher.doFinal(data);
        return encVal;
    }

    public static byte[] decryptByte(byte[] Data, String key) throws Exception {
        Cipher cipher;
        cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(CHARSET), ALGORITHM), new IvParameterSpec(new byte[16]));
        byte[] encVal = cipher.doFinal(Data);
        return encVal;
    }

}
