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
 * 上海石指(健康)科技有限公司 sdrin.com 2020/7/28 9:41 上午
 * 费用包含：门诊和住院，根据编码 1 开头是门诊，2开头的是住院， 5开头的是 既是住院也是门诊，都包含此类别
 * ，
 * 门诊电子处方的类别，用在诊间结算时，医生开完处方，石指调取某患者当前所需支付的费用，此时需要记录费用的类别，就是此对象
 *
 * @author 胡树铭
 */
public enum MedicalFeeType {
    // 下面是一般的门诊费用类别
    REGISTRATION_FEE("1000", "挂号费"),

    // 下面是住院的门诊费用类别
    BED_FEE("2000", "床位费"),
    NURSING_FEE("2001", "护理费"),

    // 门诊和住院都包含费用类别
    DIAGNOSTIC_FEE("5000", "诊察费"),
    INSPECTION_FEE("5001", "检查费"),
    LABORATORY_FEE("5002", "化验费"),
    TREAT_FEE("5003", "治疗费"),
    SURGICAL_FEE("5004", "手术费"),
    SANITARY_MATERIALS_FEE("5005", "卫生材料费"),
    WESTERN_MEDICAL("5006", "西药费"),
    CHINESE_HERBAL_FEE("5007", "中药饮片"),
    CHINESE_PATENT_MEDICINE_FEE("5008", "中成药费"),
    GENERAL_MEDICAL_FEE("5009", "一般诊疗费"),

    // 异常
    UNTHOWN("9999", "非正规渠道来源");

    /**
     * 编码，
     */
    private String code;

    /**
     * 中文名
     */
    private String value;

    MedicalFeeType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    /**
     * 根据编码获取对应枚举类, 处方类别
     *
     * @param code 码
     * @return 返回对象
     */
    public static MedicalFeeType getByCode(String code) {
        for (MedicalFeeType medicalFeeType : values()) {
            if (Objects.equals(medicalFeeType.getCode(), code)) {
                return medicalFeeType;
            }
        }
        return null;
    }
}
