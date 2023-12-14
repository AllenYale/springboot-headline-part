package com.atguigu.test;

import com.atguigu.utils.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ClassName: JWTTest
 * PackageName: com.atguigu.test
 * Description:
 *
 * @Author: Hanyu
 * @Date: 23/12/14 - 15:05
 * @Version: v1.0
 */
@SpringBootTest
public class JWTTest {
    @Autowired
    private JwtHelper jwtHelper;
    
    @Test
    public void test(){
        System.out.println("-------------test start---------------");
        String allen_user_id = jwtHelper.createToken(123456L);
        System.out.println("allen_user_id = " + allen_user_id);
        Long userId = jwtHelper.getUserId(allen_user_id);
        System.out.println("userId = " + userId);
        System.out.println("-------------test end---------------");
    }

    @Test
    public void test1(){
        //生成 传入用户标识
        String token = jwtHelper.createToken(1L);
        System.out.println("token = " + token);

        //解析用户标识
        int userId = jwtHelper.getUserId(token+" ").intValue();
        System.out.println("userId = " + userId);

        //校验是否到期! false 未到期 true到期
        boolean expiration = jwtHelper.isExpiration(token);
        System.out.println("expiration = " + expiration);
    }

}
