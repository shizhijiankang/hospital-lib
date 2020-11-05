package com.sdrin.lib.hospital.domain;

import java.util.Objects;

public enum MedicalType {

    REGISTRATION("01", "挂号"),
    OUT_PATIENT("02", "门诊"),
    HOSPITALIZATION("03", "住院");

    /**
     * 编码，
     */
    private String code;

    /**
     * 中文名
     */
    private String value;

    MedicalType(String code, String value) {
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
    public static MedicalType getByCode(String code) {
        for (MedicalType medicalType : values()) {
            if (Objects.equals(medicalType.getCode(), code)) {
                return medicalType;
            }
        }
        return null;
    }

}
