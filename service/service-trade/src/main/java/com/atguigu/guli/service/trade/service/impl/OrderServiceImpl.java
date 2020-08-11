package com.atguigu.guli.service.trade.service.impl;

import com.atguigu.guli.common.base.result.ResultCodeEnum;
import com.atguigu.guli.service.base.dto.CourseDto;
import com.atguigu.guli.service.base.dto.MemberDto;
import com.atguigu.guli.service.base.exce.GuliException;
import com.atguigu.guli.service.trade.entity.OrderEntity;
import com.atguigu.guli.service.trade.entity.PayLogEntity;
import com.atguigu.guli.service.trade.feign.EduCourseService;
import com.atguigu.guli.service.trade.feign.UcenterMemberService;
import com.atguigu.guli.service.trade.mapper.OrderMapper;
import com.atguigu.guli.service.trade.mapper.PayLogMapper;
import com.atguigu.guli.service.trade.service.OrderService;
import com.atguigu.guli.service.trade.util.OrderNoUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-10
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @Autowired
    private PayLogMapper payLogMapper;

    @Override
    public String saveOrder(String courseId, String memberId) {

        // 查询当前用户是否已有当前课程的订单
        LambdaQueryWrapper<OrderEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderEntity::getCourseId, courseId);
        queryWrapper.eq(OrderEntity::getMemberId, memberId);
        OrderEntity orderExist = baseMapper.selectOne(queryWrapper);
        if (orderExist != null) {
            // 如果订单已存在，则直接返回订单id
            return orderExist.getId();
        }

        // 查询课程信息
        CourseDto courseDto = eduCourseService.getCourseDtoById(courseId);
        if (courseDto == null) {
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }

        // 查询用户信息
        MemberDto memberDto = ucenterMemberService.getMemberDtoByMemberId(memberId);
        if (memberDto == null) {
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }

        // 创建订单
        OrderEntity order = new OrderEntity();
        order.setOrderNo(OrderNoUtils.getOrderNo())
                .setCourseId(courseId)
                .setCourseTitle(courseDto.getTitle())
                .setCourseCover(courseDto.getCover())
                .setTeacherName(courseDto.getTeacherName())
                // 分
                .setTotalFee(courseDto.getPrice().multiply(new BigDecimal(100)))
                .setMemberId(memberId)
                .setMobile(memberDto.getMobile())
                .setNickname(memberDto.getNickname())
                // 未支付
                .setStatus(0)
                // 微信支付
                .setPayType(1);
        baseMapper.insert(order);
        return order.getId();
    }

    @Override
    public OrderEntity getByOrderId(String orderId, String memberId) {
        LambdaQueryWrapper<OrderEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderEntity::getId, orderId)
                .eq(OrderEntity::getMemberId, memberId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Boolean isBuyByCourseId(String courseId, String memberId) {
        LambdaQueryWrapper<OrderEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderEntity::getCourseId, courseId)
                .eq(OrderEntity::getMemberId, memberId)
                .eq(OrderEntity::getStatus, 1);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }

    @Override
    public List<OrderEntity> selectByMemberId(String memberId) {
        LambdaQueryWrapper<OrderEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(OrderEntity::getGmtCreate)
                .eq(OrderEntity::getMemberId, memberId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void removeOrder(String orderId, String memberId) {
        LambdaQueryWrapper<OrderEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderEntity::getId, orderId)
                .eq(OrderEntity::getMemberId, memberId);
        this.remove(queryWrapper);
    }

    @Override
    public OrderEntity getOrderByOrderNo(String orderNo) {
        LambdaQueryWrapper<OrderEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderEntity::getOrderNo, orderNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderStatus(Map<String, String> notifyMap) {

        // 更新订单状态
        String outTradeNo = notifyMap.get("out_trade_no");
        OrderEntity order = this.getOrderByOrderNo(outTradeNo);
        // 支付成功
        order.setStatus(1);
        baseMapper.updateById(order);

        // 记录支付日志
        PayLogEntity payLog = new PayLogEntity()
        .setOrderNo(outTradeNo)
        .setPayTime(LocalDateTime.now())
        //支付类型：微信支付
        .setPayType(1)
        .setTotalFee(Long.parseLong(notifyMap.get("total_fee")))
        .setTradeState(notifyMap.get("result_code"))
        .setTransactionId(notifyMap.get("transaction_id"))
        .setAttr(new Gson().toJson(notifyMap));
        payLogMapper.insert(payLog);

        //更新课程销量
        eduCourseService.updateBuyCountById(order.getCourseId());

    }

    @Override
    public boolean queryPayStatus(String orderNo) {
        LambdaQueryWrapper<OrderEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderEntity::getOrderNo, orderNo);
        OrderEntity order = baseMapper.selectOne(queryWrapper);
        return order.getStatus() == 1;
    }
}
