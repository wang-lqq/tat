package com.example.work.service.impl;

import com.example.work.entity.WorkProductionEquipment;
import com.example.work.mapper.WorkProductionEquipmentMapper;
import com.example.work.service.WorkProductionEquipmentService;
import com.example.work.param.WorkProductionEquipmentPageParam;
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
        return super.save(workProductionEquipment);
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
        IPage<WorkProductionEquipment> iPage = workProductionEquipmentMapper.selectPage(page, wrapper);
        return new Paging<WorkProductionEquipment>(iPage);
    }

}
