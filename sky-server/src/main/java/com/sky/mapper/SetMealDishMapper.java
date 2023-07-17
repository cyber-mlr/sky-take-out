package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealDishMapper {

    //根据菜品id查询 菜品与套餐关联数据
    //@Select("select * from setmeal_dish where dish_id in ids")
    List<SetmealDish> selectDishByDishId(List<Long> ids);

    void insertBySetMealId(List<SetmealDish> setmealDishes);

    //根据套餐ids删除菜品与套餐关联数据
    void deleteSetMealDishByIds(List<Long> ids);

    //根据套餐 id 查询对应的菜品
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> selectDishBySetMealId(Long id);
}
