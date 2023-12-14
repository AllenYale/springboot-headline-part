package com.atguigu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.MD5Util;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author allen_thinkpad_t14
 * @description 针对表【news_user】的数据库操作Service实现
 * @createDate 2023-12-14 13:31:23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 查询用户是否登入成功
     * <p>
     * 1：获取参数username、pwd
     * 2：查询是否有username
     * 否，返回result
     * 3：查询pwd是否正确
     * 否：返回result
     * 4：创建token，result登入成功
     *
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {
        //TODO如果前端发过来都是null，user是否null？
        // user不会是null，所有属性值是null；不会爆NPE

        //根据账号查询
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(userLambdaQueryWrapper);

        //db查不出来返回null
        if (loginUser == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        if (!StringUtils.isEmpty(user.getUserPwd()) && MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())) {
            //账号密码正确，创建token返回
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            HashMap<String, String> hashMap = new HashMap();
            hashMap.put("token", token);
            return Result.ok(hashMap);
        }

        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 获取请求头中token数据，校验是否登入有效
     * 1：获取token查看是否过期
     * 过期，响应消息
     * 2：获取userid，查询user
     * 3：放入响应
     *
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.NOT_LOGIN);
        }

        Long userId = jwtHelper.getUserId(token);
        User user = userMapper.selectById(userId);
        user.setUserPwd("");
        Map map = new HashMap();
        map.put("loginUser", user);
        return Result.ok(map);
    }

    /**
     * 检查账号是否可以注册
     *
     * @param username 账号信息
     * @return
     */
    @Override
    public Result checkUserName(String username) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);

        if (user != null) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }

        return Result.ok(null);
    }

    @Override
    public Result regist(User user) {
        //查询用户名是否备注册
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        Long count = userMapper.selectCount(queryWrapper);

        if (count > 0){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        //用户密码加密
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        int rows = userMapper.insert(user);
        System.out.println("rows = " + rows);
        return Result.ok(null);
    }
}




