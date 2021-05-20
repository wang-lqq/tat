package com.example.work.controller;

import java.util.ArrayList;
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
import com.example.work.entity.WorkSpotcheckRecord;
import com.example.work.enums.CycleCodeEnum;
import com.example.work.param.WorkSpotcheckItemsPageParam;
import com.example.work.service.WorkSpotcheckItemsService;
import com.example.work.service.WorkSpotcheckRecordService;

import cn.hutool.core.collection.CollectionUtil;
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
    @Autowired
    private WorkSpotcheckRecordService workSpotcheckRecordService;
    

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
     * 添加
     */
    @PostMapping("/addList")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addList(@Validated(Add.class) @RequestBody List<WorkSpotcheckItems> workSpotcheckItems) throws Exception {
    	boolean flag = false;
    	for (WorkSpotcheckItems item : workSpotcheckItems) {
    		String inspectionCycle = item.getInspectionCycle();
    		if(!StringUtils.isEmpty(inspectionCycle)) {
    			String inspectionCycleCode = CycleCodeEnum.getValue(inspectionCycle);
    			item.setInspectionCycleCode(inspectionCycleCode);
    		}
    		item.setUpdateTime(new Date());
    		flag = workSpotcheckItemsService.saveOrUpdate(item);
		}
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
    public ApiResult<List<JSONObject>> getList(@Validated @RequestBody JSONObject jsonObject) throws Exception {
    	Integer productionEquipmentId = jsonObject.getInteger("productionEquipmentId");
    	Integer id = jsonObject.getInteger("id");// spotcheckPlanId
    	String definingPrinciple = jsonObject.getString("definingPrinciple");
    	
    	LambdaQueryWrapper<WorkSpotcheckItems> wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(WorkSpotcheckItems::getProductionEquipmentId, productionEquipmentId);
    	if(!StringUtils.isEmpty(definingPrinciple)) {
    		wrapper.like(WorkSpotcheckItems::getInspectionCycleCode, definingPrinciple);
    	}
    	
    	List<Integer> itemIds = new ArrayList<>();
    	List<WorkSpotcheckItems> list = workSpotcheckItemsService.list(wrapper);
    	for (WorkSpotcheckItems workSpotcheckItems : list) {
    		itemIds.add(workSpotcheckItems.getId());
		}
    	
    	LambdaQueryWrapper<WorkSpotcheckRecord> wp = new LambdaQueryWrapper<>();
    	wp.eq(WorkSpotcheckRecord::getProductionEquipmentId, productionEquipmentId);
    	wp.eq(WorkSpotcheckRecord::getSpotcheckPlanId, id);
    	if(CollectionUtil.isNotEmpty(itemIds)) {
    		wp.in(WorkSpotcheckRecord::getSpotcheckItemsId, itemIds);
    	}
    	List<WorkSpotcheckRecord> spotcheckRecords = workSpotcheckRecordService.list(wp);
    	
    	List<JSONObject> objs = new ArrayList<>();
    	for (WorkSpotcheckItems workSpotcheckItems : list) {
    		JSONObject obj=(JSONObject) JSONObject.toJSON(workSpotcheckItems);
    		obj.put("determine", "");
			obj.put("improve", "");
			obj.put("implementationInformation", "");
			obj.put("spotcheckRecordId", "");
    		for (WorkSpotcheckRecord workSpotcheckRecord : spotcheckRecords) {
				if(workSpotcheckItems.getId() == workSpotcheckRecord.getSpotcheckItemsId()) {
					// 设置点检结果
					obj.put("determine", workSpotcheckRecord.getDetermine());
					if(!StringUtils.isEmpty(workSpotcheckRecord.getImprove())) {
						obj.put("improve", workSpotcheckRecord.getImprove());
					}
					if(!StringUtils.isEmpty(workSpotcheckRecord.getImplementationInformation())) {
						obj.put("implementationInformation", workSpotcheckRecord.getImplementationInformation());
					}
					obj.put("spotcheckRecordId", workSpotcheckRecord.getId());
				}
			}
    		objs.add(obj);
		}
        return ApiResult.ok(objs);
    }
}

