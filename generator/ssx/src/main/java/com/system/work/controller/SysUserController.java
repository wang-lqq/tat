package com.system.work.controller;

import com.system.work.entity.SysUser;
import com.system.work.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import com.system.work.param.SysUserPageParam;
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
 * 系统用户 控制器
 *
 * @author wanglonglong
 * @since 2021-02-24
 */
@Slf4j
@RestController
@RequestMapping("/sysUser")
@Module("work")
@Api(value = "系统用户API", tags = {"系统用户"})
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 添加系统用户
     */
    @PostMapping("/add")
    @OperationLog(name = "添加系统用户", type = OperationLogType.ADD)
    @ApiOperation(value = "添加系统用户", response = ApiResult.class)
    public ApiResult<Boolean> addSysUser(@Validated(Add.class) @RequestBody SysUser sysUser) throws Exception {
        boolean flag = sysUserService.saveSysUser(sysUser);
        return ApiResult.result(flag);
    }

    /**
     * 修改系统用户
     */
    @PostMapping("/update")
    @OperationLog(name = "修改系统用户", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改系统用户", response = ApiResult.class)
    public ApiResult<Boolean> updateSysUser(@Validated(Update.class) @RequestBody SysUser sysUser) throws Exception {
        boolean flag = sysUserService.updateSysUser(sysUser);
        return ApiResult.result(flag);
    }

    /**
     * 删除系统用户
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除系统用户", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除系统用户", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysUser(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysUserService.deleteSysUser(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取系统用户详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "系统用户详情", type = OperationLogType.INFO)
    @ApiOperation(value = "系统用户详情", response = SysUser.class)
    public ApiResult<SysUser> getSysUser(@PathVariable("id") Long id) throws Exception {
        SysUser sysUser = sysUserService.getById(id);
        return ApiResult.ok(sysUser);
    }

    /**
     * 系统用户分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "系统用户分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "系统用户分页列表", response = SysUser.class)
    public ApiResult<Paging<SysUser>> getSysUserPageList(@Validated @RequestBody SysUserPageParam sysUserPageParam) throws Exception {
        Paging<SysUser> paging = sysUserService.getSysUserPageList(sysUserPageParam);
        return ApiResult.ok(paging);
    }

}

