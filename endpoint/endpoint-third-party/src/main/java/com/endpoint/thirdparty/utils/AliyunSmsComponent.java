package com.endpoint.thirdparty.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云短信发送组件（参考 health_parent 项目 SMSUtils 改造）
 * 敏感信息（AK/SK）全部通过环境变量注入，不落源码
 */
@Slf4j
@ConfigurationProperties(prefix = "aliyun.sms")
@Data
@Component
public class AliyunSmsComponent {

    /** 阿里云 AccessKeyId，来自环境变量 ALIYUN_SMS_ACCESS_KEY_ID */
    private String accessKeyId;
    /** 阿里云 AccessKeySecret，来自环境变量 ALIYUN_SMS_ACCESS_KEY_SECRET */
    private String accessKeySecret;
    /** 短信签名（需在阿里云控制台审核通过） */
    private String signName;
    /** 验证码短信模板 ID，如 SMS_165366012 */
    private String templateCode;

    /**
     * 发送短信验证码
     *
     * @param phoneNumbers 手机号（国内号码，无需 +86 前缀）
     * @param code         验证码
     */
    public void sendSmsCode(String phoneNumbers, String code) {
        if (StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(accessKeySecret)) {
            log.warn("阿里云短信 AK/SK 未配置（ALIYUN_SMS_ACCESS_KEY_ID/SECRET），跳过发送: phone={}", phoneNumbers);
            return;
        }
        try {
            // 设置超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            // 短信API产品名称与域名（固定，无需修改）
            final String product = "Dysmsapi";
            final String domain = "dysmsapi.aliyuncs.com";
            // 初始化ascClient
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            // 组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            request.setMethod(MethodType.POST);
            // 必填:待发送手机号
            request.setPhoneNumbers(phoneNumbers);
            // 必填:短信签名
            request.setSignName(signName);
            // 必填:短信模板
            request.setTemplateCode(templateCode);
            // 模板变量替换：模板内容为"您的验证码为${code}"
            request.setTemplateParam("{\"code\":\"" + code + "\"}");
            // 发起请求
            SendSmsResponse response = acsClient.getAcsResponse(request);
            if (response.getCode() != null && "OK".equals(response.getCode())) {
                log.info("阿里云短信发送成功: phone={}", phoneNumbers);
            } else {
                log.error("阿里云短信发送失败: phone={}, code={}, message={}", phoneNumbers, response.getCode(), response.getMessage());
            }
        } catch (Exception e) {
            log.error("阿里云短信发送异常: phone={}", phoneNumbers, e);
        }
    }
}
