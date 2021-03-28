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

package com.sdrin.lib.hospital.domain.http;

import com.sdrin.lib.hospital.config.Constant;
import com.sdrin.lib.hospital.util.date.DateTimeUtil;

import java.time.LocalDateTime;

import static com.sdrin.lib.hospital.config.Constant.*;

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/6/7 1:01 下午
 * <p>
 * 在进行和上海石指慧眼系统进行交互时，强烈建议请求对象按照此对象，
 * 签名的规则：如果 {@link #needSign()} ，则必须签名，否则报错
 * 加密的规则：如果 {@link #needEncrypt()} 则必须加密，否则报错
 * 如果需要rsa签名和加密，则此为提交的request body，
 * 可以参考：http://simulate-his.sdrin.com/docs/index.html#_4_数字签名和数据加密
 * 这个对象主要是，his与上海石指的交互api对象
 *
 * @author 胡树铭
 */
public class SHttpRequest extends BaseHttpRequest {
    /**
     * 连接对方系统的唯一账户id，由对方系统分配，
     */
    private String appId;

    public SHttpRequest() {
    }

    public SHttpRequest(String appId, boolean needSign, boolean needEncrypt) {
        super(needSign, needEncrypt);
        this.appId = appId;
    }

    public SHttpRequest(String appId, String bizContent, boolean needSign, boolean needEncrypt) {
        super(needSign, needEncrypt);
        this.appId = appId;
        this.bizContent = bizContent;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "SHttpRequest{" +
                "bizContent='" + bizContent + '\'' +
                ", signType='" + signType + '\'' +
                ", sign='" + sign + '\'' +
                ", encType='" + encType + '\'' +
                ", letter='" + letter + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", version='" + version + '\'' +
                ", appId='" + appId + '\'' +
                "} ";
    }
}

