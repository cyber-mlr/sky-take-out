package com.sky.controller.admin;

import com.sky.annotation.AutoFill;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 新增员工
     *
     */
    @PostMapping
    public Result<Integer> add(@RequestBody EmployeeDTO employeeDTO){
        employeeService.add(employeeDTO);
        return Result.success();
    }


    /**
     * 分页查询员工
     */

    @GetMapping("/page")
    public Result<PageResult> select(EmployeePageQueryDTO employeePageQueryDTO){
        PageResult pageResult =employeeService.selectEmp(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用员工账号
     * 与修改员工公用一个接口
     */
    @PostMapping("status/{status}")
    public Result<Integer> updateEmployee(@PathVariable Integer status,Long id){
        employeeService.updateEmployee(status,id);
        return Result.success();
    }

    /**
     * 修改员工
     * 1. 根据id查询员工
     * 2. 修改员工
     */

    @GetMapping("{id}")
    public Result<EmployeeDTO> selectEmployeeById(@PathVariable Long id){
        EmployeeDTO employeeDTO = employeeService.selectEmployeeById(id);
        return Result.success(employeeDTO);
    }

    //修改员工
    @PutMapping()
    public Result<Integer> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO){
        employeeService.updateEmployee(employeeDTO);
        return Result.success();
    }
}
