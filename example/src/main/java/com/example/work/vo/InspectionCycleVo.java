package com.example.work.vo;

import com.example.work.entity.WorkSpotcheckItems;

public class InspectionCycleVo extends WorkSpotcheckItems implements Comparable<InspectionCycleVo>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int sort;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Override
	public int compareTo(InspectionCycleVo o) {
		if(o.getSort() > this.getSort()) {
			return -1;
		}
		if(o.getSort() < this.getSort()) {
			return 1;
		}
		return 0;
	}
}
