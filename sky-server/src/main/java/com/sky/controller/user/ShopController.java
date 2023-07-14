package com.sky.controller.user;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user/shop")
@Slf4j
@RestController("userShopController")
public class ShopController {

    public static final String KEY = "shop_status";
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/status")
    public Result<Integer> getShopStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("查询店铺状态为{}",status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
