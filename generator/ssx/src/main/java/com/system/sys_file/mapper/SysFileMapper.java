package com.system.sys_file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.sys_file.entity.SysFile;
import com.system.sys_file.param.SysFilePageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 *  Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-01-20
 */
@Repository
public interface SysFileMapper extends BaseMapper<SysFile> {


}
