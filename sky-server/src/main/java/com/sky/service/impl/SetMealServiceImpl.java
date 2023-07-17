package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    //分页查询
    @Override
    public PageResult selectSetMeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        List<SetmealVO> setmeal = setmealMapper.selectSetMeal(setmealPageQueryDTO);
        Page<SetmealVO> page = (Page<SetmealVO>) setmeal;
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    @Transactional
    public void insertSetMeal(SetmealDTO setMealDTO) {
        //添加套餐
        Setmeal setmeal = Setmeal.builder().build();
        BeanUtils.copyProperties(setMealDTO,setmeal);
        setmealMapper.insertSetMeal(setmeal);
        //添加菜品套餐关系
        List<SetmealDish> setmealDishes = setMealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmeal.getId());
        }
        setMealDishMapper.insertBySetMealId(setmealDishes);
    }
}
