package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface SetMealService {
    PageResult selectSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);

    void insertSetMeal(SetmealDTO setMealDTO);

    void deleteSetMealByIds(List<Long> ids);

    SetmealDTO selectSetMealById(Long id);

    void updateSetMeal(SetmealDTO setmealDTO);

    void updateSetMealByStatus(Integer status, Long id);
}
