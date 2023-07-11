package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void add(EmployeeDTO employeeDTO);

    PageResult selectEmp(EmployeePageQueryDTO employeePageQueryDTO);

    void updateEmployee(Integer status,Long id);

    EmployeeDTO selectEmployeeById(Long id);

    void updateEmployee(EmployeeDTO employeeDTO);
}
