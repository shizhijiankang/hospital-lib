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

import com.sdrin.lib.hospital.config.Constant;
import com.sdrin.lib.hospital.domain.http.SHttpRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/10/7 5:42 下午
 *
 * @author 胡树铭
 */
class SHttpRequestTest {

    @Test
    void needSign() {
        SHttpRequest request1 = new SHttpRequest();
        request1.setSignType(Constant.SIGN_TYPE);
        assertTrue(request1.needSign());
        SHttpRequest request2 = new SHttpRequest();
        assertFalse(request2.needSign());
    }

    @Test
    void needEncrypt() {
        SHttpRequest request1 = new SHttpRequest();
        request1.setEncType(Constant.ENC_TYPE);
        assertTrue(request1.needEncrypt());
        SHttpRequest request2 = new SHttpRequest();
        assertFalse(request2.needEncrypt());
    }
}
