package top.it6666.service_pay.service;

import java.util.HashMap;

import com.baomidou.mybatisplus.extension.service.IService;

import top.it6666.service_pay.entity.PayLog;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author BNTang
 * @since 2021-06-05
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * PC网站支持
     * 
     * @param orderNo
     *            订单号
     * @return 支付页面
     */
    String aliPay(String orderNo);

    /**
     * 更新订单状态
     * 
     * @param map
     *            订单属性Map
     */
    void updateOrdersStatus(HashMap<String, String> map);
}
