package com.example.sb.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.sb.entity.SbComputers;
import com.example.sb.param.SbComputersPageParam;
import com.example.sb.service.SbComputersService;

import io.geekidea.springbootplus.framework.common.api.ApiResult;
import io.geekidea.springbootplus.framework.common.controller.BaseController;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.validator.groups.Add;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;
import io.geekidea.springbootplus.framework.log.annotation.Module;
import io.geekidea.springbootplus.framework.log.annotation.OperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.geekidea.springbootplus.system.service.SysDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 *  控制器
 *
 * @author wanglonglong
 * @since 2021-04-01
 */
@Slf4j
@RestController
@RequestMapping("/sbComputers")
@Module("sb")
@Api(value = "API", tags = {""})
public class SbComputersController extends BaseController {

    @Autowired
    private SbComputersService sbComputersService;
    @Autowired
    private SysDepartmentService sysDepartmentService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addSbComputers(@Validated(Add.class) @RequestBody JSONObject jsonObject) throws Exception {
    	String permissionStr = jsonObject.getString("permissionIds");
    	List<Integer> permissionIds = JSONArray.parseArray(permissionStr, Integer.class);
    	jsonObject.remove("permissionIds");
    	
    	String htmlStr = HtmlUtils.htmlUnescape(JSONObject.toJSONString(jsonObject));
    	JSONObject obj = JSONObject.parseObject(htmlStr);
    	SbComputers sbComputers = JSON.toJavaObject(obj, SbComputers.class);
    	
    	if(StringUtils.isEmpty(sbComputers.getDepartmentName())) {
    		String departmentName = sysDepartmentService.getById(sbComputers.getDepartmentId()).getName();
    		sbComputers.setDepartmentName(departmentName);
    	}
    	boolean flag = sbComputersService.saveSbComputers(sbComputers);
    	// 创建软件中间表信息
    	sbComputersService.setComputersSoftware(obj, sbComputers.getId());
    	// 创建文件夹权限
    	sbComputersService.updateSbComputersPermission(permissionIds, sbComputers.getId().intValue());
        return ApiResult.result(flag);
    }
    
    /**
     * 添加
     */
    @PostMapping("/circulation")
    @OperationLog(name = "流转", type = OperationLogType.ADD)
    @ApiOperation(value = "流转", response = ApiResult.class)
    public ApiResult<Boolean> circulation(@Validated(Add.class) @RequestBody JSONObject jsonObject) throws Exception {
    	String permissionStr = jsonObject.getString("permissionIds");
    	List<Integer> permissionIds = JSONArray.parseArray(permissionStr, Integer.class);
    	jsonObject.remove("permissionIds");
    	
    	String htmlStr = HtmlUtils.htmlUnescape(JSONObject.toJSONString(jsonObject));
    	JSONObject obj = JSONObject.parseObject(htmlStr);
    	SbComputers sbComputers = JSON.toJavaObject(obj, SbComputers.class);
    	
    	LambdaQueryWrapper<SbComputers> wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(SbComputers::getMac, sbComputers.getMac());
    	wrapper.eq(SbComputers::getStatus, 0);
    	SbComputers oldComputers = sbComputersService.getOne(wrapper);
    	if(oldComputers != null && oldComputers.getFullName().equals(sbComputers.getFullName())) {
    		// 一台机器不能流转同一个人
    		return ApiResult.result(true);
    	}
    	if(oldComputers != null) {
    		// 流转
    		oldComputers.setStatus(1);
    		oldComputers.setUpdateTime(new Date());
    		oldComputers.setCirculationTime(new Date());
    		sbComputersService.updateById(oldComputers);
    	}
    	if(StringUtils.isEmpty(sbComputers.getDepartmentName())) {
    		String departmentName = sysDepartmentService.getById(sbComputers.getDepartmentId()).getName();
    		sbComputers.setDepartmentName(departmentName);
    	}
    	boolean flag = sbComputersService.saveSbComputers(sbComputers);
    	// 创建软件中间表信息
    	sbComputersService.setComputersSoftware(obj, sbComputers.getId());
    	// 创建文件夹权限
    	sbComputersService.updateSbComputersPermission(permissionIds, sbComputers.getId().intValue());
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateSbComputers(@Validated(Update.class) @RequestBody JSONObject jsonObject) throws Exception {
        String permissionStr = jsonObject.getString("permissionIds");
    	List<Integer> permissionIds = JSONArray.parseArray(permissionStr, Integer.class);
    	jsonObject.remove("permissionIds");
    	
    	String htmlStr = HtmlUtils.htmlUnescape(JSONObject.toJSONString(jsonObject));
    	JSONObject obj = JSONObject.parseObject(htmlStr);
    	SbComputers sbComputers = JSON.toJavaObject(obj, SbComputers.class);
    	
    	if(StringUtils.isEmpty(sbComputers.getDepartmentName())) {
    		String departmentName = sysDepartmentService.getById(sbComputers.getDepartmentId()).getName();
    		sbComputers.setDepartmentName(departmentName);
    	}
    	sbComputers.setUpdateTime(new Date());
    	boolean flag = sbComputersService.updateSbComputers(sbComputers);
    	// 更新软件信息
    	sbComputersService.updateSoftware(obj,sbComputers.getId());
    	// 创建文件夹权限
    	sbComputersService.updateSbComputersPermission(permissionIds, sbComputers.getId().intValue());
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteSbComputers(@PathVariable("id") Long id) throws Exception {
        boolean flag = sbComputersService.deleteSbComputers(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = SbComputers.class)
    public ApiResult<SbComputers> getSbComputers(@PathVariable("id") Long id) throws Exception {
        SbComputers sbComputers = sbComputersService.getById(id);
        return ApiResult.ok(sbComputers);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = SbComputers.class)
    public ApiResult<Paging<SbComputers>> getSbComputersPageList(@Validated @RequestBody SbComputersPageParam sbComputersPageParam) throws Exception {
        Paging<SbComputers> paging = sbComputersService.getSbComputersPageList(sbComputersPageParam);
        return ApiResult.ok(paging);
    }
    
    /**
     * 分页列表
     */
    @PostMapping("/getList")
    @OperationLog(name = "列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "列表", response = SbComputers.class)
    public ApiResult<List<JSONObject>> getList(@Validated @RequestBody JSONObject jsonObject) throws Exception {
        List<JSONObject> list = sbComputersService.getList(jsonObject);
        return ApiResult.ok(list);
    }
    
    /**
     * 通过域用户名查询电脑台账id
     */
    @PostMapping("/getByDomainUsername")
    @OperationLog(name = "通过域用户名查询电脑台账id", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "通过域用户名查询电脑台账id", response = Long.class)
    public ApiResult<Long> getByDomainUsername(@Validated @RequestBody JSONObject jsonObject){
        Long id = sbComputersService.getByDomainUsername(jsonObject);
        return ApiResult.ok(id);
    }
}

