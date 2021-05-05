package com.example.work.service.impl;

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
import com.example.work.entity.WorkProductionEquipment;
import com.example.work.mapper.WorkProductionEquipmentMapper;
import com.example.work.param.WorkProductionEquipmentPageParam;
import com.example.work.service.WorkProductionEquipmentService;

import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备表 服务实现类
 *
 * @author wanglonglong
 * @since 2021-02-20
 */
@Slf4j
@Service
public class WorkProductionEquipmentServiceImpl extends BaseServiceImpl<WorkProductionEquipmentMapper, WorkProductionEquipment> implements WorkProductionEquipmentService {

    @Autowired
    private WorkProductionEquipmentMapper workProductionEquipmentMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWorkProductionEquipment(WorkProductionEquipment workProductionEquipment) throws Exception {
        return super.saveOrUpdate(workProductionEquipment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateWorkProductionEquipment(WorkProductionEquipment workProductionEquipment) throws Exception {
        return super.updateById(workProductionEquipment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWorkProductionEquipment(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<WorkProductionEquipment> getWorkProductionEquipmentPageList(WorkProductionEquipmentPageParam workProductionEquipmentPageParam) throws Exception {
        Page<WorkProductionEquipment> page = new PageInfo<>(workProductionEquipmentPageParam, OrderItem.desc(getLambdaColumn(WorkProductionEquipment::getCreateTime)));
        LambdaQueryWrapper<WorkProductionEquipment> wrapper = new LambdaQueryWrapper<>();
        String keyword = workProductionEquipmentPageParam.getKeyword();
    	if(!StringUtils.isEmpty(keyword)) {
    		keyword = StringEscapeUtils.unescapeHtml4(keyword);
    		JSONObject obj = JSONObject.parseObject(keyword);
    		String assetCode = obj.getString("assetCode");
    		String equipmentName = obj.getString("equipmentName");
    		String machineNumber = obj.getString("machineNumber");
    		String departmentId = obj.getString("departmentId");
    		if(!StringUtils.isEmpty(assetCode)) {
    			wrapper.like(WorkProductionEquipment::getAssetCode, assetCode);
    		}
    		if(!StringUtils.isEmpty(equipmentName)) {
    			wrapper.like(WorkProductionEquipment::getEquipmentName, equipmentName);
    		}
    		if(!StringUtils.isEmpty(machineNumber)) {
    			wrapper.like(WorkProductionEquipment::getMachineNumber, machineNumber);
    		}
    		if(!StringUtils.isEmpty(departmentId)) {
    			wrapper.like(WorkProductionEquipment::getDepartmentId, departmentId);
    		}
    	}
        IPage<WorkProductionEquipment> iPage = workProductionEquipmentMapper.selectPage(page, wrapper);
        return new Paging<WorkProductionEquipment>(iPage);
    }

}
