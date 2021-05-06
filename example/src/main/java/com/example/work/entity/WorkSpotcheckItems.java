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
 * @since 2021-05-05
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WorkSpotcheckItems对象")
public class WorkSpotcheckItems extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("设备id")
    private Integer productionEquipmentId;

    @ApiModelProperty("分类")
    private String categoryName;

    @ApiModelProperty("检查部位")
    private String examinationSite;

    @ApiModelProperty("检查内容")
    private String inspectionContent;

    @ApiModelProperty("检查方式")
    private String inspectionMethod;

    @ApiModelProperty("判定基准")
    private String definingPrinciple;

    @ApiModelProperty("点检周期")
    private String inspectionCycle;

    @ApiModelProperty("点检周期code")
    private String inspectionCycleCode;

    @ApiModelProperty("技术附件")
    private String technicalAnnex;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

}
