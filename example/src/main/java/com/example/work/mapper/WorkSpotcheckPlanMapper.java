package com.example.work.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.work.entity.WorkSpotcheckPlan;
import com.example.work.param.WorkSpotcheckPlanPageParam;
import com.example.work.vo.WorkSpotcheckPlanVo;

/**
 *  Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-05-06
 */
@Repository
public interface WorkSpotcheckPlanMapper extends BaseMapper<WorkSpotcheckPlan> {
	
	IPage<WorkSpotcheckPlanVo> getPlanPageList(@Param("page") Page page,
			@Param("param") WorkSpotcheckPlanPageParam workSpotcheckPlanPageParam,
			@Param("spotCheckTime") String  spotCheckTime,
			@Param("departmentId") Integer departmentId,
			@Param("assetCode") String assetCode,
			@Param("equipmentName") String equipmentName,
			@Param("machineNumber") String machineNumber);
}
