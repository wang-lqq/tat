package com.example.sb.service.impl;

import com.example.sb.entity.SbComputersSoftware;
import com.example.sb.mapper.SbComputersSoftwareMapper;
import com.example.sb.service.SbComputersSoftwareService;
import com.example.sb.param.SbComputersSoftwarePageParam;
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
 * @since 2021-04-12
 */
@Slf4j
@Service
public class SbComputersSoftwareServiceImpl extends BaseServiceImpl<SbComputersSoftwareMapper, SbComputersSoftware> implements SbComputersSoftwareService {

    @Autowired
    private SbComputersSoftwareMapper sbComputersSoftwareMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSbComputersSoftware(SbComputersSoftware sbComputersSoftware) throws Exception {
        return super.save(sbComputersSoftware);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSbComputersSoftware(SbComputersSoftware sbComputersSoftware) throws Exception {
        return super.updateById(sbComputersSoftware);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSbComputersSoftware(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<SbComputersSoftware> getSbComputersSoftwarePageList(SbComputersSoftwarePageParam sbComputersSoftwarePageParam) throws Exception {
        Page<SbComputersSoftware> page = new PageInfo<>(sbComputersSoftwarePageParam, OrderItem.desc(getLambdaColumn(SbComputersSoftware::getCreateTime)));
        LambdaQueryWrapper<SbComputersSoftware> wrapper = new LambdaQueryWrapper<>();
        IPage<SbComputersSoftware> iPage = sbComputersSoftwareMapper.selectPage(page, wrapper);
        return new Paging<SbComputersSoftware>(iPage);
    }

}
