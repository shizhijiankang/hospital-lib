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

import java.util.Objects;

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/7/26 17:19
 * 记录api请求中，公共的返回code和消息。返回码，详见：http://simulate-his.sdrin.com/docs/index.html#_6_api_公共错误码
 *
 * @author 谢海波
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommonApiResponseDto {
    OK("10000", "接口调用成功"),
    FAIL("20000", "服务不可用"),
    INVALID_TOKEN("20001", "无效的访问令牌"),
    EXPIRE_TOKEN("20002", "访问令牌已过期"),
    UNAUTHORIZED("20003", "未授权当前接口"),
    ERR_SIGN("20100", "签名参数缺少或错误"),
    ERR_SIGN_TYPE("20101", "签名类型参数缺少或错误"),
    ERR_APP_ID("20102", "appId参数缺少或错误"),
    ERR_TIMESTAMP("20103", "时间戳参数缺少或错误"),
    ERR_VERSION("20104", "版本参数缺少或错误"),
    ERR_BIZ_CONTENT("20105", "业务内容缺少或错误"),
    ERR_LETTER("20106", "加密信封缺少或错误"),
    ERR_ENC_TYPE("20107", "加密类型缺少或错误"),
    OTHER("20900", ""),
    UNTHOWN("99999", "未知异常"),
    ;

    /**
     * 状态码
     */
    private String code;

    /**
     * 返回消
     */
    private String msg;

    CommonApiResponseDto(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 根据编码获取对应枚举类
     *
     * @param code 码
     * @return 返回对象
     */
    public static CommonApiResponseDto getByCode(String code) {
        for (CommonApiResponseDto responseDto : values()) {
            if (Objects.equals(responseDto.getCode(), code)) {
                return responseDto;
            }
        }
        return UNTHOWN;
    }
}
