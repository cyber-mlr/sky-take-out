package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdersDetailMapper {
    //新增订单时  新增订单详情
    void insertOrderDetailByOrdersId(List<OrderDetail> orderDetails);
}
