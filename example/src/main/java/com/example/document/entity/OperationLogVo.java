package com.example.document.entity;

import io.geekidea.springbootplus.framework.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OperationLogVo对象")
public class OperationLogVo extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String path;
	
	private Integer visits;
	
	private String visitTime;
}
