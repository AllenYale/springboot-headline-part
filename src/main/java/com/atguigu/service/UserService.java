package com.atguigu.service;

import com.atguigu.pojo.User;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author allen_thinkpad_t14
* @description 针对表【news_user】的数据库操作Service
* @createDate 2023-12-14 13:31:23
*/
public interface UserService extends IService<User> {

    /**
     * 查询用户登入是否成功方法
     * @param user
     * @return
     */
    Result login(User user);

    /**
     * 获取请求头中token数据，校验是否登入有效
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * 检查账号是否可以注册
     * @param username
     * @return
     */
    Result checkUserName(String username);

    Result regist(User user);
}
