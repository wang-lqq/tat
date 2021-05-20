package com.example.work.vo;

import java.util.Date;

import com.example.work.entity.WorkProductionEquipment;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WorkSpotcheckPlanVo对象")
public class WorkSpotcheckPlanVo extends WorkProductionEquipment{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Integer id;

    private Integer productionEquipmentId;

    private String spotCheckTime;

    private String definingPrinciple;
    
    private Date inspectionTime;

    private Integer spotCheckStatus;

    private Date createTime;

    private Date updateTime;

}
