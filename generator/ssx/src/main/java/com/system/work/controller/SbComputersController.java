package com.system.work.controller;

import com.system.work.entity.SbComputers;
import com.system.work.service.SbComputersService;
import lombok.extern.slf4j.Slf4j;
import com.system.work.param.SbComputersPageParam;
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
 * @since 2021-04-07
 */
@Slf4j
@RestController
@RequestMapping("/sbComputers")
@Module("work")
@Api(value = "API", tags = {""})
public class SbComputersController extends BaseController {

    @Autowired
    private SbComputersService sbComputersService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addSbComputers(@Validated(Add.class) @RequestBody SbComputers sbComputers) throws Exception {
        boolean flag = sbComputersService.saveSbComputers(sbComputers);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateSbComputers(@Validated(Update.class) @RequestBody SbComputers sbComputers) throws Exception {
        boolean flag = sbComputersService.updateSbComputers(sbComputers);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteSbComputers(@PathVariable("id") Long id) throws Exception {
        boolean flag = sbComputersService.deleteSbComputers(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = SbComputers.class)
    public ApiResult<SbComputers> getSbComputers(@PathVariable("id") Long id) throws Exception {
        SbComputers sbComputers = sbComputersService.getById(id);
        return ApiResult.ok(sbComputers);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = SbComputers.class)
    public ApiResult<Paging<SbComputers>> getSbComputersPageList(@Validated @RequestBody SbComputersPageParam sbComputersPageParam) throws Exception {
        Paging<SbComputers> paging = sbComputersService.getSbComputersPageList(sbComputersPageParam);
        return ApiResult.ok(paging);
    }

}

