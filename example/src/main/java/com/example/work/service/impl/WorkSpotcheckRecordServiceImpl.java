package com.example.work.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.work.entity.WorkSpotcheckItems;
import com.example.work.entity.WorkSpotcheckPlan;
import com.example.work.entity.WorkSpotcheckRecord;
import com.example.work.entity.WorkSpotcheckReportform;
import com.example.work.enums.CycleCodeEnum;
import com.example.work.mapper.WorkSpotcheckRecordMapper;
import com.example.work.param.WorkSpotcheckRecordPageParam;
import com.example.work.service.WorkSpotcheckItemsService;
import com.example.work.service.WorkSpotcheckPlanService;
import com.example.work.service.WorkSpotcheckRecordService;
import com.example.work.service.WorkSpotcheckReportformService;

import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import lombok.extern.slf4j.Slf4j;

/**
 *  服务实现类
 *
 * @author wanglonglong
 * @since 2021-05-13
 */
@Slf4j
@Service
public class WorkSpotcheckRecordServiceImpl extends BaseServiceImpl<WorkSpotcheckRecordMapper, WorkSpotcheckRecord> implements WorkSpotcheckRecordService {

    @Autowired
    private WorkSpotcheckRecordMapper workSpotcheckRecordMapper;
    @Autowired
    private WorkSpotcheckItemsService workSpotcheckItemsService;
    @Autowired
    private WorkSpotcheckPlanService workSpotcheckPlanService;
    @Autowired
    private WorkSpotcheckReportformService workSpotcheckReportformService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWorkSpotcheckRecord(WorkSpotcheckRecord workSpotcheckRecord) throws Exception {
        return super.save(workSpotcheckRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateWorkSpotcheckRecord(WorkSpotcheckRecord workSpotcheckRecord) throws Exception {
        return super.updateById(workSpotcheckRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWorkSpotcheckRecord(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<WorkSpotcheckRecord> getWorkSpotcheckRecordPageList(WorkSpotcheckRecordPageParam workSpotcheckRecordPageParam) throws Exception {
        Page<WorkSpotcheckRecord> page = new PageInfo<>(workSpotcheckRecordPageParam, OrderItem.desc(getLambdaColumn(WorkSpotcheckRecord::getCreateTime)));
        LambdaQueryWrapper<WorkSpotcheckRecord> wrapper = new LambdaQueryWrapper<>();
        IPage<WorkSpotcheckRecord> iPage = workSpotcheckRecordMapper.selectPage(page, wrapper);
        return new Paging<WorkSpotcheckRecord>(iPage);
    }

	@Override
	public boolean addList(JSONObject jsonObject) {
		String inspectionCycle = jsonObject.getString("inspectionCycle");
		Integer productionEquipmentId = jsonObject.getInteger("productionEquipmentId");
		Integer spotcheckPlanId = jsonObject.getInteger("spotcheckPlanId");
    	LambdaQueryWrapper<WorkSpotcheckItems> wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(WorkSpotcheckItems::getProductionEquipmentId, productionEquipmentId);
		wrapper.like(WorkSpotcheckItems::getInspectionCycleCode, inspectionCycle);
		List<WorkSpotcheckItems> items = workSpotcheckItemsService.list(wrapper);
		Date now = new Date();
		// 获取点检记录条数
		boolean b = true;
		int nullCount = 0;
		int size = items.size();
		for (int i = 0; i < size; i++) {
			Integer id = jsonObject.getInteger("id"+i);
			Integer spotcheckItemsId = jsonObject.getInteger("spotcheckItemsId"+i);
			Integer determine = jsonObject.getInteger("determine"+i);
			String improve = jsonObject.getString("improve"+i);
			String implementationInformation = jsonObject.getString("implementationInformation"+i);
			
			WorkSpotcheckRecord record = new WorkSpotcheckRecord();
			record.setId(id);
			record.setProductionEquipmentId(productionEquipmentId);
			record.setSpotcheckPlanId(spotcheckPlanId);
			record.setSpotcheckItemsId(spotcheckItemsId);
			record.setImprove(improve);
			record.setImplementationInformation(implementationInformation);
			record.setDetermine(determine);
			record.setUpdateTime(now);
			// 更新或新增
			saveOrUpdate(record);
			if(determine == null) {
				b = false;
				nullCount++;
			}
		}
		// 更新点检计划表
		WorkSpotcheckPlan plan = workSpotcheckPlanService.getById(spotcheckPlanId);
		if(!b && nullCount < size) {
			plan.setUpdateTime(now);
			plan.setInspectionTime(now);
			plan.setSpotCheckStatus(1);// 点检中
			workSpotcheckPlanService.updateById(plan);
		}else if(b) {
			plan.setUpdateTime(now);
			plan.setInspectionTime(now);
			plan.setSpotCheckStatus(2);// 已点检
			workSpotcheckPlanService.updateById(plan);
		}
		// 更新点检报表
		LambdaQueryWrapper<WorkSpotcheckReportform> wp = new LambdaQueryWrapper<>();
		wp.eq(WorkSpotcheckReportform::getProductionEquipmentId, productionEquipmentId);
		WorkSpotcheckReportform reportform = workSpotcheckReportformService.getOne(wp);
		if(reportform == null) {
			reportform = new WorkSpotcheckReportform();
			reportform.setProductionEquipmentId(productionEquipmentId);
			reportform.setUpdateTime(now);
		}
		if(plan.getDefiningPrinciple().equals(CycleCodeEnum.DAY_CODE.getCycle())) {// 日
			reportform.setDaySpotcheckPlanId(plan.getId());
			reportform.setDaySpotCheckStatus(plan.getSpotCheckStatus());
			reportform.setDayInspectionTime(now);
		}
		if(plan.getDefiningPrinciple().equals(CycleCodeEnum.WEEK_CODE.getCycle())) {// 周
			reportform.setWeekSpotcheckPlanId(plan.getId());
			reportform.setWeekSpotCheckStatus(plan.getSpotCheckStatus());
			reportform.setWeekInspectionTime(now);
		}
		if(plan.getDefiningPrinciple().equals(CycleCodeEnum.MONTH_CODE.getCycle())) {// 月
			reportform.setMonthSpotcheckPlanId(plan.getId());
			reportform.setMonthSpotCheckStatus(plan.getSpotCheckStatus());
			reportform.setMonthInspectionTime(now);
		}
		if(plan.getDefiningPrinciple().equals(CycleCodeEnum.QUARTER_CODE.getCycle())) {// 季度
			reportform.setQuarterSpotcheckPlanId(plan.getId());
			reportform.setQuarterSpotCheckStatus(plan.getSpotCheckStatus());
			reportform.setQuarterInspectionTime(now);
		}
		if(plan.getDefiningPrinciple().equals(CycleCodeEnum.HALF_YEAR.getCycle())) {// 半年度
			reportform.setHalfyearSpotcheckPlanId(plan.getId());
			reportform.setHalfyearSpotCheckStatus(plan.getSpotCheckStatus());
			reportform.setHalfyearInspectionTime(now);
		}
		if(plan.getDefiningPrinciple().equals(CycleCodeEnum.YEAR_CODE.getCycle())) {// 年
			reportform.setYearSpotcheckPlanId(plan.getId());
			reportform.setYearSpotCheckStatus(plan.getSpotCheckStatus());
			reportform.setYearInspectionTime(now);
		}
		reportform.setUpdateTime(now);
		workSpotcheckReportformService.saveOrUpdate(reportform);
		return true;
	}
}
