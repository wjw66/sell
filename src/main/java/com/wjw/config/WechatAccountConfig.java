package com.wjw.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Data
@Component
/**
 * 读取application.yml文件中配置的信息
 */
@ConfigurationProperties(prefix = "wechat")
@PropertySource("classpath:application.yml")
/**
 * 微信账号相关的配置
 * @author : wjwjava@163.com
 * @date : 23:51 2019/11/11
 */
public class WechatAccountConfig {
    /**
     * 公众号的appID
     */
    private String mpAppId;
    /**
     * 公众号的appsecret
     */
    private String mpAppSecret;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 商户秘钥
     */
    private String mchKey;
    /**
     * 商户证书路径
     */
    private String keyPath;
    /**
     * 微信支付异步通知地址
     */
    private String notifyUrl;
}
