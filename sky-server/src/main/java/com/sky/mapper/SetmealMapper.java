package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
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
     * 菜品表 分类id
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

    //根据ids查询套餐售卖状态
    List<Setmeal> selectSetMealByIds(List<Long> ids);

    //根据ids删除套餐
    void deleteSetMealByIds(List<Long> ids);

    //根据 id 查询套餐
    @Select("select * from setmeal where id = #{id}")
    Setmeal selectSetMealById(Long id);

    //修改套餐
    @AutoFill(OperationType.UPDATE)
    void updateSetMeal(Setmeal setmeal);

    //查询套餐内菜品的售卖状态
    //select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{id}
    @Select("select d.status from dish as d join (select dish_id from setmeal_dish as sd join setmeal as s where sd.setmeal_id = #{id}) as a where a.dish_id = d.id")
    List<Dish> selectSetMealDishStatus(Long id);
}
