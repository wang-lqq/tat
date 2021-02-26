package com.work.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.work.entity.WorkRepairParts;
import com.work.work.param.WorkRepairPartsPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 维修配件表 Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
@Repository
public interface WorkRepairPartsMapper extends BaseMapper<WorkRepairParts> {


}
