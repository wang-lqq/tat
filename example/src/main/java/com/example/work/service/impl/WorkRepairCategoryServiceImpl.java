package com.example.work.service.impl;

import com.example.work.entity.WorkRepairCategory;
import com.example.work.mapper.WorkRepairCategoryMapper;
import com.example.work.service.WorkRepairCategoryService;
import com.example.work.param.WorkRepairCategoryPageParam;
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
 * 报修类别表 服务实现类
 *
 * @author wanglonglong
 * @since 2021-02-20
 */
@Slf4j
@Service
public class WorkRepairCategoryServiceImpl extends BaseServiceImpl<WorkRepairCategoryMapper, WorkRepairCategory> implements WorkRepairCategoryService {

    @Autowired
    private WorkRepairCategoryMapper workRepairCategoryMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWorkRepairCategory(WorkRepairCategory workRepairCategory) throws Exception {
        return super.save(workRepairCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateWorkRepairCategory(WorkRepairCategory workRepairCategory) throws Exception {
        return super.updateById(workRepairCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWorkRepairCategory(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<WorkRepairCategory> getWorkRepairCategoryPageList(WorkRepairCategoryPageParam workRepairCategoryPageParam) throws Exception {
        Page<WorkRepairCategory> page = new PageInfo<>(workRepairCategoryPageParam, OrderItem.desc(getLambdaColumn(WorkRepairCategory::getCreateTime)));
        LambdaQueryWrapper<WorkRepairCategory> wrapper = new LambdaQueryWrapper<>();
        IPage<WorkRepairCategory> iPage = workRepairCategoryMapper.selectPage(page, wrapper);
        return new Paging<WorkRepairCategory>(iPage);
    }

}
