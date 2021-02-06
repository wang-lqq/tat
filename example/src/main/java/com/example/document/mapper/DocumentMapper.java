package com.example.document.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.document.entity.Document;
import com.example.document.entity.OperationLogVo;

/**
 * 文档 Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-01-01
 */
@Repository
public interface DocumentMapper extends BaseMapper<Document> {

	List<OperationLogVo> getVisit(@Param("path") String path,@Param("startTime") String startTime, @Param("endTime") String endTime);
	
	List<OperationLogVo> getVisitCount(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	List<OperationLogVo> getForward(@Param("startTime") String startTime,@Param("endTime") String endTime);
}
