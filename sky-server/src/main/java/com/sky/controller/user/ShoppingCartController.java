package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    //添加购物车
    @PostMapping("add")
    public Result<Integer> add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加购物车: {}",shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);
        return Result.success();
    }

    //查询购物车
    @GetMapping("list")
    public Result<List<ShoppingCart>> selectShoppingCart(){
        List<ShoppingCart> shoppingCartList = shoppingCartService.selectShoppingCart();
        return Result.success(shoppingCartList);
    }

    //清空购物车
    @DeleteMapping("clean")
    public Result<Integer> delete(){
        shoppingCartService.delete();
        return Result.success();
    }

    //修改购物车  减少、删除
    @PostMapping("/sub")
    public Result<Integer> sub(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("修改或删除购物车的一个商品的数量: {}",shoppingCartDTO);
        shoppingCartService.sub(shoppingCartDTO);
        return Result.success();
    }
}
