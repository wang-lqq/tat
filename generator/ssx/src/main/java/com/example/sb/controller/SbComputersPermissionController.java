package com.example.sb.controller;

import com.example.sb.entity.SbComputersPermission;
import com.example.sb.service.SbComputersPermissionService;
import lombok.extern.slf4j.Slf4j;
import com.example.sb.param.SbComputersPermissionPageParam;
import io.geekidea.springbootplus.framework.common.controller.BaseController;
import io.geekidea.springbootplus.framework.common.api.ApiResult;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.common.param.IdParam;
import io.geekidea.springbootplus.framework.log.annotation.Module;
import io.geekidea.springbootplus.framework.log.annotation.OperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.geekidea.springbootplus.framework.core.validator.groups.Add;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  控制器
 *
 * @author wanglonglong
 * @since 2021-04-08
 */
@Slf4j
@RestController
@RequestMapping("/sbComputersPermission")
@Module("sb")
@Api(value = "API", tags = {""})
public class SbComputersPermissionController extends BaseController {

    @Autowired
    private SbComputersPermissionService sbComputersPermissionService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addSbComputersPermission(@Validated(Add.class) @RequestBody SbComputersPermission sbComputersPermission) throws Exception {
        boolean flag = sbComputersPermissionService.saveSbComputersPermission(sbComputersPermission);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateSbComputersPermission(@Validated(Update.class) @RequestBody SbComputersPermission sbComputersPermission) throws Exception {
        boolean flag = sbComputersPermissionService.updateSbComputersPermission(sbComputersPermission);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteSbComputersPermission(@PathVariable("id") Long id) throws Exception {
        boolean flag = sbComputersPermissionService.deleteSbComputersPermission(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = SbComputersPermission.class)
    public ApiResult<SbComputersPermission> getSbComputersPermission(@PathVariable("id") Long id) throws Exception {
        SbComputersPermission sbComputersPermission = sbComputersPermissionService.getById(id);
        return ApiResult.ok(sbComputersPermission);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = SbComputersPermission.class)
    public ApiResult<Paging<SbComputersPermission>> getSbComputersPermissionPageList(@Validated @RequestBody SbComputersPermissionPageParam sbComputersPermissionPageParam) throws Exception {
        Paging<SbComputersPermission> paging = sbComputersPermissionService.getSbComputersPermissionPageList(sbComputersPermissionPageParam);
        return ApiResult.ok(paging);
    }

}

