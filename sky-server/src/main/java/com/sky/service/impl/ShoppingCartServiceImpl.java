package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;


    @Override
    @Transactional
    public void add(ShoppingCartDTO shoppingCartDTO) {
        /*
         * TODO
         *  业务逻辑
         *  接收数据后查询该用户的购物车中是否有该商品
         *   1. 有  商品数量加一
         *   2. 没有  购物车中插入新数据
         *      2.1 判断传入的数据是单品还是套餐
         *      2.2 封装数据
         *   3. 调用 Mapper 添加购物车
         */
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(BaseContext.getCurrentId())
                .build();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

        //查询购物车
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectShoppingCartByUserId(shoppingCart);

        //判断是否存在该商品
        if (shoppingCartList != null && shoppingCartList.size() == 1) {
            //存在  数量+1  更新
            ShoppingCart cart = shoppingCartList.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateByUserId(cart);
            return;
        }
        //不存在  判断是单品还是套餐
        Long dishId = shoppingCart.getDishId();
        if (dishId != null) {
            //是单品 封装单品数据
            Dish dish = dishMapper.selectDishById(dishId);
            shoppingCart.setName(dish.getName());
            shoppingCart.setAmount(dish.getPrice());
            shoppingCart.setImage(dish.getImage());
        } else {
            //是套餐 封装套餐信息
            Setmeal setmeal = setmealMapper.selectSetMealById(shoppingCart.getSetmealId());
            shoppingCart.setName(setmeal.getName());
            shoppingCart.setAmount(setmeal.getPrice());
            shoppingCart.setImage(setmeal.getImage());
        }
        //添加到购物车
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.insert(shoppingCart);
    }

    //查看购物车
    @Override
    public List<ShoppingCart> selectShoppingCart() {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(BaseContext.getCurrentId())
                .build();
        return shoppingCartMapper.selectShoppingCartByUserId(shoppingCart);
    }

    //清空购物车
    @Override
    public void delete() {
        shoppingCartMapper.delete(BaseContext.getCurrentId());

    }

    //修改购物车物品份数
    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        //判断商品的份数  大于1  修改  等于1  删除
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(BaseContext.getCurrentId())
                .build();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

        //查询购物车
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectShoppingCartByUserId(shoppingCart);
        assert shoppingCartList != null;
        ShoppingCart cart = shoppingCartList.get(0);
        if (cart.getNumber() == 1) {
            //删除
            shoppingCartMapper.deleteDiahOrSetMeal(cart.getId());
        } else {
            //修改
            cart.setNumber(cart.getNumber() - 1);
            shoppingCartMapper.updateByUserId(cart);
        }
    }
}
