package com.endpoint.thirdparty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.thirdparty.config.AlipayTemplate;
import com.endpoint.thirdparty.entity.OrderPay;
import com.endpoint.thirdparty.feign.MemberFeignService;
import com.endpoint.thirdparty.mapper.OrderPayMapper;
import com.endpoint.thirdparty.service.IOrderPayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.endpoint.thirdparty.vo.PayVo;
import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

/**
 * <p>
 * 充值订单 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class OrderPayServiceImpl extends ServiceImpl<OrderPayMapper, OrderPay> implements IOrderPayService {

    @Autowired
    private AlipayTemplate alipayTemplate;
    @Autowired
    private MemberFeignService memberFeignService;

    @SneakyThrows
    @Override
    public String createPayOrder(Integer payAmount,Long  userId) {
        //1.设置根据充值金额在order_pay表生成订单数据,订单状态默认为2 待支付
        Date currentDate = new Date();
        Long outTradeNo = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(currentDate)+new Random().nextInt(10));
        OrderPay orderPay = new OrderPay();
        orderPay.setOutTradeNo(outTradeNo);
        orderPay.setPayChannel(1);//1支付宝
        orderPay.setTotalAmount(payAmount);
        orderPay.setUserId(userId);
        orderPay.setCreateTime(LocalDateTime.now());
        orderPay.setUpdateTime(LocalDateTime.now());
        baseMapper.insert(orderPay);
        //2.补充支付需要参数调用支付宝模板返回响应 字符串
        PayVo payVo = new PayVo();
        payVo.setOut_trade_no(outTradeNo.toString());
        payVo.setTotal_amount(payAmount.toString());
        payVo.setSubject("终点小说网充值");
        payVo.setBody("充值终点币");
        String body = alipayTemplate.pay(payVo);
        return body;
    }

    @Override
    public void updatePayOrder(Long outTradeNo, String tradeNo, String tradeStatus) {
        //1.查询当前订单数据
        OrderPay orderPay = baseMapper.selectOne(new QueryWrapper<OrderPay>().eq("out_trade_no", outTradeNo));

        if(orderPay!=null&&orderPay.getPayStatus()!=1){ //此订单还未处理
            if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
                //支付成功
                //1.更新订单状态为成功
                orderPay.setPayStatus(1);
                orderPay.setUpdateTime(LocalDateTime.now());
                baseMapper.updateById(orderPay);
                //2.增加用户余额
                memberFeignService.addAmount(orderPay.getUserId(),orderPay.getTotalAmount()*100);
            }
        }
    }
}
