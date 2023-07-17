package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/setmeal")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    //添加套餐
    @PostMapping
    public Result<Integer> insertSetMeal(@RequestBody SetmealDTO setMealDTO){
        setMealService.insertSetMeal(setMealDTO);
        return Result.success();
    }
    //分页查询
    @GetMapping("/page")
    public Result<PageResult> selectSetMeal(SetmealPageQueryDTO setmealPageQueryDTO){
         PageResult pageResult = setMealService.selectSetMeal(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
}
