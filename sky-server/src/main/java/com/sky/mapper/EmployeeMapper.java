package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 添加员工
     */
    @Insert("insert into employee(name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user) " +
            "values(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void add(Employee employee);

    //分页查询
    Page<Employee> selectEmp(EmployeePageQueryDTO employeePageQueryDTO);

    //修改员工
    //@Update("update employee set name = #{name}, username = #{username}, password = #{password}, phone = #{phone}, sex = #{sex}, id_number = #{idNumber}, status = #{status}, create_time = #{createTime}, update_time = #{updateTime}, create_user = #{createUser}, update_user = #{updateUser} where id = #{id}")
    void updateEmployee(Employee employee);

    //根据id查询员工
    @Select("select * from employee where id = #{id}")
    Employee selectEmployeeById(Long id);
}
