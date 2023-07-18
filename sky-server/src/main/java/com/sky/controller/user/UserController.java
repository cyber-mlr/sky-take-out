package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user/user")
public class UserController {

    /**
     * 调用 wx.login() 获取 **临时登录凭证code** ，并回传到开发者服务器。
     * userLoginDTO 中的data即为code
     * 微信登录接口
     * 封装返回用户登录信息
     */
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;



    //登录
    @PostMapping("/login")
    public Result<UserLoginVO> userLogin(@RequestBody UserLoginDTO userLoginDTO){
        //微信登录
        log.info("微信用户登录: {}",userLoginDTO.getCode());
        User user = userService.login(userLoginDTO);
        //获取jwt 令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .token(token)
                .openid(user.getOpenid())
                .build();
        return Result.success(userLoginVO);
    }
}
