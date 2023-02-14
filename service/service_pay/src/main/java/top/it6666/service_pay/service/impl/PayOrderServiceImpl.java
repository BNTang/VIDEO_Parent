package top.it6666.service_pay.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import top.it6666.common_utils.entity.ContentWebVO;
import top.it6666.common_utils.entity.User;
import top.it6666.common_utils.utils.UUIDUtil;
import top.it6666.service_pay.client.ContentClient;
import top.it6666.service_pay.client.UserClient;
import top.it6666.service_pay.entity.PayOrder;
import top.it6666.service_pay.mapper.PayOrderMapper;
import top.it6666.service_pay.service.PayOrderService;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author BNTang
 * @since 2021-06-05
 */
@Service
@RequiredArgsConstructor
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements PayOrderService {
    private final UserClient userClient;
    private final ContentClient contentClient;

    @Override
    public String saveOrder(String contentId, String userId) {
        // 通过远程调用根据用户id获取用户信息
        User userInfo = userClient.getUserInfoOrderById(userId);

        // 通过远程调用根据作品id获取作品信息
        ContentWebVO contentInfo = contentClient.getContentInfoOrder(contentId);

        // 创建Order对象，向order对象里面设置需要的数据
        PayOrder order = new PayOrder();

        // 订单号
        order.setOrderNo(UUIDUtil.getRandomNumber(16));

        // 课程id
        order.setContentId(contentId);
        order.setContentTitle(contentInfo.getTitle());
        order.setContentCover(contentInfo.getCover());
        order.setAuthorName(contentInfo.getAuthorName());
        order.setTotalFee(contentInfo.getPrice());
        order.setUserId(userId);
        order.setMobile(userInfo.getMobile());
        order.setNickname(userInfo.getNickname());

        // 订单状态（0：未支付 1：已支付）
        order.setStatus(0);

        // 支付类型 ，支付宝2
        order.setPayType(2);
        baseMapper.insert(order);

        // 返回订单号
        return order.getOrderNo();
    }

    @Override
    public PayOrder getOrderByOrderNo(String orderNo) {
        QueryWrapper<PayOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        return this.getOne(queryWrapper);
    }
}