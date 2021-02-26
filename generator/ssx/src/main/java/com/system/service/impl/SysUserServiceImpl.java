package com.system.service.impl;

import com.system.entity.SysUser;
import com.system.mapper.SysUserMapper;
import com.system.service.SysUserService;
import com.system.param.SysUserPageParam;
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
 * 系统用户 服务实现类
 *
 * @author wanglonglong
 * @since 2021-02-24
 */
@Slf4j
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysUser(SysUser sysUser) throws Exception {
        return super.save(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysUser(SysUser sysUser) throws Exception {
        return super.updateById(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysUser(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<SysUser> getSysUserPageList(SysUserPageParam sysUserPageParam) throws Exception {
        Page<SysUser> page = new PageInfo<>(sysUserPageParam, OrderItem.desc(getLambdaColumn(SysUser::getCreateTime)));
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        IPage<SysUser> iPage = sysUserMapper.selectPage(page, wrapper);
        return new Paging<SysUser>(iPage);
    }

}
