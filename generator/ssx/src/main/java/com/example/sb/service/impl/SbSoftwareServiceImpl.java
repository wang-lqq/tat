package com.example.sb.service.impl;

import com.example.sb.entity.SbSoftware;
import com.example.sb.mapper.SbSoftwareMapper;
import com.example.sb.service.SbSoftwareService;
import com.example.sb.param.SbSoftwarePageParam;
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
public class SbSoftwareServiceImpl extends BaseServiceImpl<SbSoftwareMapper, SbSoftware> implements SbSoftwareService {

    @Autowired
    private SbSoftwareMapper sbSoftwareMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSbSoftware(SbSoftware sbSoftware) throws Exception {
        return super.save(sbSoftware);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSbSoftware(SbSoftware sbSoftware) throws Exception {
        return super.updateById(sbSoftware);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSbSoftware(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<SbSoftware> getSbSoftwarePageList(SbSoftwarePageParam sbSoftwarePageParam) throws Exception {
        Page<SbSoftware> page = new PageInfo<>(sbSoftwarePageParam, OrderItem.desc(getLambdaColumn(SbSoftware::getCreateTime)));
        LambdaQueryWrapper<SbSoftware> wrapper = new LambdaQueryWrapper<>();
        IPage<SbSoftware> iPage = sbSoftwareMapper.selectPage(page, wrapper);
        return new Paging<SbSoftware>(iPage);
    }

}
