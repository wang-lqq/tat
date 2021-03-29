package com.example.sb.service.impl;

import com.example.sb.entity.SbComputer;
import com.example.sb.mapper.SbComputerMapper;
import com.example.sb.service.SbComputerService;
import com.example.sb.param.SbComputerPageParam;
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
 * @since 2021-03-29
 */
@Slf4j
@Service
public class SbComputerServiceImpl extends BaseServiceImpl<SbComputerMapper, SbComputer> implements SbComputerService {

    @Autowired
    private SbComputerMapper sbComputerMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSbComputer(SbComputer sbComputer) throws Exception {
        return super.save(sbComputer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSbComputer(SbComputer sbComputer) throws Exception {
        return super.updateById(sbComputer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSbComputer(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<SbComputer> getSbComputerPageList(SbComputerPageParam sbComputerPageParam) throws Exception {
        Page<SbComputer> page = new PageInfo<>(sbComputerPageParam, OrderItem.desc(getLambdaColumn(SbComputer::getCreateTime)));
        LambdaQueryWrapper<SbComputer> wrapper = new LambdaQueryWrapper<>();
        IPage<SbComputer> iPage = sbComputerMapper.selectPage(page, wrapper);
        return new Paging<SbComputer>(iPage);
    }

}
