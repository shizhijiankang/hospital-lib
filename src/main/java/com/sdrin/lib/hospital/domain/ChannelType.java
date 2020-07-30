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

import java.util.Objects;

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/7/28 12:06 下午
 * 渠道类型，例如支付、挂号，充值等是通过哪个渠道完成的，
 *
 * @author 胡树铭
 */
public enum ChannelType {

    UNTHOWN("99", "非正规渠道来源");

    /**
     * 编码，
     */
    private String code;

    /**
     * 中文名
     */
    private String value;

    ChannelType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return value;
    }

    /**
     * 根据编码获取对应枚举类, 处方类别
     *
     * @param code 码
     * @return 返回对象
     */
    public static ChannelType getByCode(String code) {
        for (ChannelType channelType : values()) {
            if (Objects.equals(channelType.getCode(), code)) {
                return channelType;
            }
        }
        return UNTHOWN;
    }
}
