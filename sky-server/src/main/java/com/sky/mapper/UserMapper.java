package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    //通过 openid 查询用户
    @Select("select * from user where openid = #{openId}")
    User selectUserByOpenId(String openId);

    //新增用户
    void insertUser(User user);

    //根据id 查询用户信息
    @Select("select * from user where id = #{userId}")
    User getById(Long userId);
}
