package com.example.work.enums;

public enum StatusEnum {
	REFUSE(-1, "审核拒绝"),
	WAIT_FOR(0, "待审核"),
	AGREE(1, "同意审核"),
	UNDER_REPAIR(2, "维修中"),
	REPAIR_COMPLETE(3, "维修完成"),
	COMPLETE_AGREE(4, "维修审核");
	
	private Integer code;
	
    private String msg;
    
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private StatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
