package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
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

import java.util.ArrayList;
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
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        List<SetmealVO> setmeal = setmealMapper.selectSetMeal(setmealPageQueryDTO);
        Page<SetmealVO> page = (Page<SetmealVO>) setmeal;
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void insertSetMeal(SetmealDTO setMealDTO) {
        //添加套餐
        Setmeal setmeal = Setmeal.builder().build();
        BeanUtils.copyProperties(setMealDTO, setmeal);
        setmealMapper.insertSetMeal(setmeal);
        //添加菜品套餐关系
        List<SetmealDish> setmealDishes = setMealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmeal.getId());
        }
        setMealDishMapper.insertBySetMealId(setmealDishes);
    }

    @Override
    @Transactional
    public void deleteSetMealByIds(List<Long> ids) {
        //判断套餐是否启用
        //根据id查询套餐
        List<Setmeal> list = setmealMapper.selectSetMealByIds(ids);
        //否
        if (list != null && list.size() > 0) {
            for (Setmeal setmeal : list) {
                if (setmeal.getStatus() == StatusConstant.ENABLE) {
                    throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
                }
            }
            //是  删除套餐  删除套餐菜品关系
            setmealMapper.deleteSetMealByIds(ids);
            setMealDishMapper.deleteSetMealDishByIds(ids);
        }
    }

    //修改套餐回显
    @Override
    public SetmealDTO selectSetMealById(Long id) {
        Setmeal setMeals = setmealMapper.selectSetMealById(id);
        List<SetmealDish> setMealDishes = setMealDishMapper.selectDishBySetMealId(id);
        //封装SetMealDTO
        SetmealDTO setmealDTO = new SetmealDTO();
        BeanUtils.copyProperties(setMeals, setmealDTO);
        setmealDTO.setSetmealDishes(setMealDishes);
        return setmealDTO;
    }

    //修改套餐
    @Override
    @Transactional
    public void updateSetMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.updateSetMeal(setmeal);
        //修改setmeal_dish表 1. 删除原套餐菜品 2.插入新套餐菜品
        List<Long> ids = new ArrayList<>();
        Long id = setmealDTO.getId();
        ids.add(id);
        setMealDishMapper.deleteSetMealDishByIds(ids);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDTO.getId());
        }
        setMealDishMapper.insertBySetMealId(setmealDishes);
    }

    @Override
    public void updateSetMealByStatus(Integer status, Long id) {
        //停售 直接修改
        Setmeal setmeal = Setmeal
                .builder()
                .status(status)
                .id(id)
                .build();
        if (status == StatusConstant.DISABLE){
            setmealMapper.updateSetMeal(setmeal);
            return;
        }
        //套餐内有停售菜品不能起售
        //查询套餐内菜品信息
        List<Dish> dishes = setmealMapper.selectSetMealDishStatus(id);
        if (dishes != null && dishes.size() > 0){
            for (Dish dish : dishes) {
                //判断菜品是否全部起售
                if (dish.getStatus() == StatusConstant.DISABLE){
                    throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
            //不报错  全部起售
            //修改套餐状态
            setmealMapper.updateSetMeal(setmeal);
        }
    }
}
