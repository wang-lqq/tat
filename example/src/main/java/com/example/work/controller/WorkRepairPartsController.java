package com.example.work.controller;

import java.math.BigDecimal;
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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.work.entity.WorkParts;
import com.example.work.entity.WorkRepairParts;
import com.example.work.entity.WorkRepairReport;
import com.example.work.param.WorkRepairPartsPageParam;
import com.example.work.service.WorkPartsService;
import com.example.work.service.WorkRepairPartsService;
import com.example.work.service.WorkRepairReportService;

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
 * 维修配件表 控制器
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
@Slf4j
@RestController
@RequestMapping("/workRepairParts")
@Module("work")
@Api(value = "维修配件表API", tags = {"维修配件表"})
public class WorkRepairPartsController extends BaseController {
    @Autowired
    private WorkRepairPartsService workRepairPartsService;
    @Autowired
    private WorkRepairReportService workRepairReportService;
    @Autowired
    private WorkPartsService workPartsService;

    /**
     * 添加维修配件表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加维修配件表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加维修配件表", response = ApiResult.class)
    public ApiResult<Boolean> addWorkRepairParts(@Validated(Add.class) @RequestBody WorkRepairParts workRepairParts) throws Exception {
    	WorkParts workParts = workPartsService.getById(workRepairParts.getPartsId());
    	workRepairParts.setUnitPrice(workParts.getUnitPrice());
    	if(!workParts.getAccessoryName().contains("人工费")) {
    		workRepairParts.setNumber(1);
    	}
    	workRepairParts.setMoney(workParts.getUnitPrice());
    	workRepairParts.setAccessoryName(workParts.getAccessoryName());
    	workRepairParts.setMaterialCode(workParts.getMaterialCode());
    	workRepairParts.setBrand(workParts.getBrand());
    	String specifications= HtmlUtils.htmlUnescape(workParts.getSpecifications());
    	workRepairParts.setSpecifications(specifications);
    	workRepairParts.setCompany(workParts.getCompany());
    	workRepairParts.setSupplierName(workParts.getSupplierName());
    	workRepairParts.setRemarks(workParts.getRemarks());
    	boolean flag = workRepairPartsService.saveWorkRepairParts(workRepairParts);
        return ApiResult.result(flag);
    }

    /**
     * 修改维修配件表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改维修配件表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改维修配件表", response = ApiResult.class)
    public ApiResult<Boolean> updateWorkRepairParts(@Validated(Update.class) @RequestBody WorkRepairParts workRepairParts) throws Exception {
        if(workRepairParts.getUnitPrice() != null && workRepairParts.getUnitPrice() == null) {
        	BigDecimal unitPrice = new BigDecimal(workRepairParts.getUnitPrice());
        	double money = unitPrice.doubleValue();
        	workRepairParts.setMoney(money);
        }
        if(workRepairParts.getNumber() != null && workRepairParts.getUnitPrice() != null) {
        	BigDecimal number = new BigDecimal(workRepairParts.getNumber());
        	BigDecimal unitPrice = new BigDecimal(workRepairParts.getUnitPrice());
        	double money = unitPrice.multiply(number).doubleValue();
        	workRepairParts.setMoney(money);
        }
        if(!StringUtils.isEmpty(workRepairParts.getSpecifications())) {
        	String specifications = workRepairParts.getSpecifications();
        	if(specifications.contains("&amp;")) {
        		specifications = specifications.replace("&amp;", "&");
        	}
        	specifications = HtmlUtils.htmlUnescape(specifications);
        	workRepairParts.setSpecifications(specifications);
        }
        boolean	flag = workRepairPartsService.updateWorkRepairParts(workRepairParts);
        // 更新总金额
        double money = 0;
        String workOrderNo = workRepairParts.getWorkOrderNo();
        LambdaQueryWrapper<WorkRepairParts> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkRepairParts::getWorkOrderNo, workOrderNo);
        List<WorkRepairParts> list = workRepairPartsService.list(wrapper);
        for (WorkRepairParts part : list) {
        	money += part.getMoney();
		}
        // 更新
        LambdaQueryWrapper<WorkRepairReport> wp = new LambdaQueryWrapper<>();
        wp.eq(WorkRepairReport::getWorkOrderNo, workOrderNo);
        List<WorkRepairReport> repairReports = workRepairReportService.list(wp);
        if(CollectionUtil.isNotEmpty(repairReports)) {
        	WorkRepairReport repairReport = repairReports.get(0);
        	repairReport.setTotalCost(money);
        	repairReport.setUpdateTime(new Date());
        	workRepairReportService.updateWorkRepairReport(repairReport);
        }
        return ApiResult.result(flag);
    }

    /**
     * 删除维修配件表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除维修配件表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除维修配件表", response = ApiResult.class)
    public ApiResult<Boolean> deleteWorkRepairParts(@PathVariable("id") Long id) throws Exception {
        boolean flag = workRepairPartsService.deleteWorkRepairParts(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取维修配件表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "维修配件表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "维修配件表详情", response = WorkRepairParts.class)
    public ApiResult<WorkRepairParts> getWorkRepairParts(@PathVariable("id") Long id) throws Exception {
        WorkRepairParts workRepairParts = workRepairPartsService.getById(id);
        return ApiResult.ok(workRepairParts);
    }

    /**
     * 维修配件表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "维修配件表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "维修配件表分页列表", response = WorkRepairParts.class)
    public ApiResult<Paging<WorkRepairParts>> getWorkRepairPartsPageList(@Validated @RequestBody WorkRepairPartsPageParam workRepairPartsPageParam) throws Exception {
    	Paging<WorkRepairParts> paging = workRepairPartsService.getWorkRepairPartsPageList(workRepairPartsPageParam);
        List<WorkRepairParts> repairParts = paging.getRecords();
        if(CollectionUtil.isNotEmpty(repairParts)) {
        	WorkRepairParts countParts = new WorkRepairParts();
        	double countMoney = 0;
        	for (WorkRepairParts workRepairParts : repairParts) {
        		countMoney += workRepairParts.getMoney();
        	}
        	countParts.setAccessoryName("维修总费用");
        	countParts.setMoney(countMoney);
        	repairParts.add(countParts);
        }
        return ApiResult.ok(paging);
    }

}

