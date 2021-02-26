package com.example.work.controller;

import com.example.work.entity.WorkRepairReport;
import com.example.work.service.WorkRepairReportService;
import lombok.extern.slf4j.Slf4j;
import com.example.work.param.WorkRepairReportPageParam;
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
 * 联络-维修单表 控制器
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
@Slf4j
@RestController
@RequestMapping("/workRepairReport")
@Module("work")
@Api(value = "联络-维修单表API", tags = {"联络-维修单表"})
public class WorkRepairReportController extends BaseController {

    @Autowired
    private WorkRepairReportService workRepairReportService;

    /**
     * 添加联络-维修单表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加联络-维修单表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加联络-维修单表", response = ApiResult.class)
    public ApiResult<Boolean> addWorkRepairReport(@Validated(Add.class) @RequestBody WorkRepairReport workRepairReport) throws Exception {
        boolean flag = workRepairReportService.saveWorkRepairReport(workRepairReport);
        return ApiResult.result(flag);
    }

    /**
     * 修改联络-维修单表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改联络-维修单表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改联络-维修单表", response = ApiResult.class)
    public ApiResult<Boolean> updateWorkRepairReport(@Validated(Update.class) @RequestBody WorkRepairReport workRepairReport) throws Exception {
        boolean flag = workRepairReportService.updateWorkRepairReport(workRepairReport);
        return ApiResult.result(flag);
    }

    /**
     * 删除联络-维修单表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除联络-维修单表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除联络-维修单表", response = ApiResult.class)
    public ApiResult<Boolean> deleteWorkRepairReport(@PathVariable("id") Long id) throws Exception {
        boolean flag = workRepairReportService.deleteWorkRepairReport(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取联络-维修单表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "联络-维修单表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "联络-维修单表详情", response = WorkRepairReport.class)
    public ApiResult<WorkRepairReport> getWorkRepairReport(@PathVariable("id") Long id) throws Exception {
        WorkRepairReport workRepairReport = workRepairReportService.getById(id);
        return ApiResult.ok(workRepairReport);
    }

    /**
     * 联络-维修单表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "联络-维修单表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "联络-维修单表分页列表", response = WorkRepairReport.class)
    public ApiResult<Paging<WorkRepairReport>> getWorkRepairReportPageList(@Validated @RequestBody WorkRepairReportPageParam workRepairReportPageParam) throws Exception {
        Paging<WorkRepairReport> paging = workRepairReportService.getWorkRepairReportPageList(workRepairReportPageParam);
        return ApiResult.ok(paging);
    }

}

