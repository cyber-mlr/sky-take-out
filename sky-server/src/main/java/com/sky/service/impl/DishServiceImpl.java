package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;

import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Override
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.addDish(dish);

        //根据insert语句  获取dishID
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() != 0) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
            }
        }
        dishFlavorMapper.addDishFlavor(flavors);
    }

    @Override
    public PageResult selectDish(DishPageQueryDTO dishPageQueryDTO) {
        //配置分页参数
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        //调用mapper层方法获取查询对象
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishPageQueryDTO,dish);
        List<DishVO> list = dishMapper.selectDish(dish);
        //封装Page对象
        Page<DishVO> page = (Page<DishVO>) list;
        return new PageResult(page.getTotal(), page.getResult());
    }

    //删除菜品 1. 起售中的菜品不能删除 2. 被套餐关联的菜品不能删除 3. 删除菜品后相关的口味数据也需要删除
    @Override
    @Transactional
    public void deleteDish(List<Long> ids) {
        for (Long id : ids) {
            //判断是否存在还在起售状态的菜品
            Dish dish = dishMapper.selectDishById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }


        //判断是否被套餐关联
        List<SetmealDish> list = setMealDishMapper.selectDishByDishId(ids);
        if (list != null && list.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品
        dishMapper.deleteDish(ids);
        //删除口味
        dishFlavorMapper.deleteDishFlavor(ids);

    }

    @Override
    public DishVO selectDishById(Long id) {
        //根据id 查询菜品信息
        Dish dish = dishMapper.selectDishById(id);
        //查询口味信息
        List<DishFlavor> flavors= dishFlavorMapper.selectDishFlavor(id);
        //封装dishVO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    //修改菜品
    @Override
    @Transactional
    public void updateDish(DishDTO dishDTO) {
        //修改菜品信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.updateDish(dish);
        //修改口味信息
        /**
         * TODO
         *  1. 删除原先口味信息
         *  2. 插入口味信息
         */
        //删除口味
        List<Long> ids = new ArrayList<>();
        ids.add(dishDTO.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDTO.getId());
        }
        dishFlavorMapper.deleteDishFlavor(ids);
        dishFlavorMapper.addDishFlavor(flavors);
    }

    @Override
    public List<Dish> selectDishByCategoryId(Long categoryId) {
        return dishMapper.selectDishByCategoryId(categoryId);
    }

    /**
     * 条件查询菜品和口味
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<DishVO> dishList = dishMapper.selectDish(dish);

        for (DishVO dishVO : dishList) {
            List<DishFlavor> dishFlavors = dishFlavorMapper.selectDishFlavor(dishVO.getId());
            dishVO.setFlavors(dishFlavors);
        }

        return dishList;
    }
}
