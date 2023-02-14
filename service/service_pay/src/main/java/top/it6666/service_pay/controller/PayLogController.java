package top.it6666.service_pay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_pay.service.PayLogService;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author BNTang
 * @since 2021-06-05
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/service_pay/pay-log")
public class PayLogController {
    private final PayLogService payService;

    /**
     * 订单支付
     * 
     * @param orderNo
     *            订单号
     * @return 支付页面
     */
    @GetMapping("/aliPay/{orderNo}")
    public ResponseResult aliPay(@PathVariable String orderNo) {
        return ResponseResult.ok().data("form", payService.aliPay(orderNo));
    }
}