/*
 * Copyright (c) 2019 - 2020 上海石指（健康）科技有限公司. All Rights Reserved
 */

package com.sdrin.lib.hospital.domain.user;

import java.io.Serializable;
import java.util.Objects;

/**
 * 上海石指(健康)科技有限公司  2020/5/24 12:37 下午
 * 包含证件类型和号码的 class, 它是综合索引。
 *
 * @author 胡树铭
 */
public class Certificate implements Serializable {
    private String certType;  // 证件类型的编码
    private String certNum; // 证件号码


    public Certificate(CertType certType, String certNum) {
        this.certType = certType.getCode();
        this.certNum = certNum;
    }

    public String getCertType() {
        return certType;
    }

    public String getCertNum() {
        return certNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Certificate)) return false;
        Certificate that = (Certificate) o;
        return certType.equals(that.certType) &&
                Objects.equals(certNum, that.certNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certType, certNum);
    }
}
