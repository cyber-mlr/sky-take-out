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
    private Result<Integer> add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加购物车: {}",shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("list")
    private Result<List<ShoppingCart>> selectShoppingCart(){
        List<ShoppingCart> shoppingCartList = shoppingCartService.selectShoppingCart();
        return Result.success(shoppingCartList);
    }
}
