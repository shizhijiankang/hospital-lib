package com.sdrin.lib.hospital.domain.http;

import com.sdrin.lib.hospital.config.Constant;
import com.sdrin.lib.hospital.util.date.DateTimeUtil;

import java.time.LocalDateTime;

import static com.sdrin.lib.hospital.config.Constant.*;

/**
 * 最基础的http 的 交互对象，包含了签名，加密等变量
 */
public abstract class BaseHttpRequest {
    /**
     * 具体的业务内容，json string 格式。
     */
    protected String bizContent;
    /**
     * 签名类型，目前支持的是：MD5+RSA，参考：http://simulate-his.sdrin.com/docs/index.html#_4_1_1_1_签名流程
     */
    protected String signType;
    /**
     * 根据MD5+RSA做的数字签名密文
     */
    protected String sign;
    /**
     * 加密技术类型，默认是 AES+RSA，是否加密由业务场景而定, 一般情况下，请求服务器是 {@code HTTPS} 则无需加密，
     * 否则涉及到敏感内容的均需加密：参考 http://simulate-his.sdrin.com/docs/index.html#_4_2_数据加密
     */
    protected String encType;
    /**
     * 是上面数字信封加密后的保存的数字信封，先通过AES对称算法生成随机对称密钥，
     * 和利用AES对称算法对bizContent加密得到密文A,；再使用上海石指公钥和RSA2算法对"对称密钥"进行加密得到密文B，密文B就是数字信封。
     * 也就是这里的变量 letter
     */
    protected String letter;
    /**
     * 当前时间点，也是时间戳。
     */
    protected String timestamp;
    /**
     * 当前测版本。默认是 1.0
     */
    protected String version;

    public BaseHttpRequest() {

    }

    public BaseHttpRequest(boolean needSign, boolean needEncrypt) {
        this.timestamp = DateTimeUtil.toString(LocalDateTime.now());
        this.version = HTTP_VERSION;
        if (needEncrypt) {
            this.encType = Constant.ENC_TYPE;
        }
        if (needSign) {
            this.signType = Constant.SIGN_TYPE;
        }
    }

    /**
     * 判断该对象是否需要签名，自定义的判断
     *
     * @return 需要则返回true
     */
    public boolean needSign() {
        return this.signType != null && this.signType.equals(SIGN_TYPE);
    }

    /**
     * 判断该对象是否需要加密，自定义的判断
     *
     * @return 需要则返回true
     */
    public boolean needEncrypt() {
        return this.encType != null && this.encType.equals(ENC_TYPE);
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEncType() {
        return encType;
    }

    public void setEncType(String encType) {
        this.encType = encType;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BaseHttpRequest{" +
                "bizContent='" + bizContent + '\'' +
                ", signType='" + signType + '\'' +
                ", sign='" + sign + '\'' +
                ", encType='" + encType + '\'' +
                ", letter='" + letter + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
