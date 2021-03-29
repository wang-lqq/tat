package com.example.sb.controller;

import com.example.sb.entity.SbComputer;
import com.example.sb.service.SbComputerService;
import lombok.extern.slf4j.Slf4j;
import com.example.sb.param.SbComputerPageParam;
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
@RequestMapping("/sbComputer")
@Module("sb")
@Api(value = "API", tags = {""})
public class SbComputerController extends BaseController {

    @Autowired
    private SbComputerService sbComputerService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addSbComputer(@Validated(Add.class) @RequestBody SbComputer sbComputer) throws Exception {
        boolean flag = sbComputerService.saveSbComputer(sbComputer);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateSbComputer(@Validated(Update.class) @RequestBody SbComputer sbComputer) throws Exception {
        boolean flag = sbComputerService.updateSbComputer(sbComputer);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteSbComputer(@PathVariable("id") Long id) throws Exception {
        boolean flag = sbComputerService.deleteSbComputer(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = SbComputer.class)
    public ApiResult<SbComputer> getSbComputer(@PathVariable("id") Long id) throws Exception {
        SbComputer sbComputer = sbComputerService.getById(id);
        return ApiResult.ok(sbComputer);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = SbComputer.class)
    public ApiResult<Paging<SbComputer>> getSbComputerPageList(@Validated @RequestBody SbComputerPageParam sbComputerPageParam) throws Exception {
        Paging<SbComputer> paging = sbComputerService.getSbComputerPageList(sbComputerPageParam);
        return ApiResult.ok(paging);
    }

}

