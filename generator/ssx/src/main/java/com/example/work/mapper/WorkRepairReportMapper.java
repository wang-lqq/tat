package com.example.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.work.entity.WorkRepairReport;
import com.example.work.param.WorkRepairReportPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 联络-维修单表 Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-03-01
 */
@Repository
public interface WorkRepairReportMapper extends BaseMapper<WorkRepairReport> {


}
