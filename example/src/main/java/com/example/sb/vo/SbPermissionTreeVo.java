package com.example.sb.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文件夹权限树形列表VO
 * @author SSX_IT08112
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SbPermissionTreeVo对象", description = "文件夹权限树形列表")
public class SbPermissionTreeVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String title;
	
	private String field;
	
	private Integer parentId;
	
	public List<SbPermissionTreeVo> children;
	
	// 是否选中
	private boolean checked;
	
	// 是否展开
	private boolean spread;
	
	private boolean disabled;
	
}
