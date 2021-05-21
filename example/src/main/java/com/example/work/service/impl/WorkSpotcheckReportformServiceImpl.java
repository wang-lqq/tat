package com.example.work.service.impl;

import java.util.Date;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.work.entity.WorkSpotcheckReportform;
import com.example.work.enums.CycleCodeEnum;
import com.example.work.mapper.WorkSpotcheckReportformMapper;
import com.example.work.param.WorkSpotcheckReportformPageParam;
import com.example.work.service.WorkSpotcheckReportformService;
import com.example.work.vo.WorkSpotcheckReportformVo;

import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import lombok.extern.slf4j.Slf4j;

/**
 *  服务实现类
 *
 * @author wanglonglong
 * @since 2021-05-20
 */
@Slf4j
@Service
public class WorkSpotcheckReportformServiceImpl extends BaseServiceImpl<WorkSpotcheckReportformMapper, WorkSpotcheckReportform> implements WorkSpotcheckReportformService {

    @Autowired
    private WorkSpotcheckReportformMapper workSpotcheckReportformMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWorkSpotcheckReportform(WorkSpotcheckReportform workSpotcheckReportform) throws Exception {
        return super.save(workSpotcheckReportform);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateWorkSpotcheckReportform(WorkSpotcheckReportform workSpotcheckReportform) throws Exception {
        return super.updateById(workSpotcheckReportform);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWorkSpotcheckReportform(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<WorkSpotcheckReportform> getWorkSpotcheckReportformPageList(WorkSpotcheckReportformPageParam workSpotcheckReportformPageParam) throws Exception {
        Page<WorkSpotcheckReportform> page = new PageInfo<>(workSpotcheckReportformPageParam, OrderItem.desc(getLambdaColumn(WorkSpotcheckReportform::getCreateTime)));
        LambdaQueryWrapper<WorkSpotcheckReportform> wrapper = new LambdaQueryWrapper<>();
        IPage<WorkSpotcheckReportform> iPage = workSpotcheckReportformMapper.selectPage(page, wrapper);
        return new Paging<WorkSpotcheckReportform>(iPage);
    }

	@Override
	public Paging<WorkSpotcheckReportformVo> reportForm(WorkSpotcheckReportformPageParam workSpotcheckReportformPageParam) {
		Page<WorkSpotcheckReportform> page = new PageInfo<>(workSpotcheckReportformPageParam);
        
		Integer departmentId = 0;
		String assetCode = "";
		String equipmentName = "";
		String machineNumber = "";
		String keyword = workSpotcheckReportformPageParam.getKeyword();
    	if(!StringUtils.isEmpty(keyword)) {
    		keyword = StringEscapeUtils.unescapeHtml4(keyword);
    		JSONObject obj = JSONObject.parseObject(keyword);
    		departmentId = obj.getInteger("departmentId");
    		assetCode = obj.getString("assetCode");
    		equipmentName = obj.getString("equipmentName");
    		machineNumber = obj.getString("machineNumber");
    	}
    	IPage<WorkSpotcheckReportformVo> iPage = workSpotcheckReportformMapper.getReportFormPageList(page, workSpotcheckReportformPageParam,
    			departmentId,assetCode,equipmentName,machineNumber);
        return new Paging<WorkSpotcheckReportformVo>(iPage);
	}
	
	public void setIsSpotcheckItems(String inspectionCycle, Integer productionEquipmentId) {
		LambdaQueryWrapper<WorkSpotcheckReportform> wp = new LambdaQueryWrapper<>();
		wp.eq(WorkSpotcheckReportform::getProductionEquipmentId, productionEquipmentId);
		WorkSpotcheckReportform reportform = workSpotcheckReportformMapper.selectOne(wp);
		
		Date now = new Date();
		int isSpotcheckItems = 1;
		if(reportform == null) {
			reportform = new WorkSpotcheckReportform();
			reportform.setProductionEquipmentId(productionEquipmentId);
		}
		if(inspectionCycle.equals(CycleCodeEnum.DAY_CODE.getCycle())) {// 日
			reportform.setDaySpotcheckItems(isSpotcheckItems);
		}
		if(inspectionCycle.equals(CycleCodeEnum.WEEK_CODE.getCycle())) {// 周
			reportform.setWeekSpotcheckItems(isSpotcheckItems);
		}
		if(inspectionCycle.equals(CycleCodeEnum.MONTH_CODE.getCycle())) {// 月
			reportform.setMonthSpotcheckItems(isSpotcheckItems);
		}
		if(inspectionCycle.equals(CycleCodeEnum.QUARTER_CODE.getCycle())) {// 季度
			reportform.setQuarterSpotcheckItems(isSpotcheckItems);
		}
		if(inspectionCycle.equals(CycleCodeEnum.HALF_YEAR.getCycle())) {// 半年度
			reportform.setHalfyearSpotcheckItems(isSpotcheckItems);
		}
		if(inspectionCycle.equals(CycleCodeEnum.YEAR_CODE.getCycle())) {// 年
			reportform.setYearSpotcheckItems(isSpotcheckItems);
		}
		reportform.setUpdateTime(now);
		this.saveOrUpdate(reportform);
	}
}
