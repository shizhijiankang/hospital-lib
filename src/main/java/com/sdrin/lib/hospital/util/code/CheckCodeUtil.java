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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/7/5 10:49 上午
 * <p>
 * 医院签到的工具, 参考： <a href="http://simulate-his.sdrin.com/docs/index.html#_3_签到处编码">签到处编码</a>
 *
 * @author 胡树铭
 */
public class CheckCodeUtil {
    /**
     * 因为签到的编码包含了 科室的编码，所以这里进行整合。
     * 从本地 resource 里，读取科室配置文本，都在目录 /resource/签到/**.txt 里面，
     *
     * @return 返回全部的编码数据
     */
    public static CodeValueItem[] getAllCheckCode() {
        List<CodeValueItem> checkCodeList;
        // 先获取签到编码的数据
        InputStream in = CheckCodeUtil.class.getResourceAsStream("/签到/check-code.txt");
        if (in != null) {
            checkCodeList = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)).lines()
                    .map(e -> {
                        String[] arr = e.split("\t");
                        return new CodeValueItem(arr[0], arr[1]);
                    })
                    .collect(Collectors.toList());
            // 在添加科室编码，合成一起。
            return Stream.concat(checkCodeList.stream(), Stream.of(DepartmentCodeUtil.getAllDepCode()))
                    .toArray(CodeValueItem[]::new);
        }
        return null;
    }

    /**
     * 通过签到的 code，获取到对应的中文name
     *
     * @param code 签到的code
     * @return 返回对应的中文name
     */
    public static String getCheckNameByCode(String code) {
        return Stream.of(getAllCheckCode())
                .filter(e -> e.getCode().equals(code))
                .map(CodeValueItem::getValue)
                .findAny()
                .orElse(null);
    }

    public static void main(String[] args) {
        getAllCheckCode();
    }
}
