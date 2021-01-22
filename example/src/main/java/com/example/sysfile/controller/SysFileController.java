package com.example.sysfile.controller;

import com.example.sysfile.entity.SysFile;
import com.example.sysfile.service.SysFileService;
import lombok.extern.slf4j.Slf4j;
import com.example.sysfile.param.SysFilePageParam;
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
 * @since 2021-01-20
 */
@Slf4j
@RestController
@RequestMapping("/sysFile")
@Module("sysfile")
@Api(value = "API", tags = {""})
public class SysFileController extends BaseController {

    @Autowired
    private SysFileService sysFileService;

    /**
     * 添加
     */
    @PostMapping("/insert")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addSysFile(@Validated(Add.class) @RequestBody SysFile sysFile) throws Exception {
        boolean flag = sysFileService.saveSysFile(sysFile);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateSysFile(@Validated(Update.class) @RequestBody SysFile sysFile) throws Exception {
        boolean flag = sysFileService.updateSysFile(sysFile);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysFile(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysFileService.deleteSysFile(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = SysFile.class)
    public ApiResult<SysFile> getSysFile(@PathVariable("id") Long id) throws Exception {
        SysFile sysFile = sysFileService.getById(id);
        return ApiResult.ok(sysFile);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = SysFile.class)
    public ApiResult<Paging<SysFile>> getSysFilePageList(@Validated @RequestBody SysFilePageParam sysFilePageParam) throws Exception {
        Paging<SysFile> paging = sysFileService.getSysFilePageList(sysFilePageParam);
        return ApiResult.ok(paging);
    }

}

