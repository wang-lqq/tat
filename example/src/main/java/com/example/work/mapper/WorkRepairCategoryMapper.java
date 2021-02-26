package com.example.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.work.entity.WorkRepairCategory;
import com.example.work.param.WorkRepairCategoryPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 报修类别表 Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-02-20
 */
@Repository
public interface WorkRepairCategoryMapper extends BaseMapper<WorkRepairCategory> {


}
