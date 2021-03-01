package com.example.work.service.impl;

import com.example.work.entity.WorkRepairReport;
import com.example.work.mapper.WorkRepairReportMapper;
import com.example.work.service.WorkRepairReportService;
import com.example.work.param.WorkRepairReportPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 联络-维修单表 服务实现类
 *
 * @author wanglonglong
 * @since 2021-03-01
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
        Page<WorkRepairReport> page = new PageInfo<>(workRepairReportPageParam, OrderItem.desc(getLambdaColumn(WorkRepairReport::getCreateTime)));
        LambdaQueryWrapper<WorkRepairReport> wrapper = new LambdaQueryWrapper<>();
        IPage<WorkRepairReport> iPage = workRepairReportMapper.selectPage(page, wrapper);
        return new Paging<WorkRepairReport>(iPage);
    }

}
