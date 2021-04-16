package com.example.sb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sb.entity.SbComputersSoftware;
import com.example.sb.param.SbComputersSoftwarePageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 *  Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-04-12
 */
@Repository
public interface SbComputersSoftwareMapper extends BaseMapper<SbComputersSoftware> {


}
