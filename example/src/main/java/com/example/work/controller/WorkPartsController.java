package com.example.work.controller;

import com.example.work.entity.WorkParts;
import com.example.work.service.WorkPartsService;
import lombok.extern.slf4j.Slf4j;
import com.example.work.param.WorkPartsPageParam;
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
 * 配件表 控制器
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
@Slf4j
@RestController
@RequestMapping("/workParts")
@Module("work")
@Api(value = "配件表API", tags = {"配件表"})
public class WorkPartsController extends BaseController {

    @Autowired
    private WorkPartsService workPartsService;

    /**
     * 添加配件表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加配件表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加配件表", response = ApiResult.class)
    public ApiResult<Boolean> addWorkParts(@Validated(Add.class) @RequestBody WorkParts workParts) throws Exception {
        boolean flag = workPartsService.saveWorkParts(workParts);
        return ApiResult.result(flag);
    }

    /**
     * 修改配件表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改配件表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改配件表", response = ApiResult.class)
    public ApiResult<Boolean> updateWorkParts(@Validated(Update.class) @RequestBody WorkParts workParts) throws Exception {
        boolean flag = workPartsService.updateWorkParts(workParts);
        return ApiResult.result(flag);
    }

    /**
     * 删除配件表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除配件表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除配件表", response = ApiResult.class)
    public ApiResult<Boolean> deleteWorkParts(@PathVariable("id") Long id) throws Exception {
        boolean flag = workPartsService.deleteWorkParts(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取配件表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "配件表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "配件表详情", response = WorkParts.class)
    public ApiResult<WorkParts> getWorkParts(@PathVariable("id") Long id) throws Exception {
        WorkParts workParts = workPartsService.getById(id);
        return ApiResult.ok(workParts);
    }

    /**
     * 配件表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "配件表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "配件表分页列表", response = WorkParts.class)
    public ApiResult<Paging<WorkParts>> getWorkPartsPageList(@Validated @RequestBody WorkPartsPageParam workPartsPageParam) throws Exception {
        Paging<WorkParts> paging = workPartsService.getWorkPartsPageList(workPartsPageParam);
        return ApiResult.ok(paging);
    }
}

