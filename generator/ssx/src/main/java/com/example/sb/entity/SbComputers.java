package com.example.sb.entity;

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
 * @since 2021-04-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SbComputers对象")
public class SbComputers extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("mac地址")
    private String mac;

    @ApiModelProperty("主机型号")
    private String hostModel;

    @ApiModelProperty("OA管理编码")
    private String oaManagementCode;

    @ApiModelProperty("始用日期")
    private Date startDate;

    @ApiModelProperty("部门id")
    private Integer departmentId;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("姓名")
    private String fullName;

    @ApiModelProperty("ip地址")
    private String ipAddress;

    @ApiModelProperty("域用户名")
    private String domainUsername;

    @ApiModelProperty("计算机名")
    private String computerName;

    @ApiModelProperty("新资产编码")
    private String newAssetcode;

    @ApiModelProperty("操作系统")
    private String operatingSystem;

    @ApiModelProperty("管理员密码")
    private String administratorPassword;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("状态：0，默认；-1，报废；1，已流转")
    private Integer status;

    @ApiModelProperty("流转时间")
    private Date circulationTime;

}
