/*
 * Copyright 2019-2020 上海石指健康科技有限公司
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sdrin.lib.hospital.util.encry;

import com.sdrin.lib.hospital.domain.http.BaseHttpRequest;
import com.sdrin.lib.hospital.domain.http.SHttpRequest;
import com.sdrin.lib.hospital.domain.http.SHttpResponse;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2021/1/3 20:46 下午
 * 这里是数字信封技术的工具，数字信封技术概念：https://blog.csdn.net/lycb_gz/article/details/78058747
 * 这里结合了：{@link AESUtil} 和 {@link RSAUtil}
 * 兼容了c# 和 java
 * <p>
 * （1）甲使用对称密钥对明文进行加密，生成密文信息。
 * （2）甲使用乙的公钥加密对称密钥，生成数字信封。
 * （3）甲将数字信封和密文信息一起发送给乙。
 * （4）乙接收到甲的加密信息后，使用自己的私钥打开数字信封，得到对称密钥。
 * （5）乙使用对称密钥对密文信息进行解密，得到最初的明文。
 *
 * @author 胡树铭
 */
public class DigitLetterUtil {
    /**
     * 做数字信封。
     *
     * @param request       请求对象，用在api接口。
     * @param openPublicKey 对方给的共享的公钥。
     * @return 返回做了数字信封的对象。
     */
    public static BaseHttpRequest encrypt(BaseHttpRequest request, byte[] openPublicKey) {
        if (request.getBizContent() != null) {
            // 生成对称密钥。
            String symmetricKey = RandomStringUtils.randomAlphanumeric(16);
            // AES对称密钥进行加密，生成密文
            request.setBizContent(AESUtil.encrypt(request.getBizContent(), symmetricKey));
            // 利用随机数+RSA做信封
            //公钥加密对称密钥，生成数字信封
            request.setLetter(RSAUtil.encrypt(symmetricKey, openPublicKey));
        }
        return request;
    }


    /**
     * 做数字信封。
     *
     * @param response      请求对象，用在api接口。
     * @param openPublicKey 对方给的共享的公钥。
     * @return 返回做了数字信封的对象。
     */
    public static SHttpResponse encrypt(SHttpResponse response, byte[] openPublicKey) {
        if (response.getBizContent() != null) {
            // 生成对称密钥。
            String symmetricKey = RandomStringUtils.randomAlphanumeric(16);
            // AES对称密钥进行加密，生成密文
            response.setBizContent(AESUtil.encrypt(response.getBizContent(), symmetricKey));
            // 利用随机数+RSA做信封
            //公钥加密对称密钥，生成数字信封
            response.setLetter(RSAUtil.encrypt(symmetricKey, openPublicKey));
        }
        return response;
    }

    /**
     * 数字信封解密
     *
     * @param request    请求
     * @param privateKey rsa私钥
     * @return 返回解密后内容。
     */
    public static BaseHttpRequest decrypt(BaseHttpRequest request, byte[] privateKey) {
        if (request.getBizContent() != null) {
            // 解密得到：对称密钥。
            String symmetricKey = RSAUtil.decrypt(request.getLetter(), privateKey);
            // AES对称密钥进行解密，得到内容明文。
            request.setBizContent(AESUtil.decrypt(request.getBizContent(), symmetricKey));
            request.setLetter(null);
        }
        return request;
    }

    /**
     * 数字信封解密
     *
     * @param response   请求
     * @param privateKey rsa私钥
     * @return 返回解密后内容。
     */
    public static SHttpResponse decrypt(SHttpResponse response, byte[] privateKey) {
        if (response.getBizContent() != null) {
            // 解密得到：对称密钥。
            String symmetricKey = RSAUtil.decrypt(response.getLetter(), privateKey);
            // AES对称密钥进行解密，得到内容明文。
            response.setBizContent(AESUtil.decrypt(response.getBizContent(), symmetricKey));
            response.setLetter(null);
        }
        return response;
    }
}
