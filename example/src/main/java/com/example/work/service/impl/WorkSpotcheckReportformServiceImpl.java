package com.example.work.service.impl;

import com.example.work.entity.WorkSpotcheckReportform;
import com.example.work.mapper.WorkSpotcheckReportformMapper;
import com.example.work.service.WorkSpotcheckReportformService;
import com.example.work.param.WorkSpotcheckReportformPageParam;
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

}
