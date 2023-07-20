package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrdersDetailMapper {
    //新增订单时  新增订单详情
    void insertOrderDetailByOrdersId(List<OrderDetail> orderDetails);

    //查询订单详情
    @Select("select * from order_detail where order_id = #{orderId}")
    List<OrderDetail> selectByNumberAndUserId(Long orderId);
}
