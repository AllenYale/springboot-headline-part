package com.atguigu.service;

import com.atguigu.pojo.Type;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author allen_thinkpad_t14
* @description 针对表【news_type】的数据库操作Service
* @createDate 2023-12-14 13:31:23
*/
public interface TypeService extends IService<Type> {

    /**
     * 查询全部类别信息
     * @return
     */
    Result findAllTypes();
}
