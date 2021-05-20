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
 * @since 2021-05-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WorkSpotcheckPlan对象")
public class WorkSpotcheckPlan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("设备id")
    private Integer productionEquipmentId;

    @ApiModelProperty("点检时间")
    private String spotCheckTime;

    @ApiModelProperty("点检周期")
    private String definingPrinciple;
    
    @ApiModelProperty("点检时间")
    private Date inspectionTime;

    @ApiModelProperty("点检状态：0，未点检；1，已点检")
    private Integer spotCheckStatus;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

}
