package com.atguigu.mapper;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.PortalVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author allen_thinkpad_t14
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2023-12-14 13:31:23
* @Entity com.atguigu.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    /**
     * 分页条件查询首页数据
     * @param iPage
     * @param portalVo
     * @return
     */
    IPage<Map> selectMyPage(IPage<Map> iPage,@Param("portalVo") PortalVo portalVo);

    /**
     * 根据头条id查询头条&aythor数据，返回自定义map
     * @param hid
     * @return
     */
    Map selectDetailMap(Integer hid);
}




