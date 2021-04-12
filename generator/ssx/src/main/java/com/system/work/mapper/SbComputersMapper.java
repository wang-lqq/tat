package com.system.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.work.entity.SbComputers;
import com.system.work.param.SbComputersPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 *  Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-04-07
 */
@Repository
public interface SbComputersMapper extends BaseMapper<SbComputers> {


}
