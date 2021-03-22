/*
 * Copyright (c) 2019 - 2020 上海石指（健康）科技有限公司. All Rights Reserved
 */

package com.sdrin.lib.hospital.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 上海石指(健康)科技有限公司 2020/5/24 12:32 下午
 * 医疗 表示证件类型
 *
 * @author 胡树铭
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CertType {
    ID("01"),
    FAMILY_REGISTER("02"),
    PASSPORT("03"),
    OFFICIAL_CARD("04"),
    DRIVING_LICENCE("05"),
    HK_MACAU("06"),
    TAIWAN("07"),
    OTHER_CERT("99");

    private String code;

    public String getCode() {
        return code;
    }

    CertType(String code) {
        this.code = code;
    }

    /**
     * 返回中文解释
     *
     * @return 中文解释
     */
    public String toChinese() {
        String result = null;
        switch (this) {
            case ID:
                result = "居民身份证";
                break;
            case FAMILY_REGISTER:
                result = "居民户口簿";
                break;
            case PASSPORT:
                result = "护照";
                break;
            case OFFICIAL_CARD:
                result = "军官证";
                break;
            case DRIVING_LICENCE:
                result = "驾驶证";
                break;
            case HK_MACAU:
                result = "港澳居⺠来往内地通⾏证";
                break;
            case TAIWAN:
                result = "台湾居⺠来往内地通⾏证";
                break;
            case OTHER_CERT:
                result = "其他法定有效证件";
                break;
            default:
                break;

        }
        return result;
    }

    public static CertType numToCertType(String code) {

        switch (code) {
            case "01":
                return ID;
            case "02":
                return FAMILY_REGISTER;
            case "03":
                return PASSPORT;
            case "04":
                return OFFICIAL_CARD;
            case "05":
                return DRIVING_LICENCE;
            case "06":
                return HK_MACAU;
            case "07":
                return TAIWAN;
            case "99":
                return OTHER_CERT;
            default:
                return null;
        }
    }

    /**
     * 检查 code 是否正确
     *
     * @param code code参数，就是 01-07，99 等
     * @return 返回是否是验证码
     */
    public static boolean checkCode(String code) {
        return numToCertType(code) != null;
    }

}
