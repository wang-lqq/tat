package com.example.work.service.impl;

import com.example.work.entity.WorkSpotcheckPlan;
import com.example.work.mapper.WorkSpotcheckPlanMapper;
import com.example.work.service.WorkSpotcheckPlanService;
import com.example.work.param.WorkSpotcheckPlanPageParam;
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
 * @since 2021-05-06
 */
@Slf4j
@Service
public class WorkSpotcheckPlanServiceImpl extends BaseServiceImpl<WorkSpotcheckPlanMapper, WorkSpotcheckPlan> implements WorkSpotcheckPlanService {

    @Autowired
    private WorkSpotcheckPlanMapper workSpotcheckPlanMapper;

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

}
