package com.cool.modules.user.proxy;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.cool.modules.user.config.AlipayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 支付宝代理类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AlipayProxy {
    
    private final AlipayConfig alipayConfig;
    
    private AlipayClient alipayClient;
    
    /**
     * 获取支付宝客户端
     */
    public AlipayClient getAlipayClient() {
        if (alipayClient == null) {
            synchronized (this) {
                if (alipayClient == null) {
                    String serverUrl = alipayConfig.isSandbox() ? 
                        alipayConfig.getSandboxServerUrl() : alipayConfig.getServerUrl();
                    
                    alipayClient = new DefaultAlipayClient(
                        serverUrl,
                        alipayConfig.getAppId(),
                        alipayConfig.getMerchantPrivateKey(),
                        alipayConfig.getFormat(),
                        alipayConfig.getCharset(),
                        alipayConfig.getAlipayPublicKey(),
                        alipayConfig.getSignType()
                    );
                    
                    log.info("支付宝客户端初始化成功，使用{}环境", alipayConfig.isSandbox() ? "沙箱" : "正式");
                }
            }
        }
        return alipayClient;
    }
}
