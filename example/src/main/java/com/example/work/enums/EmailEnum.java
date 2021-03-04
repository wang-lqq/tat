package com.example.work.enums;

public enum EmailEnum {
	
	REPORT_EXAMINE("report_examine", "报修工单审核", "templateReport"),
	REPORT_ORDER("repair", "报修工单联络", "templateRepair"),
	REPORT_COMPLETE("report_examine", "报修工单处理完成提醒", "complete");
	
	// 邮件接受者角色编码
	private String roleCode;
	// 主题
    private String subject;
    // 模版
    private String template;
    
	private EmailEnum(String roleCode, String subject, String template) {
		this.roleCode = roleCode;
		this.subject = subject;
		this.template = template;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
}
