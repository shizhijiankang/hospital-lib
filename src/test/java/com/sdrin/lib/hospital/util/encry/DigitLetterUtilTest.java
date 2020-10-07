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

import com.sdrin.lib.hospital.domain.SHttpRequest;
import com.sdrin.lib.hospital.domain.SHttpResponse;
import com.sdrin.lib.hospital.util.json.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.sdrin.lib.hospital.util.encry.RSAUtil.PRIVATE_KEY_PKCS8;
import static com.sdrin.lib.hospital.util.encry.RSAUtil.PUBLIC_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/10/6 11:26 下午
 *
 * @author 胡树铭
 */
class DigitLetterUtilTest {
    SHttpRequest sHttpRequest = new SHttpRequest();
    SHttpResponse sHttpResponse = new SHttpResponse();
    Map<String, String> map = new HashMap<>();
    Map<String, byte[]> rsaKeys = RSAUtil.initKey();

    @Test
    void encryptDecryptSHttpRequest() {
        map.put("abc", "你好");
        sHttpRequest.setBizContent(JsonUtil.toJson(map));
        DigitLetterUtil.encrypt(sHttpRequest, rsaKeys.get(PUBLIC_KEY));
        SHttpRequest result = DigitLetterUtil.decrypt(sHttpRequest, rsaKeys.get(PRIVATE_KEY_PKCS8));
        assertEquals(sHttpRequest, result);
    }

    @Test
    void encryptDecryptSHttpResponse() {
        map.put("abc", "你好");
        sHttpResponse.setBizContent(JsonUtil.toJson(map));
        DigitLetterUtil.encrypt(sHttpResponse, rsaKeys.get(PUBLIC_KEY));
        SHttpResponse result = DigitLetterUtil.decrypt(sHttpResponse, rsaKeys.get(PRIVATE_KEY_PKCS8));
        assertEquals(sHttpResponse, result);
    }
}
