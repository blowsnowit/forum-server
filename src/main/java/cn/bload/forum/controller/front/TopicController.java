package cn.bload.forum.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import cn.bload.forum.base.BaseController;
import cn.bload.forum.base.Page;
import cn.bload.forum.base.ResultBean;
import cn.bload.forum.entity.dto.TopicDTO;
import cn.bload.forum.entity.query.TopicQuery;
import cn.bload.forum.service.TopicService;
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
@RequestMapping("/api/topic")
@Api(value = "/api/topic",tags = "话题控制器")
public class TopicController extends BaseController {
    @Autowired
    TopicService topicService;

    @GetMapping("/{topicName}")
    @ApiOperation(value = "",notes = "/获取指定话题信息")
    public ResultBean getTopicInfo(@ApiParam(value = "话题名称") @PathVariable String topicName){
        TopicDTO topicDTO = topicService.getTopicInfo(topicName);
        if (topicDTO == null){
            return ResultGenerator.getErrorResult("话题不存在");
        }
        return ResultGenerator.getSuccessResult(topicDTO);
    }

    //获取热门话题列表
    @GetMapping("/hot")
    @ApiOperation(value = "",notes = "/获取热门话题列表")
    public ResultBean getHotTopics(TopicQuery topicQuery){
        Page page = topicQuery.createPage();
        List<TopicDTO> topicDTOList = topicService.getHotTopics(topicQuery);
        page.setRecords(topicDTOList);
        return ResultGenerator.getSuccessResult(page);
    }
}
