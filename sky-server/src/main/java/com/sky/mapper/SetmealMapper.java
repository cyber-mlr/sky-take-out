package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    //添加套餐
    @AutoFill(OperationType.INSERT)
    /*@Insert("insert into setmeal(name,category_id,price,description,image,create_time,update_time,create_user,update_user) " +
            "values(#{name}, #{categoryId}, #{price}, #{description}, #{image}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")*/
    void insertSetMeal(Setmeal setmeal);

    //分页查询
    List<SetmealVO> selectSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);

}
