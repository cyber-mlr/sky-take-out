package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;

import java.util.List;

public interface SetMealService {
    PageResult selectSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);

    void insertSetMeal(SetmealDTO setMealDTO);

    void deleteSetMealByIds(List<Long> ids);

    SetmealDTO selectSetMealById(Long id);

    void updateSetMeal(SetmealDTO setmealDTO);

    void updateSetMealByStatus(Integer status, Long id);

    /**
     * 条件查询
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     */
    List<DishItemVO> getDishItemById(Long id);
}
