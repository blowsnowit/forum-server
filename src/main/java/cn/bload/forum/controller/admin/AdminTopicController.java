package cn.bload.forum.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.Page;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.entity.query.TopicQuery;
import cn.bload.forum.entity.vo.TopicUpdateVO;
import cn.bload.forum.service.TopicService;
import cn.bload.forum.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/15 14:36
 * @describe 类描述:
 */
@RestController
@RequestMapping("/api/admin/topic")
@Api(value = "/api/admin/topic",tags = "后台话题控制器")
public class AdminTopicController extends BaseController {
    @Autowired
    TopicService topicService;

    @GetMapping("")
    @ApiOperation(value = "",notes = "获取话题列表")
    public ResultBean getTopics(TopicQuery topicQuery){
        Page page = topicQuery.createPage();
        page.setRecords(topicService.getHotTopics(topicQuery));
        return ResultGenerator.getSuccessResult(page);
    }

    //保存话题描述
    @PutMapping("/desc/{topicName}")
    @ApiOperation(value = "",notes = "修改话题描述")
    public ResultBean saveTopicDesc(@ApiParam("话题名称") @PathVariable String topicName,
                                    @RequestBody TopicUpdateVO topicUpdateVO){
        topicService.saveTopicDesc(topicName,topicUpdateVO.getTopicDesc());
        return ResultGenerator.getSuccessResult();
    }
}
