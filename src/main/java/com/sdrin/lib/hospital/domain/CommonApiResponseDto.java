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

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/7/22 6:59 下午
 * 记录api请求中，公共的返回code和消息。返回码，详见：http://simulate-his.sdrin.com/docs/index.html#_6_api_公共错误码
 *
 * @author 胡树铭
 */
public enum CommonApiResponseDto {
    OK,
    FAIL,
    INVALID_TOKEN,
    EXPIRE_TOKEN,
    UNAUTHORIZED,
    ERR_SIGN,
    ERR_SIGN_TYPE,
    ERR_APP_ID,
    ERR_TIMESTAMP,
    ERR_VERSION,
    ERR_BIZ_CONTENT,
    ERR_LETTER,
    ERR_ENC_TYPE,
    OTHER;

    public String toCode() {
        switch (this) {
            case OK:
                return "10000";
            case FAIL:
                return "20000";
            case INVALID_TOKEN:
                return "20001";
            case EXPIRE_TOKEN:
                return "20002";
            case UNAUTHORIZED:
                return "20003";
            case ERR_SIGN:
                return "20100";
            case ERR_SIGN_TYPE:
                return "20101";
            case ERR_APP_ID:
                return "20102";
            case ERR_TIMESTAMP:
                return "20103";
            case ERR_VERSION:
                return "20104";
            case ERR_BIZ_CONTENT:
                return "20105";
            case ERR_LETTER:
                return "20106";
            case ERR_ENC_TYPE:
                return "20107";
            case OTHER:
                return "20900";
            default:
                return null;
        }
    }

    public String toChinese() {
        switch (this) {
            case OK:
                return "接口调用成功";
            case FAIL:
                return "服务不可用";
            case INVALID_TOKEN:
                return "无效的访问令牌";
            case EXPIRE_TOKEN:
                return "访问令牌已过期";
            case UNAUTHORIZED:
                return "未授权当前接口";
            case ERR_SIGN:
                return "签名参数缺少或错误";
            case ERR_SIGN_TYPE:
                return "签名类型参数缺少或错误";
            case ERR_APP_ID:
                return "appId参数缺少或错误";
            case ERR_TIMESTAMP:
                return "时间戳参数缺少或错误";
            case ERR_VERSION:
                return "版本参数缺少或错误";
            case ERR_BIZ_CONTENT:
                return "业务内容缺少或错误";
            case ERR_LETTER:
                return "加密信封缺少或错误";
            case ERR_ENC_TYPE:
                return "加密类型缺少或错误";
            case OTHER:
                return "";// 后续补充。
            default:
                return null;
        }
    }

}
