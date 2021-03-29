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
 * @since 2021-03-29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SbComputer对象")
public class SbComputer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("mac地址")
    private String mac;

    @ApiModelProperty("主机型号")
    private String hostModel;

    @ApiModelProperty("OA管理编码")
    private String oaManagementCode;

    @ApiModelProperty("始用日期")
    private Date startDate;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

}
