package com.example.sb.service.impl;

import com.example.sb.entity.SbComputers;
import com.example.sb.mapper.SbComputersMapper;
import com.example.sb.service.SbComputersService;
import com.example.sb.param.SbComputersPageParam;
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
 * @since 2021-04-19
 */
@Slf4j
@Service
public class SbComputersServiceImpl extends BaseServiceImpl<SbComputersMapper, SbComputers> implements SbComputersService {

    @Autowired
    private SbComputersMapper sbComputersMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSbComputers(SbComputers sbComputers) throws Exception {
        return super.save(sbComputers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSbComputers(SbComputers sbComputers) throws Exception {
        return super.updateById(sbComputers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSbComputers(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<SbComputers> getSbComputersPageList(SbComputersPageParam sbComputersPageParam) throws Exception {
        Page<SbComputers> page = new PageInfo<>(sbComputersPageParam, OrderItem.desc(getLambdaColumn(SbComputers::getCreateTime)));
        LambdaQueryWrapper<SbComputers> wrapper = new LambdaQueryWrapper<>();
        IPage<SbComputers> iPage = sbComputersMapper.selectPage(page, wrapper);
        return new Paging<SbComputers>(iPage);
    }

}
