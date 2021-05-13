package com.example.work.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.work.entity.WorkSpotcheckPlan;
import com.example.work.param.WorkSpotcheckPlanPageParam;
import com.example.work.service.WorkSpotcheckPlanService;

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
 * @since 2021-05-06
 */
@Slf4j
@RestController
@RequestMapping("/workSpotcheckPlan")
@Module("work")
@Api(value = "API", tags = {""})
public class WorkSpotcheckPlanController extends BaseController {

    @Autowired
    private WorkSpotcheckPlanService workSpotcheckPlanService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addWorkSpotcheckPlan(@Validated(Add.class) @RequestBody WorkSpotcheckPlan workSpotcheckPlan) throws Exception {
        boolean flag = workSpotcheckPlanService.saveWorkSpotcheckPlan(workSpotcheckPlan);
        return ApiResult.result(flag);
    }
    
    /**
     * 添加
     */
    @PostMapping("/addList")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addList(@Validated(Add.class) @RequestBody JSONObject jsonObject) throws Exception {
        boolean flag = workSpotcheckPlanService.addList(jsonObject);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateWorkSpotcheckPlan(@Validated(Update.class) @RequestBody WorkSpotcheckPlan workSpotcheckPlan) throws Exception {
        boolean flag = workSpotcheckPlanService.updateWorkSpotcheckPlan(workSpotcheckPlan);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteWorkSpotcheckPlan(@PathVariable("id") Long id) throws Exception {
        boolean flag = workSpotcheckPlanService.deleteWorkSpotcheckPlan(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = WorkSpotcheckPlan.class)
    public ApiResult<WorkSpotcheckPlan> getWorkSpotcheckPlan(@PathVariable("id") Long id) throws Exception {
        WorkSpotcheckPlan workSpotcheckPlan = workSpotcheckPlanService.getById(id);
        return ApiResult.ok(workSpotcheckPlan);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = WorkSpotcheckPlan.class)
    public ApiResult<Paging<WorkSpotcheckPlan>> getWorkSpotcheckPlanPageList(@Validated @RequestBody WorkSpotcheckPlanPageParam workSpotcheckPlanPageParam) throws Exception {
        Paging<WorkSpotcheckPlan> paging = workSpotcheckPlanService.getWorkSpotcheckPlanPageList(workSpotcheckPlanPageParam);
        return ApiResult.ok(paging);
    }
    
    /**
     * 时间列表
     */
    @PostMapping("/getDate")
    @OperationLog(name = "时间列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "时间列表", response = WorkSpotcheckPlan.class)
    public ApiResult<List<JSONObject>> getDate(@Validated @RequestBody JSONObject jsonObject) throws Exception {
        List<JSONObject> list = workSpotcheckPlanService.getDate(jsonObject);
        return ApiResult.ok(list);
    }
}

