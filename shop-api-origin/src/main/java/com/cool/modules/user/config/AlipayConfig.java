package com.cool.modules.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付宝配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {
    
    /**
     * 应用ID
     */
    private String appId;
    
    /**
     * 商户私钥
     */
    private String merchantPrivateKey;
    
    /**
     * 支付宝公钥
     */
    private String alipayPublicKey;
    
    /**
     * 支付宝网关
     */
    private String serverUrl = "https://openapi.alipay.com/gateway.do";
    
    /**
     * 沙箱网关
     */
    private String sandboxServerUrl = "https://openapi.alipaydev.com/gateway.do";
    
    /**
     * 格式
     */
    private String format = "json";
    
    /**
     * 编码
     */
    private String charset = "UTF-8";
    
    /**
     * 签名类型
     */
    private String signType = "RSA2";
    
    /**
     * 是否沙箱环境
     */
    private boolean sandbox = true;
    
    /**
     * 异步通知地址
     */
    private String notifyUrl;
    
    /**
     * 同步返回地址
     */
    private String returnUrl;
}
