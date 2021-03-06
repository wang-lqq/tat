/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.geekidea.springbootplus.system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.geekidea.springbootplus.framework.common.api.ApiResult;
import io.geekidea.springbootplus.framework.common.controller.BaseController;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.log.annotation.Module;
import io.geekidea.springbootplus.framework.log.annotation.OperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.geekidea.springbootplus.framework.shiro.util.CurrentUserUtil;
import io.geekidea.springbootplus.system.entity.SysPermission;
import io.geekidea.springbootplus.system.param.SysPermissionPageParam;
import io.geekidea.springbootplus.system.service.SysPermissionService;
import io.geekidea.springbootplus.system.service.SysRolePermissionService;
import io.geekidea.springbootplus.system.vo.SysPermissionQueryVo;
import io.geekidea.springbootplus.system.vo.SysPermissionTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 系统权限 前端控制器
 * </pre>
 *
 * @author geekidea
 * @since 2019-10-24
 */
@Slf4j
@RestController
@RequestMapping("/sysPermission")
@Module("system")
@Api(value = "系统权限 API", tags = {"系统权限"})
public class SysPermissionController extends BaseController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 添加系统权限
     */
    @PostMapping("/insert")
//    @RequiresPermissions("sys:permission:add")
    @OperationLog(name = "添加系统权限", type = OperationLogType.ADD)
    @ApiOperation(value = "添加系统权限", response = ApiResult.class)
    public ApiResult<Boolean> addSysPermission(@Validated @RequestBody SysPermission sysPermission) throws Exception {
    	if(sysPermission.getParentId() != null && sysPermission.getParentId() != 0) {
    		sysPermission.setLevel(2);
    	}else {
    		sysPermission.setLevel(1);
    	}
        boolean flag = sysPermissionService.saveSysPermission(sysPermission);
        return ApiResult.result(flag);
    }

    /**
     * 修改系统权限
     */
    @PostMapping("/update")
//    @RequiresPermissions("sys:permission:update")
    @OperationLog(name = "添加系统权限", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改系统权限", response = ApiResult.class)
    public ApiResult<Boolean> updateSysPermission(@Validated @RequestBody SysPermission sysPermission) throws Exception {
    	if(sysPermission.getParentId() != null && sysPermission.getParentId() != 0) {
    		sysPermission.setLevel(2);
    	}else {
    		sysPermission.setLevel(1);
    	}
        boolean flag = sysPermissionService.updateSysPermission(sysPermission);
        return ApiResult.result(flag);
    }

    /**
     * 删除系统权限
     */
    @PostMapping("/delete/{id}")
//    @RequiresPermissions("sys:permission:delete")
    @OperationLog(name = "删除系统权限", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除系统权限", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysPermission(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysPermissionService.deleteSysPermission(id);
        return ApiResult.result(flag);
    }

    /**
     * 系统权限详情
     */
    @GetMapping("/info/{id}")
//    @RequiresPermissions("sys:permission:info")
    @OperationLog(name = "系统权限详情", type = OperationLogType.INFO)
    @ApiOperation(value = "系统权限详情", notes = "", response = SysPermissionQueryVo.class)
    public ApiResult<SysPermissionQueryVo> getSysPermission(@PathVariable("id") Long id) throws Exception {
        SysPermissionQueryVo sysPermissionQueryVo = sysPermissionService.getSysPermissionById(id);
        return ApiResult.ok(sysPermissionQueryVo);
    }

    /**
     * 系统权限分页列表
     */
    @PostMapping("/getPageList")
//    @RequiresPermissions("sys:permission:page")
    @OperationLog(name = "系统权限分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "系统权限分页列表", response = SysPermissionQueryVo.class)
    public ApiResult<Paging<SysPermissionQueryVo>> getSysPermissionPageList(@Validated @RequestBody SysPermissionPageParam sysPermissionPageParam) throws Exception {
        Paging<SysPermissionQueryVo> paging = sysPermissionService.getSysPermissionPageList(sysPermissionPageParam);
        return ApiResult.ok(paging);
    }

    /**
     * 获取所有菜单列表
     * @return
     */
    @GetMapping("/getAllMenuList")
//    @RequiresPermissions("sys:permission:all:menu:list")
    @OperationLog(name = "获取所有菜单列表", type = OperationLogType.LIST)
    @ApiOperation(value = "获取所有菜单列表", response = SysPermission.class)
    public ApiResult<List<SysPermission>> getAllMenuList() throws Exception {
        List<SysPermission> list = sysPermissionService.getAllMenuList();
        return ApiResult.ok(list);
    }

    /**
     * 获取获取菜单树形列表
     * @return
     */
    @GetMapping("/getAllMenuTree")
//    @RequiresPermissions("sys:permission:all:menu:tree")
    @OperationLog(name = "获取获取菜单树形列表", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "获取获取菜单树形列表", response = SysPermissionTreeVo.class)
    public ApiResult<List<SysPermissionTreeVo>> getAllMenuTree() throws Exception {
        List<SysPermissionTreeVo> treeVos = sysPermissionService.getAllMenuTree();
        return ApiResult.ok(treeVos);
    }


    /**
     * 根据用户id获取菜单列表
     * @return
     */
    @PostMapping("/getMenuListByUserId/{userId}")
//    @RequiresPermissions("sys:permission:menu:list")
    @OperationLog(name = "根据用户id获取菜单列表", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "根据用户id获取菜单列表", response = SysPermission.class)
    public ApiResult<List<SysPermission>> getMenuListByUserId(@PathVariable("userId") Long userId) throws Exception {
        List<SysPermission> list = sysPermissionService.getMenuListByUserId(userId);
        return ApiResult.ok(list);
    }

    /**
     * 根据用户id获取菜单树形列表
     * @return
     */
    @PostMapping("/getMenuTreeByUserId/{userId}")
//    @RequiresPermissions("sys:permission:menu:tree")
    @OperationLog(name = "根据用户id获取菜单树形列表", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "根据用户id获取菜单树形列表", response = SysPermissionTreeVo.class)
    public ApiResult<List<SysPermissionTreeVo>> getMenuTreeByUserId(@PathVariable("userId") Long userId) throws Exception {
        List<SysPermissionTreeVo> treeVos = sysPermissionService.getMenuTreeByUserId(userId);
        return ApiResult.ok(treeVos);
    }
    
    @GetMapping("/getMenuTreeByUserIds")
    @OperationLog(name = "根据用户id获取菜单树形列表", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "根据用户id获取菜单树形列表", response = SysPermissionTreeVo.class)
    public ApiResult<List<Map<String, Object>>> getMenuTreeByUserIdv2() throws Exception {
    	Long userId = CurrentUserUtil.getUserIfLogin().getId();
    	List<Map<String, Object>> treeVos = sysPermissionService.getMenuTreeByUserIdv2(userId);
    	return ApiResult.ok(treeVos);
    }

    /**
     * 根据用户id获取该用户所有权限编码
     * @return
     */
    @GetMapping("/getPermissionCodesByUserId/{userId}")
//    @RequiresPermissions("sys:permission:codes")
    @OperationLog(name = "根据用户id获取该用户所有权限编码", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "根据用户id获取该用户所有权限编码", response = ApiResult.class)
    public ApiResult<List<String>> getPermissionCodesByUserId(@PathVariable("userId") Long userId) throws Exception {
        List<String> list = sysPermissionService.getPermissionCodesByUserId(userId);
        return ApiResult.ok(list);
    }

    /**
     * 根据角色id获取该对应的所有三级权限ID
     * @return
     */
    @GetMapping("/getThreeLevelPermissionIdsByRoleId/{roleId}")
//    @RequiresPermissions("sys:permission:three-ids-by-role-id")
    @OperationLog(name = "根据角色id获取该对应的所有三级权限ID", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "根据角色id获取该对应的所有三级权限ID", response = ApiResult.class)
    public ApiResult<List<Long>> getPermissionIdsByRoleId(@PathVariable("roleId") Long roleId) throws Exception {
        List<Long> list = sysRolePermissionService.getThreeLevelPermissionIdsByRoleId(roleId);
        return ApiResult.ok(list);
    }
    
    /**
     * 根据角色id获取该对应的所有权限
     * @return
     */
    @GetMapping("/getPermissionByRoleId/{roleId}")
//    @RequiresPermissions("sys:permission:by-role-id")
    @OperationLog(name = "根据角色id获取该对应的所有权限", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "根据角色id获取该对应的所有权限", response = ApiResult.class)
    public ApiResult<List<SysPermissionTreeVo>> getPermissionByRoleId(@PathVariable("roleId") Long roleId) throws Exception {
        List<SysPermissionTreeVo> list = sysPermissionService.getPermissionByRoleId(roleId);
        return ApiResult.ok(list);
    }

    /**
     * 获取所有导航树形菜单(一级/二级菜单)
     * @return
     */
    @PostMapping("/getNavMenuTree")
//    @RequiresPermissions("sys:permission:nav-menu")
    @OperationLog(name = "获取所有导航菜单(一级/二级菜单)", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "获取所有导航菜单(一级/二级菜单)", response = ApiResult.class)
    public ApiResult<List<SysPermissionTreeVo>> getNavMenuTree() throws Exception {
        List<SysPermissionTreeVo> list = sysPermissionService.getNavMenuTree();
        return ApiResult.ok(list);
    }
    
    /**
     * 获取一级菜单
     * @return
     */
    @PostMapping("/getOneLevel")
//    @RequiresPermissions("sys:permission:getOneLevel")
    @OperationLog(name = "获取一级菜单", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "获取一级菜单", response = ApiResult.class)
    public ApiResult<List<SysPermission>> OneLevel() throws Exception {
        List<SysPermission> list = sysPermissionService.OneLevel();
        return ApiResult.ok(list);
    }
}

