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

package com.sdrin.lib.hospital.util.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sdrin.lib.hospital.config.Constant;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/6/27 9:45 上午
 *
 * @author 胡树铭
 */
class JsonUtilTest {

    /**
     * 测试json java相互转化，和时间日期格式。
     */
    @Test
    void toJson() {
        TestA testA = new TestA();
        testA.name = "name";
        testA.localDate = LocalDate.now();
        testA.localDateTime = LocalDateTime.now();
        String json = JsonUtil.toJson(testA);
        System.out.println(JsonUtil.toJson(testA));

        TestA t = JsonUtil.fromJson(json, TestA.class);
        // 测试了时间
        assertNotNull(t.localDateTime);
    }


    @Test
    void parseList() {
        String json = "[20, 15, -1, 22]";
        List<String> list = JsonUtil.parseList(json, String.class);
        assertEquals(list.get(1), "15");
    }

    @Test
    void parseMap() {
        //反序列化（json转Map）
        String json = "{\"name\" : \"历史\", \"id\":123, \"age\":12.2}";
        Map<Object, Object> map = JsonUtil.parseMap(json, Object.class, Object.class);
        assertEquals(map.get("name"), "历史");
    }

    @Test
    void nativeRead() {
        String json = "[{\"name\":\"李四\", \"age\":12.3}, {\"name\":\"王文\", \"age\":16.3}]";
        List<Map<String, String>> maps = JsonUtil.nativeRead(json, new TypeReference<List<Map<String, String>>>() {
        });
        assertEquals(maps.size(), 2);
    }

    static class TestA {
        public String name;
        public String password;
        /**
         * 如果需要使用 java8 time，需要下面的注释。
         */
        @JsonFormat(pattern = Constant.LOCAL_DATE_TIME_FORMAT)
        public LocalDateTime localDateTime;
        //        @JsonFormat(pattern = "yyyy-MM-dd")
        public LocalDate localDate;

        public TestA() {
        }

        @Override
        public String toString() {
            return "TestA{" +
                    "name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    ", localDateTime=" + localDateTime +
                    ", localDate=" + localDate +
                    '}';
        }
    }

}
