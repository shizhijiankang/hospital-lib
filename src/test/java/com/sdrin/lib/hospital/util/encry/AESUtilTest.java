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

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class AESUtilTest {

    @Test
    void encrypt() {
        String symmetricKey = RandomStringUtils.randomAlphanumeric(16);

        String encty = AESUtil.encrypt("中国123abc", symmetricKey);

        System.out.println("中国123abc".equals(AESUtil.decrypt(encty, symmetricKey)));

    }

    @Test
    void encryptByte() {
        String symmetricKey = RandomStringUtils.randomAlphanumeric(16);
        byte[] Data = new byte[]{34, 35, 36, 37, 37, 37, 67, 68, 69};
        byte[] b = new byte[0];
        try {
            b = AESUtil.encryptByte(Data, symmetricKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] d = new byte[0];
        try {
            d = AESUtil.decryptByte(b, symmetricKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(java.util.Arrays.toString(d));
    }

    @Test
    void e() throws Exception {
        String key = "8so5m3uX1vjtqpd7";
        String plain = "aZPjcfhrJ5CvTzNobjCK7eZD7lGPLm7B";
        byte[] plainBytes = plain.getBytes(StandardCharsets.UTF_8);
        byte[] s = AESUtil.encryptByte(plainBytes, key);

        byte[] aa = AESUtil.decryptByte(s, key);
        String s2 = new String(aa, StandardCharsets.UTF_8);
        System.out.println(s2);

        System.out.println(s2.equals(plain));

    }
}