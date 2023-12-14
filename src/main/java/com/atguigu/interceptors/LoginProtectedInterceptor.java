package com.atguigu.interceptors;

import com.alibaba.druid.util.StringUtils;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ClassName: LoginProtectedInterceptor
 * PackageName: com.atguigu.interceptors
 * Description:
 *
 * @Author: Hanyu
 * @Date: 23/12/14 - 23:44
 * @Version: v1.0
 */
@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中获取token
        String token = request.getHeader("token");
        //检查是否有效
        boolean expiration = jwtHelper.isExpiration(token);
        //有效，放行
        if(!StringUtils.isEmpty(token) && !expiration){
            return true;
        }
        //无效，拦截；返回504状态和消息
        Result result = Result.build(null, ResultCodeEnum.NOT_LOGIN);
        //自己构造json，返回result
        ObjectMapper objectMapper = new ObjectMapper();
        String resultStr = objectMapper.writeValueAsString(result);
        response.getWriter().print(resultStr);

        return false;
    }
}
