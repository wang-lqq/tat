package com.example.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.work.entity.WorkSpotcheckPlan;
import com.example.work.param.WorkSpotcheckPlanPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 *  Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-05-06
 */
@Repository
public interface WorkSpotcheckPlanMapper extends BaseMapper<WorkSpotcheckPlan> {


}
