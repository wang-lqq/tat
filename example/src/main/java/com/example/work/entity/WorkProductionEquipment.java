package com.example.work.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.geekidea.springbootplus.framework.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 设备表
 *
 * @author wanglonglong
 * @since 2021-05-04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WorkProductionEquipment对象")
public class WorkProductionEquipment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("设备id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("归属部门id")
    private Integer departmentId;

    @ApiModelProperty("归属部门名称")
    private String departmentName;
    
    @ApiModelProperty("组织结构图中组别名称")
    private String groupName;

    @ApiModelProperty("实施担当")
    private String toBear;
}
