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

import com.sdrin.lib.hospital.domain.CodeValueItem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/7/5 10:14 上午
 * 科室编码的工具，可以查询科室编码
 *
 * @author 胡树铭
 */
public class DepartmentCodeUtil {


    /**
     * 从本地 resource 里，读取科室配置文本，都在目录 /resource/科室编号/**.txt 里面，
     *
     * @param resourcePath txt 的编码文件，code和name之间使用tab隔离。如 src/main/resource/科室编码/department-code.txt ,
     *                     传递的参数是：/科室编码/department-code.txt
     * @return 返回全部的编码数据
     */
    public static CodeValueItem[] getAllDepCode(String resourcePath) {
        // 总的科室编码的数据
        InputStream in = DepartmentCodeUtil.class.getResourceAsStream(resourcePath);
        if (in != null) {
            return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)).lines()
                    .map(e -> {
                        String[] arr = e.split("\t");
                        return new CodeValueItem(arr[0], arr[1]);
                    })
                    .toArray(CodeValueItem[]::new);
        }
        return null;
    }

    /**
     * 通过科室的 code，获取到对应的中文name
     *
     * @param resourcePath txt 的编码文件，code和name之间使用tab隔离。如 src/main/resource/科室编码/department-code.txt ,
     *                     传递的参数是：/科室编码/department-code.txt
     * @param code         科室的code
     * @return 返回对应的中文name
     */
    public static String getDepNameByCode(String resourcePath, String code) {
        return Stream.of(getAllDepCode(resourcePath))
                .filter(e -> e.getCode().equals(code))
                .map(CodeValueItem::getValue)
                .findAny()
                .orElse(null);
    }

    /**
     * 返回自定义的科室编码的，返回所有的二级科室的编码。
     *
     * @param resourcePath txt 的编码文件，code和name之间使用tab隔离。如 src/main/resource/科室编码/department-code.txt ,
     *                     传递的参数是：/科室编码/department-code.txt
     * @return 只返回二级科室的编码数据
     */
    public static String[] getAllSecondDepCodes(String resourcePath) {
        return Stream.of(getAllDepCode(resourcePath))
                .map(CodeValueItem::getCode)
                .filter(e -> !e.endsWith("00"))
                .toArray(String[]::new);
    }


}
