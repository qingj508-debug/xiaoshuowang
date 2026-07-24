package com.endpoint.thirdparty.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 充值订单
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Data
@TableName("order_pay")
public class OrderPay implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户订单号
     */
    private Long outTradeNo;

    /**
     * 支付宝/微信交易号
     */
    private String tradeNo;

    /**
     * 支付渠道，1：支付宝，2：微信
     */
    private Integer payChannel;

    /**
     * 交易金额(单位元)
     */
    private Integer totalAmount;

    /**
     * 支付用户ID
     */
    private Long userId;

    /**
     * 支付状态：0：支付失败，1：支付成功，2：待支付
     */
    private Integer payStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
