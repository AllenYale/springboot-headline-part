package com.atguigu.pojo.vo;

import lombok.Data;

/**
 * 封装请求参数
 * url地址：portal/findNewsPage
 * {
 *     "keyWords":"马斯克", // 搜索标题关键字
 *     "type":0,           // 新闻类型
 *     "pageNum":1,        // 页码数
 *     "pageSize":10     // 页大小
 * }
 */
@Data
public class PortalVo {
    
    private String keyWords;
    private Integer type;
    private Integer pageNum = 1;
    private Integer pageSize =10;
}