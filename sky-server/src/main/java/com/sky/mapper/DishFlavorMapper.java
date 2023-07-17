package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    //新增口味
    //@Insert("INSERT INTO ")
    void addDishFlavor(List<DishFlavor> flavors);

    //删除口味
    //@Delete("delete from dish_flavor where dish_id in ids")
    void deleteDishFlavor(List<Long> ids);

    //查询口味
    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> selectDishFlavor(Long id);
}
