package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    //查询购物车
    List<ShoppingCart> selectShoppingCartByUserId(ShoppingCart shoppingCart);

    //修改购物车
    void updateByUserId(ShoppingCart cart);

    //添加购物车
    void insert(ShoppingCart shoppingCart);

    //清空购物车
    void delete(Long userId);

    //删除购物车单个商品
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteDiahOrSetMeal(Long id);
}
