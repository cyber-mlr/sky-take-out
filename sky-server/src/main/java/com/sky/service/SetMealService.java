package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

public interface SetMealService {
    PageResult selectSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);

    void insertSetMeal(SetmealDTO setMealDTO);
}