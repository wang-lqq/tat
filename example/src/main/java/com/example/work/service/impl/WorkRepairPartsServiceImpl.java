package com.example.work.service.impl;

import com.example.work.entity.WorkRepairParts;
import com.example.work.entity.WorkRepairReport;
import com.example.work.mapper.WorkRepairPartsMapper;
import com.example.work.service.WorkRepairPartsService;
import com.example.work.param.WorkRepairPartsPageParam;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 维修配件表 服务实现类
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
@Slf4j
@Service
public class WorkRepairPartsServiceImpl extends BaseServiceImpl<WorkRepairPartsMapper, WorkRepairParts> implements WorkRepairPartsService {

    @Autowired
    private WorkRepairPartsMapper workRepairPartsMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWorkRepairParts(WorkRepairParts workRepairParts) throws Exception {
        return super.save(workRepairParts);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateWorkRepairParts(WorkRepairParts workRepairParts) throws Exception {
        return super.updateById(workRepairParts);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWorkRepairParts(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<WorkRepairParts> getWorkRepairPartsPageList(WorkRepairPartsPageParam workRepairPartsPageParam) throws Exception {
    	LambdaQueryWrapper<WorkRepairParts> wrapper = new LambdaQueryWrapper<>();
    	String keyword = workRepairPartsPageParam.getKeyword();
    	if(!StringUtils.isEmpty(keyword)) {
    		keyword = StringEscapeUtils.unescapeHtml4(keyword);
    		JSONObject obj = JSONObject.parseObject(keyword);
    		String workOrderNo = obj.getString("workOrderNo");
    		if(!StringUtils.isEmpty(workOrderNo)) {
    			wrapper.ge(WorkRepairParts::getWorkOrderNo, workOrderNo);
    		}
    	}
    	Page<WorkRepairParts> page = new PageInfo<>(workRepairPartsPageParam, OrderItem.desc(getLambdaColumn(WorkRepairParts::getCreateTime)));
        IPage<WorkRepairParts> iPage = workRepairPartsMapper.selectPage(page, wrapper);
        return new Paging<WorkRepairParts>(iPage);
    }

}
