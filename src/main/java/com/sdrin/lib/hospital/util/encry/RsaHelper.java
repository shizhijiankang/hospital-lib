package com.sdrin.lib.hospital.util.encry;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 此为兼容，android和java后台的底层的，rsa算法，签名使用的是  MD5withRSA
 * 1024位 java 是 pkcs8 私钥 ， 签名是 md5 with rsa
 */
public class RsaHelper {
    // 使用1024位，
    public static final int ALGORITHM_RSA_PRIVATE_KEY_LENGTH = 1024;

    private RsaHelper() {
    }

    private static final String RSA_ALGORITHM = "RSA";
    /**
     * 加密算法RSA
     */
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 编码
     */
    private static final String CHAR_ENCODING = "UTF-8";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    //************************************提供方便调用的一些方法****************************************


    /**
     * 生成RSA密钥对(默认密钥长度为1024)
     *
     * @return 返回生成的密钥对
     */
    public static KeyPair initKeyPair() {
        return initKeyPair(ALGORITHM_RSA_PRIVATE_KEY_LENGTH);
    }

    /**
     * 生成RSA密钥对
     *
     * @param length 密钥长度，范围：512～2048
     * @return 返回密钥对
     */
    public static KeyPair initKeyPair(int length) {
        KeyPair keyPair = null;
        try {
            final KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            keyPairGen.initialize(length);
            keyPair = keyPairGen.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       数据
     * @param privateKey 私钥(BASE64编码)
     * @return 签名后的数据
     */
    public static String sign(String data, String privateKey) {
        String sign = null;
        try {
            final byte[] dataBytes = data.getBytes(CHAR_ENCODING);
            final PrivateKey key = getPrivateKey(Base64.decode(privateKey));

            final byte[] signBytes = sign(dataBytes, key);
            sign = Base64.encodeToString(signBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * 校验数字签名
     *
     * @param data      数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名(BASE64编码)
     * @return 符合要求则true，否则false
     */
    public static boolean verify(String data, String publicKey, String sign) {
        boolean verify = false;
        try {
            final byte[] dataBytes = data.getBytes(CHAR_ENCODING);
            final PublicKey key = getPublicKey(Base64.decode(publicKey));
            final byte[] signBytes = Base64.decode(sign);

            final Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(key);
            signature.update(dataBytes);
            verify = signature.verify(signBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verify;
    }

    /**
     * 公钥加密
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return BASE64编码的加密数据
     */
    public static String encryptByPublicKey(String data, String publicKey) {
        String encryptData = null;
        try {
            final byte[] dataBytes = data.getBytes(CHAR_ENCODING);
            PublicKey key = getPublicKey(Base64.decode(publicKey));

            final byte[] encryptDataBytes = encryptByPublicKey(dataBytes, key);
            encryptData = Base64.encodeToString(encryptDataBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptData;
    }

    /**
     * 私钥加密
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return BASE64编码的加密数据
     */
    public static String encryptByPrivateKey(String data, String privateKey) {
        String encryptData = null;
        try {
            final byte[] dataBytes = data.getBytes(CHAR_ENCODING);
            PrivateKey key = getPrivateKey(Base64.decode(privateKey));

            final byte[] encryptDataBytes = encryptByPrivateKey(dataBytes, key);
            encryptData = Base64.encodeToString(encryptDataBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptData;
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 私钥加密的数据(BASE64编码)
     * @param publicKey     公钥(BASE64编码)
     * @return 私钥加密前的数据
     */
    public static String decryptByPublicKey(String encryptedData, String publicKey) {
        String data = null;
        try {
            final byte[] dataBytes = Base64.decode(encryptedData);
            PublicKey key = getPublicKey(Base64.decode(publicKey));

            final byte[] decryptDataBytes = decryptByPublicKey(dataBytes, key);
            data = new String(decryptDataBytes, CHAR_ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 公钥加密的数据(BASE64编码)
     * @param privateKey    私钥(BASE64编码)
     * @return 公钥加密前的数据
     */
    public static String decryptByPrivateKey(String encryptedData, String privateKey) {
        String data = null;
        try {
            final byte[] dataBytes = Base64.decode(encryptedData);
            PrivateKey key = getPrivateKey(Base64.decode(privateKey));

            final byte[] decryptDataBytes = decryptByPrivateKey(dataBytes, key);
            data = new String(decryptDataBytes, CHAR_ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    //**************************************提供一些基础操作********************************************

    /**
     * 获取公钥
     *
     * @param keyBytes 共钥字节
     * @return 从字节转共钥
     * @throws Exception 错误提示
     */
    public static PublicKey getPublicKey(byte[] keyBytes) throws Exception {
        final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        final PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        return publicKey;
    }

    /**
     * 获取私钥
     *
     * @param keyBytes 私钥
     * @return 私钥
     * @throws Exception 错误
     */
    public static PrivateKey getPrivateKey(byte[] keyBytes) throws Exception {
        final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        final PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        return privateKey;
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       数据
     * @param privateKey 私钥
     * @return 签名后的字节
     * @throws Exception 错误
     */
    private static byte[] sign(byte[] data, PrivateKey privateKey) throws Exception {
        final Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 校验数字签名
     *
     * @param data      已加密数据
     * @param publicKey 公钥
     * @param signBytes 数字签名
     * @return 验证签名是否正确
     * @throws Exception 错误提示
     */
    private static boolean verify(byte[] data, PublicKey publicKey, byte[] signBytes) throws Exception {
        final Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(signBytes);
    }

    /**
     * 公钥加密
     *
     * @param data      源数据
     * @param publicKey 公钥
     * @return 加密后字节
     * @throws Exception 错误
     */
    private static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {
        final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            final int inputLen = data.length;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            encryptedData = out.toByteArray();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
        return encryptedData;
    }

    /**
     * 私钥加密
     *
     * @param data       源数据
     * @param privateKey 私钥
     * @return 加密
     * @throws Exception 错误提示
     */
    private static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {
        final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedData = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            final int inputLen = data.length;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            encryptedData = out.toByteArray();
            out.close();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
        return encryptedData;
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 公钥加密的数据
     * @param publicKey     公钥
     * @return 解密
     * @throws Exception 错误提示
     */
    private static byte[] decryptByPublicKey(byte[] encryptedData, PublicKey publicKey) throws Exception {
        final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedData = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            final int inputLen = encryptedData.length;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            decryptedData = out.toByteArray();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
        return decryptedData;
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 公钥加密的数据
     * @param privateKey    私钥
     * @return 返回字节
     * @throws Exception 错误
     */
    private static byte[] decryptByPrivateKey(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            final int inputLen = encryptedData.length;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            decryptedData = out.toByteArray();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
        return decryptedData;
    }
}
