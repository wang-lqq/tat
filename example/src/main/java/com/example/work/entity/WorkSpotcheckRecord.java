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
 * @since 2021-05-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WorkSpotcheckRecord对象")
public class WorkSpotcheckRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("设备id")
    private Integer productionEquipmentId;

    @ApiModelProperty("点检计划id")
    private Integer spotcheckPlanId;

    @ApiModelProperty("点检资料id")
    private Integer spotcheckItemsId;

    @ApiModelProperty("判定：0，×；1，√")
    private Integer determine;

    @ApiModelProperty("改善及处理对策")
    private String improve;

    @ApiModelProperty("实施资料")
    private String implementationInformation;

    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

}
