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
import com.example.work.entity.WorkRepairReport;
import com.example.work.mapper.WorkRepairReportMapper;
import com.example.work.param.WorkRepairReportPageParam;
import com.example.work.service.WorkRepairReportService;

import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import lombok.extern.slf4j.Slf4j;

/**
 * 联络-维修单表 服务实现类
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
@Slf4j
@Service
public class WorkRepairReportServiceImpl extends BaseServiceImpl<WorkRepairReportMapper, WorkRepairReport> implements WorkRepairReportService {

    @Autowired
    private WorkRepairReportMapper workRepairReportMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWorkRepairReport(WorkRepairReport workRepairReport) throws Exception {
        return super.save(workRepairReport);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateWorkRepairReport(WorkRepairReport workRepairReport) throws Exception {
        return super.updateById(workRepairReport);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWorkRepairReport(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<WorkRepairReport> getWorkRepairReportPageList(WorkRepairReportPageParam workRepairReportPageParam) throws Exception {
    	LambdaQueryWrapper<WorkRepairReport> wrapper = new LambdaQueryWrapper<>();
    	String keyword = workRepairReportPageParam.getKeyword();
    	if(!StringUtils.isEmpty(keyword)) {
    		keyword = StringEscapeUtils.unescapeHtml4(keyword);
    		JSONObject obj = JSONObject.parseObject(keyword);
    		String status = obj.getString("status");
    		if(!StringUtils.isEmpty(status)) {
    			wrapper.ge(WorkRepairReport::getStatus, status);
    		}
    	}
    	Page<WorkRepairReport> page = new PageInfo<>(workRepairReportPageParam, OrderItem.desc(getLambdaColumn(WorkRepairReport::getCreateTime)));
        IPage<WorkRepairReport> iPage = workRepairReportMapper.selectPage(page, wrapper);
        return new Paging<WorkRepairReport>(iPage);
    }

}
