package com.system.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.work.entity.SysUser;
import com.system.work.param.SysUserPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 系统用户 Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-02-24
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {


}
