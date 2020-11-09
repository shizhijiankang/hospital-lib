package com.sdrin.lib.hospital.domain;

import java.util.Objects;

public enum MedicalBusinessType {

    REGISTER("1", "挂号"),
    OUT_PATIENT("2", "门诊"),
    EMERGENCY_TREATMENT("3", "急诊"),
    HOSPITALIZATION("4", "住院"),
    OPERATION("5", "手术");

    /**
     * 编码，
     */
    private String code;

    /**
     * 中文名
     */
    private String value;

    MedicalBusinessType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据编码获取对应枚举类, 处方类别
     *
     * @param code 码
     * @return 返回对象
     */
    public static MedicalBusinessType getByCode(String code) {
        for (MedicalBusinessType businessType : values()) {
            if (Objects.equals(businessType.getCode(), code)) {
                return businessType;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
