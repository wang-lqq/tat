package com.example.work.controller;

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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.work.entity.WorkSpotcheckItems;
import com.example.work.enums.CycleCodeEnum;
import com.example.work.param.WorkSpotcheckItemsPageParam;
import com.example.work.service.WorkSpotcheckItemsService;

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
 * @since 2021-05-05
 */
@Slf4j
@RestController
@RequestMapping("/workSpotcheckItems")
@Module("work")
@Api(value = "API", tags = {""})
public class WorkSpotcheckItemsController extends BaseController {

    @Autowired
    private WorkSpotcheckItemsService workSpotcheckItemsService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addWorkSpotcheckItems(@Validated(Add.class) @RequestBody WorkSpotcheckItems workSpotcheckItems) throws Exception {
    	String inspectionCycle = workSpotcheckItems.getInspectionCycle();
    	if(!StringUtils.isEmpty(inspectionCycle)) {
    		String inspectionCycleCode = CycleCodeEnum.getValue(inspectionCycle);
    		workSpotcheckItems.setInspectionCycleCode(inspectionCycleCode);
    	}
    	boolean flag = workSpotcheckItemsService.saveWorkSpotcheckItems(workSpotcheckItems);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateWorkSpotcheckItems(@Validated(Update.class) @RequestBody WorkSpotcheckItems workSpotcheckItems) throws Exception {
    	String inspectionCycle = workSpotcheckItems.getInspectionCycle();
    	if(!StringUtils.isEmpty(inspectionCycle)) {
    		String inspectionCycleCode = CycleCodeEnum.getValue(inspectionCycle);
    		workSpotcheckItems.setInspectionCycleCode(inspectionCycleCode);
    	}
    	workSpotcheckItems.setUpdateTime(new Date());
    	boolean flag = workSpotcheckItemsService.updateWorkSpotcheckItems(workSpotcheckItems);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteWorkSpotcheckItems(@PathVariable("id") Long id) throws Exception {
        boolean flag = workSpotcheckItemsService.deleteWorkSpotcheckItems(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = WorkSpotcheckItems.class)
    public ApiResult<WorkSpotcheckItems> getWorkSpotcheckItems(@PathVariable("id") Long id) throws Exception {
        WorkSpotcheckItems workSpotcheckItems = workSpotcheckItemsService.getById(id);
        return ApiResult.ok(workSpotcheckItems);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = WorkSpotcheckItems.class)
    public ApiResult<Paging<WorkSpotcheckItems>> getWorkSpotcheckItemsPageList(@Validated @RequestBody WorkSpotcheckItemsPageParam workSpotcheckItemsPageParam) throws Exception {
        Paging<WorkSpotcheckItems> paging = workSpotcheckItemsService.getWorkSpotcheckItemsPageList(workSpotcheckItemsPageParam);
        return ApiResult.ok(paging);
    }
    
    /**
     * 分页列表
     */
    @PostMapping("/getList")
    @OperationLog(name = "列表", type = OperationLogType.LIST)
    @ApiOperation(value = "列表", response = WorkSpotcheckItems.class)
    public ApiResult<List<WorkSpotcheckItems>> getList(@Validated @RequestBody JSONObject jsonObject) throws Exception {
    	Integer productionEquipmentId = jsonObject.getInteger("productionEquipmentId");
    	LambdaQueryWrapper<WorkSpotcheckItems> wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(WorkSpotcheckItems::getProductionEquipmentId, productionEquipmentId);
    	List<WorkSpotcheckItems> list = workSpotcheckItemsService.list(wrapper);
        return ApiResult.ok(list);
    }
}

