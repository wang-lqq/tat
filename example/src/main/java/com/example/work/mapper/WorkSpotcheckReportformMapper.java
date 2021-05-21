package com.example.work.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.work.entity.WorkSpotcheckReportform;
import com.example.work.param.WorkSpotcheckReportformPageParam;
import com.example.work.vo.WorkSpotcheckReportformVo;
@Repository
public interface WorkSpotcheckReportformMapper extends BaseMapper<WorkSpotcheckReportform> {

	IPage<WorkSpotcheckReportformVo> getReportFormPageList(@Param("page") Page page,
			@Param("param") WorkSpotcheckReportformPageParam workSpotcheckReportformPageParam,
			@Param("departmentId") Integer departmentId,
			@Param("assetCode") String assetCode,
			@Param("equipmentName") String equipmentName,
			@Param("machineNumber") String machineNumber);
}
