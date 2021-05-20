package com.example.work.enums;

public enum CycleCodeEnum {
	DAY_CODE("日", "年季度月周日"),
	WEEK_CODE("周", "年季度月周"),
	MONTH_CODE("月", "年季度月"),
	QUARTER_CODE("季度", "年季度"),
	YEAR_CODE("年", "年");
	
	private String cycle;
    private String cycleCode;
    
    public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getCycleCode() {
		return cycleCode;
	}

	public void setCycleCode(String cycleCode) {
		this.cycleCode = cycleCode;
	}

	private CycleCodeEnum(String cycle, String cycleCode) {
		this.cycle = cycle;
		this.cycleCode = cycleCode;
	}

	public static String getValue(String cycle){
        for (CycleCodeEnum cycleCodeEnum: values()) {
            if(cycleCodeEnum.getCycle().equals(cycle)){
                return  cycleCodeEnum.getCycleCode();
            }
        }
        return "";
    }
}
