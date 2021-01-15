package io.geekidea.springbootplus.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysPermissionVo extends SysPermission{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String title;
	
	private boolean checked;
	
	private boolean spread = true;
	
	private String field;
	
	private boolean disabled;
	
}
