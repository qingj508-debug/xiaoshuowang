package com.endpoint.thirdparty.service;

import com.endpoint.thirdparty.entity.OrderPay;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 充值订单 服务类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
public interface IOrderPayService extends IService<OrderPay> {

    String createPayOrder(Integer payAmount,Long userId);

    void updatePayOrder(Long outTradeNo, String tradeNo, String tradeStatus);
}
