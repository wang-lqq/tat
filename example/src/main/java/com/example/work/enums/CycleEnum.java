package com.example.work.enums;

public enum CycleEnum {
	DAY(1, "日"),
	WEEK(2, "周"),
	MONTH(3, "月"),
	QUARTER(4, "季度"),
	YEAR(5, "年");
	
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
	private CycleEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
    public static int getValue(String msg){
        for (CycleEnum  color: values()) {
            if(color.getMsg().equals(msg)){
                return  color.getCode();
            }
        }
        return 0;

    }
}
