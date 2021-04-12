package com.example.sb.service.impl;

import com.example.sb.entity.SbComputersPermission;
import com.example.sb.mapper.SbComputersPermissionMapper;
import com.example.sb.service.SbComputersPermissionService;
import com.example.sb.param.SbComputersPermissionPageParam;
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
 * @since 2021-04-08
 */
@Slf4j
@Service
public class SbComputersPermissionServiceImpl extends BaseServiceImpl<SbComputersPermissionMapper, SbComputersPermission> implements SbComputersPermissionService {

    @Autowired
    private SbComputersPermissionMapper sbComputersPermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSbComputersPermission(SbComputersPermission sbComputersPermission) throws Exception {
        return super.save(sbComputersPermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSbComputersPermission(SbComputersPermission sbComputersPermission) throws Exception {
        return super.updateById(sbComputersPermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSbComputersPermission(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<SbComputersPermission> getSbComputersPermissionPageList(SbComputersPermissionPageParam sbComputersPermissionPageParam) throws Exception {
        Page<SbComputersPermission> page = new PageInfo<>(sbComputersPermissionPageParam, OrderItem.desc(getLambdaColumn(SbComputersPermission::getCreateTime)));
        LambdaQueryWrapper<SbComputersPermission> wrapper = new LambdaQueryWrapper<>();
        IPage<SbComputersPermission> iPage = sbComputersPermissionMapper.selectPage(page, wrapper);
        return new Paging<SbComputersPermission>(iPage);
    }

}
