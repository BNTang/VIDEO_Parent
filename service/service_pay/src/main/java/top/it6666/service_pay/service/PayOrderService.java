package top.it6666.service_pay.service;

import com.baomidou.mybatisplus.extension.service.IService;

import top.it6666.service_pay.entity.PayOrder;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author BNTang
 * @since 2021-06-05
 */
public interface PayOrderService extends IService<PayOrder> {

    /**
     * 根据作品id和用户id创建订单，返回订单id
     * 
     * @param contentId
     *            作品ID
     * @param userId
     *            用户Id
     * @return 订单号
     */
    String saveOrder(String contentId, String userId);

    /**
     * 根据订单id查询订单信息
     * 
     * @param orderNo
     *            订单Id
     * @return 订单信息
     */
    PayOrder getOrderByOrderNo(String orderNo);
}