package cn.bload.forum.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.entity.dto.TagDTO;
import cn.bload.forum.service.TagService;
import cn.bload.forum.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/3 14:50
 * @describe 类描述:
 */
@RestController
@RequestMapping("/api/tag")
@Api(value = "/api/tag",tags = "标签控制器")
public class TagController extends BaseController {
    @Autowired
    TagService tagService;

    @GetMapping("/{tagName}")
    @ApiOperation(value = "/{tagName}",notes = "获取指定标签信息")
    public ResultBean getTagInfo(@ApiParam(value = "标签名称") @PathVariable String tagName){
        TagDTO tagDTO = tagService.getTagInfo(tagName);
        if (tagDTO == null){
            return ResultGenerator.getErrorResult("标签不存在");
        }
        return ResultGenerator.getSuccessResult(tagDTO);
    }
}
