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
import com.example.work.mapper.WorkSpotcheckRecordMapper;
import com.example.work.param.WorkSpotcheckRecordPageParam;
import com.example.work.service.WorkSpotcheckItemsService;
import com.example.work.service.WorkSpotcheckPlanService;
import com.example.work.service.WorkSpotcheckRecordService;

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
			record.setUpdateTime(new Date());
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
			plan.setUpdateTime(new Date());
			plan.setInspectionTime(new Date());
			plan.setSpotCheckStatus(1);// 点检中
			workSpotcheckPlanService.updateById(plan);
		}else if(b) {
			plan.setUpdateTime(new Date());
			plan.setInspectionTime(new Date());
			plan.setSpotCheckStatus(2);// 已点检
			workSpotcheckPlanService.updateById(plan);
		}
		return true;
	}
}
