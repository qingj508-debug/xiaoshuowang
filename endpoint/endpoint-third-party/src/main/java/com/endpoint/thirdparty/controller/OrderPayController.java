package com.endpoint.thirdparty.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.thirdparty.config.AlipayTemplate;
import com.endpoint.thirdparty.service.IOrderPayService;
import com.endpoint.thirdparty.vo.PayAsyncVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.lang.System.out;

/**
 * <p>
 * 充值订单 前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Controller
@RequestMapping("/thirdparty/orderPay")
public class OrderPayController {

    @Autowired
    private IOrderPayService orderPayService;

    @Autowired
    private AlipayTemplate alipayTemplate;

    @GetMapping("aliPay")
    @ResponseBody
    public ResultBean<Map> aliPay( Integer payAmount, @RequestHeader ("Authorization") String token) {
        Long userId = JwtTokenUtil.getUserIdFromToken(token);
        //调用支付业务方法,返回支付宝支付页面字符串
        String body = orderPayService.createPayOrder(payAmount,userId);
        Map<String,Object> map = new HashMap();
        map.put("body",body);

        return ResultBean.ok(map);
    }


    @PostMapping("/notify")
    @ResponseBody
    public String  payedNotify(PayAsyncVo payAsyncVo, HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
        //只要我们收到支付宝给我们的异步通知,告诉我们订单支付成功,返回success,支付宝再也不通知
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params,alipayTemplate.getAlipay_public_key(), alipayTemplate.getCharset(), alipayTemplate.getSign_type()); //调用SDK验证签名 //调用SDK验证签名
        if(signVerified){//验签通过
            //商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //更新订单状态
            orderPayService.updatePayOrder(Long.parseLong(outTradeNo), tradeNo, tradeStatus);
            return "success";
        }else {//验证失败
            return "fail";
        }
    }



}
