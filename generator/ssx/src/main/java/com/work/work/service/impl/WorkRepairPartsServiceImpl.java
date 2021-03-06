package com.work.work.service.impl;

import com.work.work.entity.WorkRepairParts;
import com.work.work.mapper.WorkRepairPartsMapper;
import com.work.work.service.WorkRepairPartsService;
import com.work.work.param.WorkRepairPartsPageParam;
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
        Page<WorkRepairParts> page = new PageInfo<>(workRepairPartsPageParam, OrderItem.desc(getLambdaColumn(WorkRepairParts::getCreateTime)));
        LambdaQueryWrapper<WorkRepairParts> wrapper = new LambdaQueryWrapper<>();
        IPage<WorkRepairParts> iPage = workRepairPartsMapper.selectPage(page, wrapper);
        return new Paging<WorkRepairParts>(iPage);
    }

}
