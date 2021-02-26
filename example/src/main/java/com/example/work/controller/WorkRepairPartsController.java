package com.example.work.controller;

import com.example.work.entity.WorkRepairParts;
import com.example.work.service.WorkRepairPartsService;
import lombok.extern.slf4j.Slf4j;
import com.example.work.param.WorkRepairPartsPageParam;
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
 * 维修配件表 控制器
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
@Slf4j
@RestController
@RequestMapping("/workRepairParts")
@Module("work")
@Api(value = "维修配件表API", tags = {"维修配件表"})
public class WorkRepairPartsController extends BaseController {

    @Autowired
    private WorkRepairPartsService workRepairPartsService;

    /**
     * 添加维修配件表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加维修配件表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加维修配件表", response = ApiResult.class)
    public ApiResult<Boolean> addWorkRepairParts(@Validated(Add.class) @RequestBody WorkRepairParts workRepairParts) throws Exception {
        boolean flag = workRepairPartsService.saveWorkRepairParts(workRepairParts);
        return ApiResult.result(flag);
    }

    /**
     * 修改维修配件表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改维修配件表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改维修配件表", response = ApiResult.class)
    public ApiResult<Boolean> updateWorkRepairParts(@Validated(Update.class) @RequestBody WorkRepairParts workRepairParts) throws Exception {
        boolean flag = workRepairPartsService.updateWorkRepairParts(workRepairParts);
        return ApiResult.result(flag);
    }

    /**
     * 删除维修配件表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除维修配件表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除维修配件表", response = ApiResult.class)
    public ApiResult<Boolean> deleteWorkRepairParts(@PathVariable("id") Long id) throws Exception {
        boolean flag = workRepairPartsService.deleteWorkRepairParts(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取维修配件表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "维修配件表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "维修配件表详情", response = WorkRepairParts.class)
    public ApiResult<WorkRepairParts> getWorkRepairParts(@PathVariable("id") Long id) throws Exception {
        WorkRepairParts workRepairParts = workRepairPartsService.getById(id);
        return ApiResult.ok(workRepairParts);
    }

    /**
     * 维修配件表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "维修配件表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "维修配件表分页列表", response = WorkRepairParts.class)
    public ApiResult<Paging<WorkRepairParts>> getWorkRepairPartsPageList(@Validated @RequestBody WorkRepairPartsPageParam workRepairPartsPageParam) throws Exception {
        Paging<WorkRepairParts> paging = workRepairPartsService.getWorkRepairPartsPageList(workRepairPartsPageParam);
        return ApiResult.ok(paging);
    }

}

