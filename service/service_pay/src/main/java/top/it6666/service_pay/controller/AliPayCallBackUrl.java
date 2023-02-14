/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_pay.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import top.it6666.common_utils.utils.ServletUtils;
import top.it6666.service_pay.common.properties.AliPayProperties;
import top.it6666.service_pay.entity.PayOrder;
import top.it6666.service_pay.service.PayLogService;
import top.it6666.service_pay.service.PayOrderService;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 支付宝回调控制器
 * @since Created in 2021/6/6 006 18:27
 **/
@Controller
@RequestMapping("/callback")
@Api(tags = "支付")
@RequiredArgsConstructor
public class AliPayCallBackUrl {
    /**
     * 编码
     */
    private static final String CHARSET = "UTF-8";

    /**
     * 支付日志
     */
    private final PayLogService payLogService;

    /**
     * 订单
     */
    private final PayOrderService payOrderService;

    /**
     * 配置
     */
    private final AliPayProperties aliPayProperties;

    /**
     * 只有付款成功才会跳转到这里且只跳转一次 浏览器调用 重定向形式,不做跟订单相关的事 页面跳转
     */
    @ApiOperation(value = "只有付款成功才会跳转到这里且只跳转一次 浏览器调用 重定向形式")
    @GetMapping("/returnUrl")
    public String returnUrl() {
        HttpServletResponse response = ServletUtils.getResponse();
        HttpServletRequest request = ServletUtils.getRequest();

        response.setContentType("text/html;charset=" + CHARSET);

        try {
            // 获取支付宝GET过来反馈信息
            boolean signVerified = checkSignature(request);
            if (signVerified) {
                // 跳转到作品详情页 哪一个作品?
                // 商户订单号
                String outTradeNo =
                    new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), CHARSET);

                // 获取订单
                PayOrder payOrder = payOrderService.getOrderByOrderNo(outTradeNo);

                // 获取作品id
                String url = "http://localhost:3000/video/" + payOrder.getContentId();
                return "redirect:" + url;
            }
        } catch (AlipayApiException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 服务器通知 : 1. ip地址一定是公网的，私有地址支付宝无法通知到客户端 2. 请求方式是POST请求
     */
    @PostMapping("/notifyUrl")
    public void notifyUrl() {
        // 支付宝调用 return success 如果没有返回 还会继续调用
        HttpServletResponse response = ServletUtils.getResponse();
        HttpServletRequest request = ServletUtils.getRequest();

        try {
            PrintWriter out = response.getWriter();

            boolean signVerified = this.checkSignature(request);
            if (signVerified) {
                // 以下信息是支付定在请求当中写入信息
                // 商户订单号
                String outTradeNo =
                    new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), CHARSET);

                // 支付宝交易号
                String transactionId =
                    new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), CHARSET);

                // 交易状态
                String tradeStatus =
                    new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), CHARSET);

                // 更新订单
                HashMap<String, String> map = new HashMap<>(1 << 4);
                map.put("out_trade_no", outTradeNo);
                map.put("trade_state", tradeStatus);
                map.put("transaction_id", transactionId);

                if ("TRADE_FINISHED".equals(tradeStatus)) {
                    // 判断该笔订单是否在商户网站中已经做过处理
                    // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
                    // 并执行商户的业务程序
                    // 如果有做过处理，不执行商户的业务程序
                    PayOrder payOrder = payOrderService.getOrderByOrderNo(outTradeNo);

                    if (payOrder.getStatus() != 1) {
                        payLogService.updateOrdersStatus(map);
                    }

                } else if ("TRADE_SUCCESS".equals(tradeStatus)) {
                    payLogService.updateOrdersStatus(map);
                }
                out.println("success");
            } else {
                out.println("fail");
            }
        } catch (AlipayApiException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验签名
     */
    private boolean checkSignature(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = new HashMap<>(1 << 4);
        Map<String, String[]> requestParams = request.getParameterMap();

        requestParams.keySet().forEach(name -> {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        });
        return AlipaySignature.rsaCheckV1(params, aliPayProperties.getPublvicKey(), CHARSET, "RSA2");
    }
}