package top.it6666.service_pay.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import top.it6666.common_utils.utils.JwtUtil;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_pay.entity.PayOrder;
import top.it6666.service_pay.service.PayOrderService;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author BNTang
 * @since 2021-06-05
 */
@RestController
@RequestMapping("/service_pay/pay-order")
@Api(tags = "订单组")
@RequiredArgsConstructor
public class PayOrderController {
    private final PayOrderService orderService;

    /**
     * 根据作品id和用户id创建订单，返回订单id
     */
    @ApiOperation(value = "根据作品id和用户id创建订单，返回订单id")
    @PostMapping("createOrder/{contentId}")
    public ResponseResult save(@PathVariable String contentId, HttpServletRequest request) {
        return ResponseResult.ok().data("orderId",
            orderService.saveOrder(contentId, JwtUtil.getUserIdByJwtToken(request)));
    }

    /**
     * 根据订单id查询订单信息
     */
    @ApiOperation(value = "根据订单id查询订单信息")
    @GetMapping("/getOrderInfo/{orderNo}")
    public ResponseResult getOrderInfo(@PathVariable String orderNo) {
        return ResponseResult.ok().data("item", orderService.getOrderByOrderNo(orderNo));
    }

    /**
     * 根据 `用户id` 和 `作品id` 查询该用户是否已经购买该作品请求
     */
    @ApiOperation(value = "根据 `用户id` 和 `作品id` 查询该用户是否已经购买该作品请求")
    @GetMapping("/isBuyContent/{userid}/{contentId}")
    public boolean isBuyContent(@PathVariable String userid, @PathVariable String contentId) {
        // 订单状态 是1表示支付成功
        int count = orderService
            .count(new QueryWrapper<PayOrder>().eq("user_id", userid).eq("content_id", contentId).eq("status", 1));
        return count > 0;
    }
}