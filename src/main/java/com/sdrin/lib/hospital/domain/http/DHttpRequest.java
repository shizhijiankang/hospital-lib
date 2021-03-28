/*
 *   Copyright 2019-2021 上海石指健康科技有限公司
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.sdrin.lib.hospital.domain.http;

/**
 * @author 胡树铭 -- @Date 2021/3/27 9:59 下午
 * 这个只是针对石指的 设备进行的 api 交互的对象
 */
public class DHttpRequest extends BaseHttpRequest {
    /**
     * 设备的唯一编码
     */
    private String deviceId;

    /**
     * 项目的唯一编码
     */
    private String projectId;

    public DHttpRequest() {
    }

    public DHttpRequest(String deviceId, String projectId, boolean needSign, boolean needEncrypt) {
        super(needSign, needEncrypt);
        this.deviceId = deviceId;
        this.projectId = projectId;
    }

    public DHttpRequest(String deviceId, String projectId, String bizContent, boolean needSign, boolean needEncrypt) {
        super(needSign, needEncrypt);
        this.deviceId = deviceId;
        this.projectId = projectId;
        this.bizContent = bizContent;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    @Override
    public String toString() {
        return "DHttpRequest{" +
                "bizContent='" + bizContent + '\'' +
                ", signType='" + signType + '\'' +
                ", sign='" + sign + '\'' +
                ", encType='" + encType + '\'' +
                ", letter='" + letter + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", version='" + version + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", projectId='" + projectId + '\'' +
                "} ";
    }
}
