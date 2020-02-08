package ${package.Controller};


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>

import com.bl.template.entity.Page;
import ${package.Entity}.${table.entityName};
import ${package.Service}.${table.serviceName};


<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import java.util.List;
import com.bl.template.entity.ResultBean;
import com.bl.template.util.ResultGenerator;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if swagger2>
@Api(tags = "${table.comment!table.entityName}控制器")
</#if>
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Resource(name = "${table.serviceName?uncapFirst}")
    ${table.serviceName} ${table.serviceName?uncapFirst};

    @GetMapping("/list")
    <#if swagger2>
    @ApiOperation(value = "获取全部${table.entityName?uncapFirst}列表")
    </#if>
    public ResultBean list(){
       List<${table.entityName}> list = ${table.serviceName?uncapFirst}.list();
       return ResultGenerator.getSuccessResult("获取成功",list);
    }

    @GetMapping("/listPage")
    <#if swagger2>
    @ApiOperation(value = "分页获取全部${table.entityName?uncapFirst}列表")
    </#if>
    public ResultBean listPage(){
        Page<${table.entityName}> page = getPagingData();
        IPage<${table.entityName}> list = ${table.serviceName?uncapFirst}.page(page);
        return ResultGenerator.getSuccessResult("获取成功",list);
    }

    @PostMapping("/add")
    <#if swagger2>
    @ApiOperation(value = "添加${table.entityName?uncapFirst}")
    </#if>
    public ResultBean add(${table.entityName} ${table.entityName?uncapFirst}){
        if (!${table.serviceName?uncapFirst}.save(${table.entityName?uncapFirst})) {
            return ResultGenerator.getErrorResult("添加失败");
        }
        return ResultGenerator.getSuccessResult("添加成功");
    }

    @PostMapping("/update")
    <#if swagger2>
    @ApiOperation(value = "更新${table.entityName?uncapFirst}")
    </#if>
    public ResultBean update(${table.entityName} ${table.entityName?uncapFirst}){
        if (!${table.serviceName?uncapFirst}.updateById(${table.entityName?uncapFirst})) {
            return ResultGenerator.getErrorResult("更新失败");
        }
        return ResultGenerator.getSuccessResult("更新成功");
    }

    @PostMapping("/del")
    <#if swagger2>
    @ApiOperation(value = "删除${table.entityName?uncapFirst}")
    </#if>
    public ResultBean del(Integer ${table.fields[0].propertyName}){
       if (!${table.serviceName?uncapFirst}.removeById(${table.fields[0].propertyName})){
           return ResultGenerator.getSuccessResult("删除失败");
       }
       return ResultGenerator.getSuccessResult("删除成功");
    }

    @GetMapping("/get")
    <#if swagger2>
    @ApiOperation(value = "获取单个${table.entityName?uncapFirst}")
    </#if>
    public ResultBean get(Integer ${table.fields[0].propertyName}){
        ${table.entityName} ${table.entityName?uncapFirst} = ${table.serviceName?uncapFirst}.getById(${table.fields[0].propertyName});
        return ResultGenerator.getSuccessResult("获取成功",${table.entityName?uncapFirst});
    }
}
</#if>
