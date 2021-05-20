package com.example.work.controller;

import com.example.work.entity.WorkSpotcheckReportform;
import com.example.work.service.WorkSpotcheckReportformService;
import lombok.extern.slf4j.Slf4j;
import com.example.work.param.WorkSpotcheckReportformPageParam;
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
 * @since 2021-05-20
 */
@Slf4j
@RestController
@RequestMapping("/workSpotcheckReportform")
@Module("work")
@Api(value = "API", tags = {""})
public class WorkSpotcheckReportformController extends BaseController {

    @Autowired
    private WorkSpotcheckReportformService workSpotcheckReportformService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addWorkSpotcheckReportform(@Validated(Add.class) @RequestBody WorkSpotcheckReportform workSpotcheckReportform) throws Exception {
        boolean flag = workSpotcheckReportformService.saveWorkSpotcheckReportform(workSpotcheckReportform);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateWorkSpotcheckReportform(@Validated(Update.class) @RequestBody WorkSpotcheckReportform workSpotcheckReportform) throws Exception {
        boolean flag = workSpotcheckReportformService.updateWorkSpotcheckReportform(workSpotcheckReportform);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteWorkSpotcheckReportform(@PathVariable("id") Long id) throws Exception {
        boolean flag = workSpotcheckReportformService.deleteWorkSpotcheckReportform(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = WorkSpotcheckReportform.class)
    public ApiResult<WorkSpotcheckReportform> getWorkSpotcheckReportform(@PathVariable("id") Long id) throws Exception {
        WorkSpotcheckReportform workSpotcheckReportform = workSpotcheckReportformService.getById(id);
        return ApiResult.ok(workSpotcheckReportform);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = WorkSpotcheckReportform.class)
    public ApiResult<Paging<WorkSpotcheckReportform>> getWorkSpotcheckReportformPageList(@Validated @RequestBody WorkSpotcheckReportformPageParam workSpotcheckReportformPageParam) throws Exception {
        Paging<WorkSpotcheckReportform> paging = workSpotcheckReportformService.getWorkSpotcheckReportformPageList(workSpotcheckReportformPageParam);
        return ApiResult.ok(paging);
    }

}

