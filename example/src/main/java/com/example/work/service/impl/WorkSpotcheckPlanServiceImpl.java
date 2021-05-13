package com.example.work.service.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.work.entity.WorkSpotcheckItems;
import com.example.work.entity.WorkSpotcheckPlan;
import com.example.work.enums.CycleEnum;
import com.example.work.mapper.WorkSpotcheckItemsMapper;
import com.example.work.mapper.WorkSpotcheckPlanMapper;
import com.example.work.param.WorkSpotcheckPlanPageParam;
import com.example.work.service.WorkSpotcheckPlanService;
import com.example.work.vo.InspectionCycleVo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import lombok.extern.slf4j.Slf4j;

/**
 *  服务实现类
 *
 * @author wanglonglong
 * @since 2021-05-06
 */
@Slf4j
@Service
public class WorkSpotcheckPlanServiceImpl extends BaseServiceImpl<WorkSpotcheckPlanMapper, WorkSpotcheckPlan> implements WorkSpotcheckPlanService {

    @Autowired
    private WorkSpotcheckPlanMapper workSpotcheckPlanMapper;
    
    @Autowired
    private WorkSpotcheckItemsMapper workSpotcheckItemsMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWorkSpotcheckPlan(WorkSpotcheckPlan workSpotcheckPlan) throws Exception {
        return super.save(workSpotcheckPlan);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateWorkSpotcheckPlan(WorkSpotcheckPlan workSpotcheckPlan) throws Exception {
        return super.updateById(workSpotcheckPlan);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWorkSpotcheckPlan(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<WorkSpotcheckPlan> getWorkSpotcheckPlanPageList(WorkSpotcheckPlanPageParam workSpotcheckPlanPageParam) throws Exception {
        Page<WorkSpotcheckPlan> page = new PageInfo<>(workSpotcheckPlanPageParam, OrderItem.desc(getLambdaColumn(WorkSpotcheckPlan::getCreateTime)));
        LambdaQueryWrapper<WorkSpotcheckPlan> wrapper = new LambdaQueryWrapper<>();
        IPage<WorkSpotcheckPlan> iPage = workSpotcheckPlanMapper.selectPage(page, wrapper);
        return new Paging<WorkSpotcheckPlan>(iPage);
    }

	@Override
	public List<JSONObject> getDate(JSONObject jsonObject) {
		String month = jsonObject.getString("month");
		Integer productionEquipmentId = jsonObject.getInteger("productionEquipmentId");
		Date date = new Date();
		if(!StringUtils.isEmpty(month)) {
			date = DateUtil.parse(month, "yyyy-MM");
		}
		String spotCheckTime = DateUtil.format(date, "yyyy-MM");
		LambdaQueryWrapper<WorkSpotcheckPlan> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(WorkSpotcheckPlan::getProductionEquipmentId, productionEquipmentId);
		wrapper.like(WorkSpotcheckPlan::getSpotCheckTime, spotCheckTime);
		List<WorkSpotcheckPlan> plans = workSpotcheckPlanMapper.selectList(wrapper);
		// 点检周期排序
		List<InspectionCycleVo> vos = new ArrayList<>();
		LambdaQueryWrapper<WorkSpotcheckItems> wp = new LambdaQueryWrapper<>();
		wp.eq(WorkSpotcheckItems::getProductionEquipmentId, productionEquipmentId);
		wp.groupBy(WorkSpotcheckItems::getInspectionCycle);
		wp.select(WorkSpotcheckItems::getInspectionCycle);
		List<WorkSpotcheckItems> items = workSpotcheckItemsMapper.selectList(wp);
		for (WorkSpotcheckItems workSpotcheckItems : items) {
			InspectionCycleVo vo = new InspectionCycleVo();
			BeanUtil.copyProperties(workSpotcheckItems, vo);
			vo.setSort(CycleEnum.getValue(workSpotcheckItems.getInspectionCycle()));
			vos.add(vo);
		}
		Collections.sort(vos);
		
		List<String> inspectionCycle = new ArrayList<>();
		inspectionCycle.add("无");
		for (InspectionCycleVo vo : vos) {
			inspectionCycle.add(vo.getInspectionCycle());
		}
		
		//按照名称排序
		Collections.sort(items, new Comparator<WorkSpotcheckItems>() {
		    @Override
		    public int compare(WorkSpotcheckItems o1, WorkSpotcheckItems o2) {
		        //排序规则：按照汉字拼音首字母排序
		        Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
		        return com.compare(o1.getInspectionCycle(), o2.getInspectionCycle());
		    }
		});
		
		
		Date start = DateUtil.beginOfMonth(date);
		Date end = DateUtil.endOfMonth(date);
		List<DateTime> dateTimes = DateUtil.rangeToList(start, end, DateField.DAY_OF_MONTH);
		List<JSONObject> range = new ArrayList<>();
		for (DateTime dateTime : dateTimes) {
			JSONObject obj = new JSONObject();
			int week = DateUtil.dayOfWeek(dateTime);
			if(week == 1) {
				week = 7;
			}else {
				week--;
			}
			String dataTimeStr = DateUtil.formatDate(dateTime);
			int day = Integer.parseInt(dataTimeStr.substring(8, 10));
			
			obj.put("week", week);
			obj.put("day", day);
			obj.put("definingPrinciple", "无");
			obj.put("inspectionCycle", inspectionCycle);
			obj.put("month", dateTime.month());
			obj.put("id", 0);
			
			for (WorkSpotcheckPlan plan : plans) {
				if(dataTimeStr.equals(plan.getSpotCheckTime())) {
					obj.put("id", plan.getId());
					obj.put("definingPrinciple", plan.getDefiningPrinciple());
				}
			}
			range.add(obj);
		}
		return range;
	}

	@Override
	public boolean addList(JSONObject jsonObject) {
		Integer productionEquipmentId = jsonObject.getInteger("productionEquipmentId");
		// 获取一个月天数
		String month = jsonObject.getString("month");
		Date date = DateUtil.parse(month, "yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int monthMax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		for (int i = 0; i < monthMax; i++) {
			String checkPlanValue = jsonObject.getString("checkPlan"+i);
			String idValue = jsonObject.getString("id"+i);
			String spotCheckTime = DateUtil.formatDate(DateUtil.offsetDay(date, i));
			
			WorkSpotcheckPlan oldPlan = workSpotcheckPlanMapper.selectById(idValue);
			if(oldPlan != null) {
				// 更新
				oldPlan.setDefiningPrinciple(checkPlanValue);
				oldPlan.setUpdateTime(new Date());
				workSpotcheckPlanMapper.updateById(oldPlan);
			}else {
				// 新增
				WorkSpotcheckPlan plan = new WorkSpotcheckPlan();
				plan.setProductionEquipmentId(productionEquipmentId);
				plan.setSpotCheckTime(spotCheckTime);
				plan.setDefiningPrinciple(checkPlanValue);
				plan.setUpdateTime(new Date());
				save(plan);
			}
		}
		return true;
	}
}
