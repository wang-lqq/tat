package com.example.sysfile.service.impl;

import com.example.sysfile.entity.SysFile;
import com.example.sysfile.mapper.SysFileMapper;
import com.example.sysfile.service.SysFileService;
import com.example.sysfile.param.SysFilePageParam;
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
 * @since 2021-01-25
 */
@Slf4j
@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    @Autowired
    private SysFileMapper sysFileMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysFile(SysFile sysFile) throws Exception {
        return super.save(sysFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysFile(SysFile sysFile) throws Exception {
        return super.updateById(sysFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysFile(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<SysFile> getSysFilePageList(SysFilePageParam sysFilePageParam) throws Exception {
        Page<SysFile> page = new PageInfo<>(sysFilePageParam, OrderItem.desc(getLambdaColumn(SysFile::getCreateTime)));
        LambdaQueryWrapper<SysFile> wrapper = new LambdaQueryWrapper<>();
        IPage<SysFile> iPage = sysFileMapper.selectPage(page, wrapper);
        return new Paging<SysFile>(iPage);
    }

}
