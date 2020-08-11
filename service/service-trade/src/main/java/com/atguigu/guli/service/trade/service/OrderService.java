package com.atguigu.guli.service.trade.service;

import com.atguigu.guli.service.trade.entity.OrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-10
 */
public interface OrderService extends IService<OrderEntity> {

    String saveOrder(String courseId, String memberId);

    OrderEntity getByOrderId(String orderId, String memberId);

    Boolean isBuyByCourseId(String courseId, String memberId);

    List<OrderEntity> selectByMemberId(String memberId);

    void removeOrder(String orderId, String memberId);

    OrderEntity getOrderByOrderNo(String orderNo);

    void updateOrderStatus(Map<String, String> notifyMap);

    boolean queryPayStatus(String orderNo);
}
