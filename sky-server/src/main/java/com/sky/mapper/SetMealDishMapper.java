package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealDishMapper {

    //根据菜品id查询菜品与套餐关联数据
    //@Select("select * from setmeal_dish where dish_id in ids")
    List<SetmealDish> selectDishByDishId(List<Long> ids);
}
