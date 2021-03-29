package com.example.sb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sb.entity.SbComputer;
import com.example.sb.param.SbComputerPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 *  Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-03-29
 */
@Repository
public interface SbComputerMapper extends BaseMapper<SbComputer> {


}
