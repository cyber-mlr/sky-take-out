package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    //删除套餐
    @DeleteMapping
    public Result<Integer> deleteSetMealByIds(@RequestParam List<Long> ids){
        setMealService.deleteSetMealByIds(ids);
        return Result.success();
    }
    //修改套餐
    /**
     * 1. 回显数据
     * 2. 修改套餐
     */
    @GetMapping("/{id}")
    public Result<SetmealDTO> selectSetMealById(@PathVariable Long id){
        SetmealDTO setmealDTO = setMealService.selectSetMealById(id);
        return Result.success(setmealDTO);
    }
    @PutMapping
    public Result<Integer> updateSetMeal(@RequestBody SetmealDTO setmealDTO){
        setMealService.updateSetMeal(setmealDTO);
        return Result.success();
    }

    //修改套餐起售状态
    @PostMapping("/status/{status}")
    public Result<Integer> updateSetMealByStatus(@PathVariable Integer status,Long id){
        setMealService.updateSetMealByStatus(status,id);
        return Result.success();
    }
}
