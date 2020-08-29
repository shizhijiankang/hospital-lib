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

package com.sdrin.lib.hospital.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/6/27 1:53 下午
 * rsa key 一共有此3种。
 *
 * @author 胡树铭
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RSAKeyType {
    /**
     * 非java私钥
     */
    PKCS1,
    /**
     * java私钥
     */
    PKCS8,
    /**
     * 公钥
     */
    PUBLIC;


    /**
     * 用在 rsa key pem文件的头尾 的描述词
     *
     * @return 返回描述词
     */
    public String toDescription() {
        switch (this) {
            case PKCS1:
                return "RSA PRIVATE KEY";
            case PKCS8:
                return "PRIVATE KEY";
            case PUBLIC:
                return "PUBLIC KEY";
            default:
                return null;
        }
    }
}
