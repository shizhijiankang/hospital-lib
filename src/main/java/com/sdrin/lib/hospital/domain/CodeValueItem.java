/*
 * Copyright (c) 2019 - 2020 上海石指（健康）科技有限公司. All Rights Reserved
 */

package com.sdrin.lib.hospital.domain;


/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/2/5 10:45 下午
 *
 * @author 胡树铭
 * <p>当查询科室，或者医生等数据时，需要编号和对应的中文的组合数据，所以此为最小的单位
 * 科室编码或签到处编码的基础类。
 * <a href="http://simulate-his.sdrin.com/docs/index.html#_1_科室表">科室编码</a>
 * <a href="http://simulate-his.sdrin.com/docs/index.html#_3_签到处编码">签到处编码</a>
 */
public class CodeValueItem {
    /**
     * 编码，例如一级科室编码为：0101
     */
    private String code;
    /**
     * 编码对应的值，对应的科室名字为：xx内科
     */
    private String value;

    public CodeValueItem(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "CodeValueItem{" +
                "code='" + code + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
