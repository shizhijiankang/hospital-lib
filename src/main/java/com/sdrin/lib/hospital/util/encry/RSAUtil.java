/*
Copyright 2019-2020 上海石指健康科技有限公司

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.sdrin.lib.hospital.util.encry;

import com.sdrin.lib.hospital.domain.RSAKeyType;
import com.sdrin.lib.hospital.domain.http.BaseHttpRequest;
import com.sdrin.lib.hospital.domain.http.DHttpRequest;
import com.sdrin.lib.hospital.domain.http.SHttpRequest;
import com.sdrin.lib.hospital.domain.http.SHttpResponse;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;


/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/6/7 6:35 下午
 *
 * @author 胡树铭
 */
public class RSAUtil {
    // MAX_DECRYPT_BLOCK应等于密钥长度/8（1byte=8bit），所以当密钥位数为2048时，最大解密长度应为256.
    //密钥算法
    //RSA 签名算法
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";
    public static final String PRIVATE_KEY_PKCS8 = "privateKeyPkcs8";
    public static final String PUBLIC_KEY_FILENAME = "rsa_public_key.pem";
    public static final String PRIVATE_KEY_FILENAME = "rsa_private_key.pem";// pkcs1
    public static final String PRIVATE_KEY_FILENAME_PKCS8 = "rsa_public_key_pkcs8.pem";


    /**
     * 产生 rsa key, 这里的私钥是，pkcs1 格式，返回的是字节形，这样便于存储在数据库，它比string格式好。
     *
     * @return 返回私有和公有的 rsa key
     */
    public static Map<String, byte[]> initKey() {
        //生成密匙对
        KeyPair keyPair = RsaHelper.initKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();
        Map<String, byte[]> keyPairMap = new HashMap<>();
        keyPairMap.put(PUBLIC_KEY, publicKey.getEncoded());
        keyPairMap.put(PRIVATE_KEY_PKCS8, privateKey.getEncoded());
        return keyPairMap;
    }


    /**
     * 从数据库里取出 公钥字节，对文本进行加密
     *
     * @param plain_text 加密前的字符串
     * @param publicKey  加密的key，字节
     * @return 返回加密后的string。 base64 格式。
     */
    public static String encrypt(String plain_text, byte[] publicKey) {
        return RsaHelper.encryptByPublicKey(plain_text, Base64.encodeToString(publicKey));
    }

    /**
     * rsa2 私钥解密，且是通过私钥的字节，pkcs8 格式私钥，编码格式为 utf-8
     *
     * @param encryptStr      加密后的字符串， 密文
     * @param pkcs8PrivateKey 私钥，pkcs8。字节
     * @return 返回加密前的string
     */
    public static String decrypt(String encryptStr, byte[] pkcs8PrivateKey) {
        return RsaHelper.decryptByPrivateKey(encryptStr, Base64.encodeToString(pkcs8PrivateKey));
    }

    /**
     * 将pkcs8的私钥转为pkcs1的私钥。
     *
     * @param pkcs8PrivateKey 传入pkcs8私钥字节
     * @return 返回pkcs1字节
     */
    public static byte[] convertPkcs8PrivateKeyToPkcs1(byte[] pkcs8PrivateKey) {
        PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(pkcs8PrivateKey);
        ASN1Encodable encodable = null;
        try {
            encodable = pkInfo.parsePrivateKey();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        ASN1Primitive primitive = encodable.toASN1Primitive();
        try {
            return primitive.getEncoded();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    /**
     * 将字节形的公钥，转为java对象
     *
     * @param keyBytes 字节形的公钥
     * @return 转为java对象
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        try {
            return RsaHelper.getPublicKey(keyBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字节形的 pkcs8 私钥转为java对象
     *
     * @param pkcs8privateKeyBytes 字节形
     * @return 转为java对象，私钥
     */
    public static PrivateKey restorePkcs8PrivateKey(byte[] pkcs8privateKeyBytes) {
        try {
            return RsaHelper.getPrivateKey(pkcs8privateKeyBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将pkcs8 格式私钥，转为pkcs1 格式，使得非java程序也能用, 已经验证过。用于前台显示。
     *
     * @param pkcs8PrivateKey java 生成的pccs8 格式的私钥
     * @return 转为base64 格式pkcs1 string
     */
    public static String convertPkcs8ToPkcs1(byte[] pkcs8PrivateKey) {
        PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(pkcs8PrivateKey);
        ASN1Encodable encodable;
        try {
            encodable = pkInfo.parsePrivateKey();
            ASN1Primitive primitive = encodable.toASN1Primitive();
            byte[] privateKeyPKCS1 = primitive.getEncoded();
            return Base64.encodeToString(privateKeyPKCS1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对请求body进行数字签名，这里只是数字签名，并没有加密。
     *
     * @param request    被数字签名前的请求body
     * @param privateKey 私钥
     */
    public static void sign(BaseHttpRequest request, byte[] privateKey) {
        // 此数字签名后的密文。
        String signed = RsaHelper.sign(DigitUtil.digit(request), Base64.encodeToString(privateKey));
        request.setSign(signed);
    }

    /**
     * 验证发送方发送的内容，验证传输的内容有没有被修改，验证发送方是不是 曾经发给己方公钥的一方，所以这里使用的是发送方的公钥。
     *
     * @param request   请求内容，
     * @param publicKey 发送方共享的公钥，非己方生成的公钥
     * @return 如果验证通过，却是是发送方，则返回true，否则false
     */
    public static boolean verify(BaseHttpRequest request, byte[] publicKey) {
        return RsaHelper.verify(DigitUtil.digit(request), Base64.encodeToString(publicKey), request.getSign());
    }

    /**
     * 对请求body进行数字签名，这里只是数字签名，并没有加密。
     *
     * @param response   被数字签名前的请求body
     * @param privateKey 私钥
     */
    public static void sign(SHttpResponse response, byte[] privateKey) {
        // 此数字签名后的密文。
        String signed = RsaHelper.sign(DigitUtil.digit(response), Base64.encodeToString(privateKey));
        response.setSign(signed);
    }

    /**
     * 验证发送方返回的内容，验证传输的内容有没有被修改，验证发送方是不是 曾经发给己方公钥的一方，所以这里使用的是发送方的公钥。
     *
     * @param response  请求返回内容，
     * @param publicKey 发送方共享的公钥，非己方生成的公钥
     * @return 如果验证通过，却是是发送方，则返回true，否则false
     */
    public static boolean verify(SHttpResponse response, byte[] publicKey) {
        return RsaHelper.verify(DigitUtil.digit(response), Base64.encodeToString(publicKey), response.getSign());
    }

    /**
     * 将rsa key 写入临时文件，便于服务器将文件返回给浏览器前台。
     *
     * @param key        rsa key，包含3种key，公钥，私钥，pkcs8
     * @param rsaKeyType 三种key类型。
     * @param path       保存的本地目录
     * @return 返回文件的路径。
     */
    public static Path writePemFile(byte[] key, RSAKeyType rsaKeyType, Path path) {
        PemObject pemObject = new PemObject(rsaKeyType.toDescription(), key);
        try {
            if (!Files.exists(path.getParent()))
                Files.createDirectories(path.getParent());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Files.newOutputStream(path));
            PemWriter pemWriter = new PemWriter(outputStreamWriter);
            pemWriter.writeObject(pemObject);
            pemWriter.close();
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将pem文件，的公钥私钥，解析为 字节。
     *
     * @param inputStreamReader pem文件。
     * @return 返回字节。
     */
    public static byte[] parsePEMFile(InputStreamReader inputStreamReader) {
        PemReader reader = new PemReader(inputStreamReader);
        PemObject pemObject = null;
        try {
            pemObject = reader.readPemObject();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        byte[] content = pemObject.getContent();
        try {
            reader.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return content;
    }
}
