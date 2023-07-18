package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * 菜品表 分类id
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    //添加菜品
    @AutoFill(OperationType.INSERT)
/*    @Insert("INSERT INTO dish (name,category_id,price,image,description,status,create_time,update_time,create_user,update_user) VALUES " +
            "(#{name}, #{categoryId}, #{price}, #{image}, #{description}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser}")*/
    void addDish(Dish dish);

    //分页查询
    List<DishVO> selectDish(DishPageQueryDTO dishPageQueryDTO);

    //根据id查菜品
    @Select("select * from dish where id = #{id}")
    Dish selectDishById(Long id);

    //批量删除菜品
    //@Delete("delete from dish where id in ids")
    void deleteDish(List<Long> ids);

    //修改菜品
    @AutoFill(OperationType.UPDATE)
    @Update("update dish set name = #{name}, category_id = #{categoryId}, price = #{price}, image = #{image}, description = #{description} where id =#{id}")
    void updateDish(Dish dish);

    //根据菜品分类查询菜品
    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> selectDishByCategoryId(Integer categoryId);
}
