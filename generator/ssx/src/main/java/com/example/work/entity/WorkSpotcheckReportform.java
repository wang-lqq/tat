package com.example.work.entity;

import io.geekidea.springbootplus.framework.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;

/**
 * 
 *
 * @author wanglonglong
 * @since 2021-05-20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WorkSpotcheckReportform对象")
public class WorkSpotcheckReportform extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("设备id")
    private Integer productionEquipmentId;

    @ApiModelProperty("日检计划id")
    private Integer daySpotcheckPlanId;

    @ApiModelProperty("点检状态：0，未点检；1，点检中，已点检")
    private Integer daySpotCheckStatus;

    @ApiModelProperty("点检时间")
    private Date dayInspectionTime;

    @ApiModelProperty("周检计划id")
    private Integer weekSpotcheckPlanId;

    @ApiModelProperty("点检状态")
    private Integer weekSpotCheckStatus;

    @ApiModelProperty("点检时间")
    private Date weekInspectionTime;

    @ApiModelProperty("月检计划id")
    private Integer monthSpotcheckPlanId;

    @ApiModelProperty("点检状态")
    private Integer monthSpotCheckStatus;

    @ApiModelProperty("点检时间")
    private Date monthInspectionTime;

    @ApiModelProperty("季度检计划id")
    private Integer quarterSpotcheckPlanId;

    @ApiModelProperty("点检状态")
    private Integer quarterSpotCheckStatus;

    @ApiModelProperty("点检时间")
    private Date quarterInspectionTime;

    @ApiModelProperty("半年检计划id")
    private Integer halfyearSpotcheckPlanId;

    @ApiModelProperty("点检状态")
    private Integer halfyearSpotCheckStatus;

    @ApiModelProperty("点检时间")
    private Date halfyearInspectionTime;

    @ApiModelProperty("年检计划id")
    private Integer yearSpotcheckPlanId;

    @ApiModelProperty("点检状态")
    private Integer yearSpotCheckStatus;

    @ApiModelProperty("点检时间")
    private Date yearInspectionTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("0：无该点检项目；1：有该点检项目")
    private Integer daySpotcheckItems;

    private Integer weekSpotcheckItems;

    private Integer monthSpotcheckItems;

    private Integer quarterSpotcheckItems;

    private Integer halfyearSpotcheckItems;

    private Integer yearSpotcheckItems;

}
