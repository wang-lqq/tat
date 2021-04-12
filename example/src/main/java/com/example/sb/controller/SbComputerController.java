package com.example.sb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.sb.entity.SbComputer;
import com.example.sb.entity.SbComputerRecord;
import com.example.sb.param.SbComputerPageParam;
import com.example.sb.service.SbComputerRecordService;
import com.example.sb.service.SbComputerService;

import cn.hutool.core.collection.CollectionUtil;
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
    @Autowired
    private SbComputerRecordService sbComputerRecordService;
    @Autowired
    private SysDepartmentService sysDepartmentService;
    
    
    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addSbComputer(@Validated(Add.class) @RequestBody JSONObject jsonObject) throws Exception {
    	boolean flag = false;
    	String str = JSONObject.toJSONString(jsonObject);
    	jsonObject = JSONObject.parseObject(HtmlUtils.htmlUnescape(str));
    	
    	Map<String, Object> map = new HashMap<>();
    	map.put("mac", jsonObject.getString("mac"));
    	if(CollectionUtil.isNotEmpty(sbComputerService.listByMap(map))) {
    		return ApiResult.result(flag);
    	}
    	SbComputer sbComputer = JSON.toJavaObject(jsonObject, SbComputer.class);
    	SbComputerRecord sbComputerRecord = JSON.toJavaObject(jsonObject, SbComputerRecord.class);
    	flag = sbComputerService.saveSbComputer(sbComputer);
    	
    	String departmentName = sysDepartmentService.getById(jsonObject.getString("departmentId")).getName();
    	sbComputerRecord.setDepartmentName(departmentName);
    	sbComputerRecord.setMac(sbComputer.getMac());
    	sbComputerRecordService.saveSbComputerRecord(sbComputerRecord);
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
    public ApiResult<Paging<JSONObject>> getSbComputerPageList(@Validated @RequestBody SbComputerPageParam sbComputerPageParam) throws Exception {
        Paging<JSONObject> paging = sbComputerService.getSbComputerPageList(sbComputerPageParam);
        return ApiResult.ok(paging);
    }

}

