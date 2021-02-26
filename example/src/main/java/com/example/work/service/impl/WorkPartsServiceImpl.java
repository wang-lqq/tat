package com.example.work.service.impl;

import com.example.work.entity.WorkParts;
import com.example.work.mapper.WorkPartsMapper;
import com.example.work.service.WorkPartsService;
import com.example.work.param.WorkPartsPageParam;
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
 * 配件表 服务实现类
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
@Slf4j
@Service
public class WorkPartsServiceImpl extends BaseServiceImpl<WorkPartsMapper, WorkParts> implements WorkPartsService {

    @Autowired
    private WorkPartsMapper workPartsMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWorkParts(WorkParts workParts) throws Exception {
        return super.save(workParts);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateWorkParts(WorkParts workParts) throws Exception {
        return super.updateById(workParts);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWorkParts(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<WorkParts> getWorkPartsPageList(WorkPartsPageParam workPartsPageParam) throws Exception {
        Page<WorkParts> page = new PageInfo<>(workPartsPageParam, OrderItem.desc(getLambdaColumn(WorkParts::getCreateTime)));
        LambdaQueryWrapper<WorkParts> wrapper = new LambdaQueryWrapper<>();
        IPage<WorkParts> iPage = workPartsMapper.selectPage(page, wrapper);
        return new Paging<WorkParts>(iPage);
    }

}
