package com.example.work.vo;

import java.util.Date;

import com.example.work.entity.WorkProductionEquipment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WorkSpotcheckReportformVo对象")
public class WorkSpotcheckReportformVo extends WorkProductionEquipment{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Integer id;

    private Integer productionEquipmentId;

    private Integer daySpotcheckPlanId;

    private Integer daySpotCheckStatus;

    private Date dayInspectionTime;

    private Integer weekSpotcheckPlanId;

    private Integer weekSpotCheckStatus;

    private Date weekInspectionTime;

    private Integer monthSpotcheckPlanId;

    private Integer monthSpotCheckStatus;

    private Date monthInspectionTime;

    private Integer quarterSpotcheckPlanId;

    private Integer quarterSpotCheckStatus;

    private Date quarterInspectionTime;

    private Integer halfyearSpotcheckPlanId;

    private Integer halfyearSpotCheckStatus;

    private Date halfyearInspectionTime;

    private Integer yearSpotcheckPlanId;

    private Integer yearSpotCheckStatus;

    private Date yearInspectionTime;

    private Integer daySpotcheckItems;
    
    private Integer weekSpotcheckItems;
    
    private Integer monthSpotcheckItems;
    
    private Integer quarterSpotcheckItems;
    
    private Integer halfyearSpotcheckItems;
    
    private Integer yearSpotcheckItems;
    
    private Date createTime;
    
    private Date updateTime;
}
