package com.example.sb.controller;

import com.example.sb.entity.SbSoftware;
import com.example.sb.service.SbSoftwareService;
import lombok.extern.slf4j.Slf4j;
import com.example.sb.param.SbSoftwarePageParam;
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
 * @since 2021-04-12
 */
@Slf4j
@RestController
@RequestMapping("/sbSoftware")
@Module("sb")
@Api(value = "API", tags = {""})
public class SbSoftwareController extends BaseController {

    @Autowired
    private SbSoftwareService sbSoftwareService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addSbSoftware(@Validated(Add.class) @RequestBody SbSoftware sbSoftware) throws Exception {
        boolean flag = sbSoftwareService.saveSbSoftware(sbSoftware);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateSbSoftware(@Validated(Update.class) @RequestBody SbSoftware sbSoftware) throws Exception {
        boolean flag = sbSoftwareService.updateSbSoftware(sbSoftware);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteSbSoftware(@PathVariable("id") Long id) throws Exception {
        boolean flag = sbSoftwareService.deleteSbSoftware(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = SbSoftware.class)
    public ApiResult<SbSoftware> getSbSoftware(@PathVariable("id") Long id) throws Exception {
        SbSoftware sbSoftware = sbSoftwareService.getById(id);
        return ApiResult.ok(sbSoftware);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = SbSoftware.class)
    public ApiResult<Paging<SbSoftware>> getSbSoftwarePageList(@Validated @RequestBody SbSoftwarePageParam sbSoftwarePageParam) throws Exception {
        Paging<SbSoftware> paging = sbSoftwareService.getSbSoftwarePageList(sbSoftwarePageParam);
        return ApiResult.ok(paging);
    }

}

