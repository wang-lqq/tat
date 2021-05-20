package com.example.work.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.work.entity.WorkSpotcheckRecord;
import com.example.work.service.WorkSpotcheckRecordService;
import lombok.extern.slf4j.Slf4j;
import com.example.work.param.WorkSpotcheckRecordPageParam;
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
 * @since 2021-05-13
 */
@Slf4j
@RestController
@RequestMapping("/workSpotcheckRecord")
@Module("work")
@Api(value = "API", tags = {""})
public class WorkSpotcheckRecordController extends BaseController {

    @Autowired
    private WorkSpotcheckRecordService workSpotcheckRecordService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addWorkSpotcheckRecord(@Validated(Add.class) @RequestBody WorkSpotcheckRecord workSpotcheckRecord) throws Exception {
        boolean flag = workSpotcheckRecordService.saveWorkSpotcheckRecord(workSpotcheckRecord);
        return ApiResult.result(flag);
    }
    
    /**
     * 批量添加
     */
    @PostMapping("/addList")
    @OperationLog(name = "批量添加", type = OperationLogType.ADD)
    @ApiOperation(value = "批量添加", response = ApiResult.class)
    public ApiResult<Boolean> addListWorkSpotcheckRecord(@Validated(Add.class) @RequestBody JSONObject jsonObject) throws Exception {
        boolean flag = workSpotcheckRecordService.addList(jsonObject);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateWorkSpotcheckRecord(@Validated(Update.class) @RequestBody WorkSpotcheckRecord workSpotcheckRecord) throws Exception {
        boolean flag = workSpotcheckRecordService.updateWorkSpotcheckRecord(workSpotcheckRecord);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteWorkSpotcheckRecord(@PathVariable("id") Long id) throws Exception {
        boolean flag = workSpotcheckRecordService.deleteWorkSpotcheckRecord(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = WorkSpotcheckRecord.class)
    public ApiResult<WorkSpotcheckRecord> getWorkSpotcheckRecord(@PathVariable("id") Long id) throws Exception {
        WorkSpotcheckRecord workSpotcheckRecord = workSpotcheckRecordService.getById(id);
        return ApiResult.ok(workSpotcheckRecord);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = WorkSpotcheckRecord.class)
    public ApiResult<Paging<WorkSpotcheckRecord>> getWorkSpotcheckRecordPageList(@Validated @RequestBody WorkSpotcheckRecordPageParam workSpotcheckRecordPageParam) throws Exception {
        Paging<WorkSpotcheckRecord> paging = workSpotcheckRecordService.getWorkSpotcheckRecordPageList(workSpotcheckRecordPageParam);
        return ApiResult.ok(paging);
    }
}

