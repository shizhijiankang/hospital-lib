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

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/7/5 11:10 上午
 *
 * @author 胡树铭
 */
class PositionCodeUtilTest {
    private String positionPath = "/设备放置处/position-code.txt";
    private String departmentPath = "/科室编码/department-code.txt";

    @Test
    void getAllCheckCode() {
        Arrays.stream(PositionCodeUtil.getAllPositionCode(positionPath, departmentPath))
                .forEach(System.out::println);
    }

    @Test
    void getCheckNameByCode() {
        assertEquals(PositionCodeUtil.getPositionNameByCode(positionPath, departmentPath, "0100"), "内科");
        assertEquals(PositionCodeUtil.getPositionNameByCode(positionPath, departmentPath, "0101"), "心血管内科");
        assertEquals(PositionCodeUtil.getPositionNameByCode(positionPath, departmentPath, "9000"), "抽血");

    }
}
