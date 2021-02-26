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
 * 配件表
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WorkParts对象")
public class WorkParts extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("配件名称")
    private String accessoryName;

    @ApiModelProperty("物料代码")
    private String materialCode;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("规格")
    private String specifications;

    @ApiModelProperty("单位")
    private String company;

    @ApiModelProperty("单价")
    private Double unitPrice;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

}
