package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatProperties weChatProperties;
    //微信用户登录
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        //调用微信接口获取openid
        String openId = getOpenId(userLoginDTO.getCode());
        //判断微信用户是否登陆成功
        if (openId == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //判断用户是否是新用户
        User user = userMapper.selectUserByOpenId(openId);

        //是新用户  即user = null  自动注册
        if (user == null){
            user = User.builder()
                    .openid(openId)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insertUser(user);
        }

        return user;
    }

    private String getOpenId (String code){
        Map<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString("openid");
    }
}
