package com.atguigu.service.impl;

import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Headline;
import com.atguigu.service.HeadlineService;
import com.atguigu.mapper.HeadlineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author allen_thinkpad_t14
 * @description 针对表【news_headline】的数据库操作Service实现
 * @createDate 2023-12-14 13:31:23
 */
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
        implements HeadlineService {

    @Autowired
    private HeadlineMapper headlineMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 首页分页查询
     * <p>
     * {
     * "code":"200",
     * "message":"success"
     * "data":{
     * "pageInfo":{
     * "pageData":[
     * {
     * "hid":"1",                     // 新闻id
     * "title":"尚硅谷宣布 ... ...",   // 新闻标题
     * "type":"1",                    // 新闻所属类别编号
     * "pageViews":"40",              // 新闻浏览量
     * "pastHours":"3" ,              // 发布时间已过小时数
     * "publisher":"1"                // 发布用户ID
     * },
     * {
     * "hid":"1",                     // 新闻id
     * "title":"尚硅谷宣布 ... ...",   // 新闻标题
     * "type":"1",                    // 新闻所属类别编号
     * "pageViews":"40",              // 新闻浏览量
     * "pastHours":"3",              // 发布时间已过小时数
     * "publisher":"1"                // 发布用户ID
     * },
     * {
     * "hid":"1",                     // 新闻id
     * "title":"尚硅谷宣布 ... ...",   // 新闻标题
     * "type":"1",                    // 新闻所属类别编号
     * "pageViews":"40",              // 新闻浏览量
     * "pastHours":"3",               // 发布时间已过小时数
     * "publisher":"1"                // 发布用户ID
     * }
     * ],
     * "pageNum":1,    //页码数
     * "pageSize":10,  // 页大小
     * "totalPage":20, // 总页数
     * "totalSize":200 // 总记录数
     * }
     * }
     * }
     *
     * @param portalVo
     * @return
     */
    @Override
    public Result findNewPage(PortalVo portalVo) {
        //分页查询; 分页对象：    List<T> getRecords();  查看响应规则：返回数据没有包装类，则用map返回，最后getRecords得到的是一个List<map>
        IPage<Map> iPage = new Page<>(portalVo.getPageNum(), portalVo.getPageSize());
        /*IPage<Map> iPage1 = */
        headlineMapper.selectMyPage(iPage, portalVo);

        //分页查询完毕，数据和分页信息在ipage中，需要放到一个map中
        HashMap<String, Object> hashMapRecordsAndPageInfo = new HashMap<>();

        List<Map> records = iPage.getRecords();
        long size = iPage.getSize();//页大小
        long total = iPage.getTotal();//总记录数
        long current = iPage.getCurrent();//当前页码数
        long pages = iPage.getPages();//总页数

        hashMapRecordsAndPageInfo.put("pageData", records);
        hashMapRecordsAndPageInfo.put("pageNum", current);
        hashMapRecordsAndPageInfo.put("pageSize", size);
        hashMapRecordsAndPageInfo.put("totalPage", pages);
        hashMapRecordsAndPageInfo.put("totalSize", total);

        //继续按照接口格式再次封装map
        HashMap<String, Map> stringMapHashMap = new HashMap<>();

        stringMapHashMap.put("pageInfo", hashMapRecordsAndPageInfo);

        //返回结果数据+消息
        return Result.ok(stringMapHashMap);

    }

    /**
     * 根据id查询详细
     * <p>
     * 1：修改阅读量 + 1 [有乐观锁，先查询，再修改。]
     * 2：查询详情（多表查询，头条和用户表。mapper方法自定义，返回map）
     * * 详情数据查询
     * * "headline":{
     * * "hid":"1",                     // 新闻id
     * * "title":"马斯克宣布 ... ...",   // 新闻标题
     * * "article":"... ..."            // 新闻正文
     * * "type":"1",                    // 新闻所属类别编号
     * * "typeName":"科技",             // 新闻所属类别
     * * "pageViews":"40",              // 新闻浏览量
     * * "pastHours":"3" ,              // 发布时间已过小时数
     * * "publisher":"1" ,              // 发布用户ID
     * * "author":"张三"                 // 新闻作者
     * * }
     * * 注意: 是多表查询 , 需要更新浏览量+1
     *
     * @param hid
     * @return
     */
    @Override
    public Result showHeadLineDetail(Integer hid) {
        //1：根据id查询
        Map headLineDetail = headlineMapper.selectDetailMap(hid);

        //2：更新浏览量；拼接头条对象，进行数据更新
        Headline headline = new Headline();
        headline.setHid((Integer) headLineDetail.get("hid"));
        headline.setPageViews((Integer) headLineDetail.get("pageViews") + 1);
        headline.setVersion((Integer) headLineDetail.get("version"));
        headlineMapper.updateById(headline);

        Map map = new HashMap();
        map.put("headline", headLineDetail);
        return Result.ok(map);


    }

    /**
     * 发布头条
     * 1：补全数据，根据token获取userid
     * 2：插入记录
     *
     * @param headline
     * @param token
     * @return
     */
    @Override
    public Result publish(Headline headline, String token) {
        //根据token查询用户id
        Long userId = jwtHelper.getUserId(token);
        headline.setPublisher(userId.intValue());
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());
        headline.setPageViews(0);
        //插入一条数据，add数据
        headlineMapper.insert(headline);

        return Result.ok(null);
    }

    @Override
    public Result findHeadlineByHid(Integer hid) {
        //根据头条id查询值
        Headline headline = headlineMapper.selectById(hid);
        Map<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("headline", headline);
        return Result.ok(pageInfoMap);

    }
    /**
     * 修改业务
     * 1.查询version版本
     * 2.补全属性,修改时间 , 版本!
     *
     * @param headline
     * @return
     */
    @Override
    public Result updateHeadLine(Headline headline) {
        //读取版本，实现乐观锁
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();
        headline.setVersion(version);
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);
        return Result.ok(null);
    }
}




