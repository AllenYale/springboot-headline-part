package com.atguigu;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * ClassName: Main
 * PackageName: com.atguigu
 * Description:
 *
 * @Author: Hanyu
 * @Date: 23/12/14 - 13:01
 * @Version: v1.0
 */
@SpringBootApplication
@MapperScan("com.atguigu.mapper")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    //配置Mybatis-plus插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());//分页
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());//乐观锁
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());//防止全局修改删除
        return mybatisPlusInterceptor;
    }
}
