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

package com.sdrin.lib.hospital.util.code;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/7/5 10:38 上午
 *
 * @author 胡树铭
 */
class DepartmentCodeUtilTest {

    @Test
    void getDepCodeFromLocalResource() {
        Arrays.stream(DepartmentCodeUtil.getAllDepCode())
                .forEach(System.out::println);
    }

    @Test
    void getDeepNameByCode() {
        assertEquals(DepartmentCodeUtil.getDepNameByCode("0100"), "内科");
        assertEquals(DepartmentCodeUtil.getDepNameByCode("0101"), "心血管内科");
    }

    @Test
    void getAllSecondDepCodes() {
        assertTrue(Arrays.stream(DepartmentCodeUtil.getAllSecondDepCodes())
                .noneMatch(e -> e.endsWith("00")));
    }
}
