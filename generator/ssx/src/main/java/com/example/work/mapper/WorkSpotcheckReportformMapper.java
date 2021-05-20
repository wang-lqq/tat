package com.example.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.work.entity.WorkSpotcheckReportform;
import com.example.work.param.WorkSpotcheckReportformPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 *  Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-05-20
 */
@Repository
public interface WorkSpotcheckReportformMapper extends BaseMapper<WorkSpotcheckReportform> {


}
