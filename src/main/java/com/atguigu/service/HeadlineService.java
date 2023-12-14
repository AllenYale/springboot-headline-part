package com.atguigu.service;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author allen_thinkpad_t14
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2023-12-14 13:31:23
*/
public interface HeadlineService extends IService<Headline> {

    /**
     * 首页分页查询
     * @param portalVo
     * @return
     */
    Result findNewPage(PortalVo portalVo);

    /**
     * 根据头条id查询详细头条信息
     * @param hid
     * @return
     */
    Result showHeadLineDetail(Integer hid);

    /**
     * 发布头条的方法
     * @param headline
     * @return
     */
    Result publish(Headline headline, String token);

    /**
     * 根据id查询详情
     * @param hid
     * @return
     */
    Result findHeadlineByHid(Integer hid);

    /**
     * 修改业务
     * 1.查询version版本
     * 2.补全属性,修改时间 , 版本!
     *
     * @param headline
     * @return
     */
    Result updateHeadLine(Headline headline);
}
