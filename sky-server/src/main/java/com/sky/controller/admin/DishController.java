package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品接口")
public class DishController {

    @Autowired
    private DishService dishService;

    //新增菜品
    @PostMapping()
    public Result<String> addDish(@RequestBody DishDTO dishDTO){
        dishService.addDish(dishDTO);
        return Result.success();
    }

    //分页查询菜品
    @GetMapping("/page")
    public Result<PageResult> selectDish(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询{}",dishPageQueryDTO);
        PageResult pageResult = dishService.selectDish(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    //批量删除菜品
    @DeleteMapping
    public Result<String> deleteDish(@RequestParam List<Long> ids){
        dishService.deleteDish(ids);
        return Result.success();
    }

    //修改菜品
    @GetMapping("/{id}")
    public Result<DishVO> selectDishById(@PathVariable Long id){
        DishVO dishVO = dishService.selectDishById(id);
        return Result.success(dishVO);
    }
    @PutMapping()
    public Result<Integer> updateDish(@RequestBody DishDTO dishDTO){
        dishService.updateDish(dishDTO);
        return Result.success();
    }

    //添加菜品
    /**
     * TODO
     *  1. 查询菜品列表
     */
    @GetMapping("/list")
    public Result<List<Dish>> selectDish(Integer categoryId){
        List<Dish> list = dishService.selectDishByCategoryId(categoryId);
        return Result.success(list);
    }
}
