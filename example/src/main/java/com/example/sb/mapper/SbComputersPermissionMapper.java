package com.example.sb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sb.entity.SbComputersPermission;
import com.example.sb.param.SbComputersPermissionPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 *  Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-04-08
 */
@Repository
public interface SbComputersPermissionMapper extends BaseMapper<SbComputersPermission> {


}
