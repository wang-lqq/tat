package io.geekidea.springbootplus.system.entity;

import java.util.List;

import lombok.Data;
@Data
public class SysPermissionVos {
	
	private String name;
	
	private String title;
	
	private String icon;
	
	private String jump;
	
	private Long id;
	
	private Long parentId;
	
	private List<SysPermissionVos> list;
}
