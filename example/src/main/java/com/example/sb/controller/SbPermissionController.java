package com.example.sb.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.example.sb.entity.SbPermission;
import com.example.sb.param.SbPermissionPageParam;
import com.example.sb.service.SbPermissionService;
import com.example.sb.vo.SbPermissionTreeVo;

import io.geekidea.springbootplus.framework.common.api.ApiResult;
import io.geekidea.springbootplus.framework.common.controller.BaseController;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.validator.groups.Add;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;
import io.geekidea.springbootplus.framework.log.annotation.Module;
import io.geekidea.springbootplus.framework.log.annotation.OperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 *  控制器
 *
 * @author wanglonglong
 * @since 2021-04-07
 */
@Slf4j
@RestController
@RequestMapping("/sbPermission")
@Module("sb")
@Api(value = "API", tags = {""})
public class SbPermissionController extends BaseController {

    @Autowired
    private SbPermissionService sbPermissionService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<List<SbPermissionTreeVo>> addSbPermission(@Validated(Add.class) @RequestBody SbPermission sbPermission) throws Exception {
    	String title= HtmlUtils.htmlUnescape(sbPermission.getTitle());
    	sbPermission.setTitle(title);
    	sbPermissionService.saveSbPermission(sbPermission);
        List<SbPermissionTreeVo> trees = sbPermissionService.openTree(sbPermission.getId());
        return ApiResult.ok(trees);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateSbPermission(@Validated(Update.class) @RequestBody SbPermission sbPermission) throws Exception {
    	String title= HtmlUtils.htmlUnescape(sbPermission.getTitle());
    	sbPermission.setTitle(title);
    	sbPermission.setUpdateTime(new Date());
    	boolean flag = sbPermissionService.updateSbPermission(sbPermission);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteSbPermission(@PathVariable("id") Long id) throws Exception {
    	// 逻辑删除
    	SbPermission sbPermission = sbPermissionService.getById(id);
    	sbPermission.setUpdateTime(new Date());
    	sbPermission.setStatus(-1);
    	boolean flag = sbPermissionService.updateSbPermission(sbPermission);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */ 
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = SbPermission.class)
    public ApiResult<SbPermission> getSbPermission(@PathVariable("id") Long id) throws Exception {
        SbPermission sbPermission = sbPermissionService.getById(id);
        return ApiResult.ok(sbPermission);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = SbPermission.class)
    public ApiResult<Paging<SbPermission>> getSbPermissionPageList(@Validated @RequestBody SbPermissionPageParam sbPermissionPageParam) throws Exception {
        Paging<SbPermission> paging = sbPermissionService.getSbPermissionPageList(sbPermissionPageParam);
        return ApiResult.ok(paging);
    }

    
    @PostMapping("/tree")
    @OperationLog(name = "收缩树形", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "收缩树形", response = SbPermissionTreeVo.class)
    public ApiResult<List<SbPermissionTreeVo>> tree() throws Exception {
        List<SbPermissionTreeVo> tree = sbPermissionService.tree();
        return ApiResult.ok(tree);
    }
    
    @PostMapping("/openTree")
    @OperationLog(name = "展开树形", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "展开树形", response = SbPermissionTreeVo.class)
    public ApiResult<List<SbPermissionTreeVo>> openTree() throws Exception {
        List<SbPermissionTreeVo> tree = sbPermissionService.openTree(0);
        return ApiResult.ok(tree);
    }
    
    /**
     * 根据id查询树
     */
    @GetMapping("/tree/{id}")
    @OperationLog(name = "树形详情", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "树形详情", response = SbPermissionTreeVo.class)
    public ApiResult<List<SbPermissionTreeVo>> treeById(@PathVariable("id") Integer id) throws Exception {
    	List<SbPermissionTreeVo> tree = sbPermissionService.treeById(id);
        return ApiResult.ok(tree);
    }
}

