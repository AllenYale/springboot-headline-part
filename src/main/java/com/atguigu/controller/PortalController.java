package com.atguigu.controller;

import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.service.HeadlineService;
import com.atguigu.service.TypeService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: PortalController
 * PackageName: com.atguigu.controller
 * Description:
 *
 * @Author: Hanyu
 * @Date: 23/12/14 - 16:39
 * @Version: v1.0
 */
@RestController
@RequestMapping("portal")
@CrossOrigin
public class PortalController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private HeadlineService headlineService;

    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        return typeService.findAllTypes();
    }
    /**
     * 首页分页查询
     * @return
     */
    @PostMapping("findNewsPage")
    public Result findNewPage(@RequestBody PortalVo portalVo){
        Result result = headlineService.findNewPage(portalVo);
        return result;
    }

    @PostMapping("showHeadlineDetail")
    public Result showHeadLineDetail(Integer hid){
        return headlineService.showHeadLineDetail(hid);
    }
}
