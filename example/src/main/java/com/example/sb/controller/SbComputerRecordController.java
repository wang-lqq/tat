package com.example.sb.controller;

import com.example.sb.entity.SbComputerRecord;
import com.example.sb.service.SbComputerRecordService;
import lombok.extern.slf4j.Slf4j;
import com.example.sb.param.SbComputerRecordPageParam;
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
 * @since 2021-03-29
 */
@Slf4j
@RestController
@RequestMapping("/sbComputerRecord")
@Module("sb")
@Api(value = "API", tags = {""})
public class SbComputerRecordController extends BaseController {

    @Autowired
    private SbComputerRecordService sbComputerRecordService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addSbComputerRecord(@Validated(Add.class) @RequestBody SbComputerRecord sbComputerRecord) throws Exception {
        boolean flag = sbComputerRecordService.saveSbComputerRecord(sbComputerRecord);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateSbComputerRecord(@Validated(Update.class) @RequestBody SbComputerRecord sbComputerRecord) throws Exception {
        boolean flag = sbComputerRecordService.updateSbComputerRecord(sbComputerRecord);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteSbComputerRecord(@PathVariable("id") Long id) throws Exception {
        boolean flag = sbComputerRecordService.deleteSbComputerRecord(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = SbComputerRecord.class)
    public ApiResult<SbComputerRecord> getSbComputerRecord(@PathVariable("id") Long id) throws Exception {
        SbComputerRecord sbComputerRecord = sbComputerRecordService.getById(id);
        return ApiResult.ok(sbComputerRecord);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = SbComputerRecord.class)
    public ApiResult<Paging<SbComputerRecord>> getSbComputerRecordPageList(@Validated @RequestBody SbComputerRecordPageParam sbComputerRecordPageParam) throws Exception {
        Paging<SbComputerRecord> paging = sbComputerRecordService.getSbComputerRecordPageList(sbComputerRecordPageParam);
        return ApiResult.ok(paging);
    }

}

