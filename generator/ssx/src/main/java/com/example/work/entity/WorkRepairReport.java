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
 * 联络-维修单表
 *
 * @author wanglonglong
 * @since 2021-04-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WorkRepairReport对象")
public class WorkRepairReport extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("工单号")
    private String workOrderNo;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("姓名")
    private String fullName;

    @ApiModelProperty("电话")
    private String telephone;

    @ApiModelProperty("申请人所在部门id")
    private Integer departmentId;

    @ApiModelProperty("申请人所在部门")
    private String departmentName;

    @ApiModelProperty("希望报修完成时间")
    private Date completionTime;

    @ApiModelProperty("生产设备id")
    private Integer productionEquipmentId;

    @ApiModelProperty("类别：A，B，C，D，E")
    private String category;

    @ApiModelProperty("设备名称")
    private String equipmentName;

    @ApiModelProperty("机械编号")
    private String machineNumber;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("设备资产编码")
    private String assetCode;

    @ApiModelProperty("报修问题及要求")
    private String repairProblems;

    @ApiModelProperty("报修审核人用户id")
    private Integer repairAuditUserId;

    @ApiModelProperty("报修审核人姓名")
    private String repairAuditFullName;

    @ApiModelProperty("报修审核时间")
    private Date repairAuditTime;

    @ApiModelProperty("维修担当")
    private String maintenanceResponsibility;

    @ApiModelProperty("社内/社外：0，社内；社外，1")
    private Integer agency;

    @ApiModelProperty("维修完成时间")
    private Date repairCompletionTime;

    @ApiModelProperty("报修问题点核实描述")
    private String problemVerification;

    @ApiModelProperty("维修总费用")
    private Double totalCost;

    @ApiModelProperty("维修说明及后期改善")
    private String explainImprove;

    @ApiModelProperty("现场服务人员")
    private String servicePersonal;

    @ApiModelProperty("报修部门核实人")
    private String departmentVerifier;

    @ApiModelProperty("维修部门长审核用户id")
    private Integer repairExamineUserId;

    @ApiModelProperty("维修部门审核人姓名")
    private String repairExamineFullName;

    @ApiModelProperty("维修部门长审核时间")
    private Date repairExamineTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("工单进度%")
    private String progress;

    @ApiModelProperty("报修问题图片")
    private String repairPicture;

    @ApiModelProperty("报修完成效果图片")
    private String completePicture;

    @ApiModelProperty("图片附件")
    private String enclosure;

    @ApiModelProperty("报修提交人用户id")
    private Integer submitterUserId;

}
