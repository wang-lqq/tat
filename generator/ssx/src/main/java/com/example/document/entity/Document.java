package com.example.document.entity;

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
 * 文档
 *
 * @author wanglonglong
 * @since 2021-01-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Document对象")
public class Document extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("类别id")
    private Integer categoryId;

    @ApiModelProperty("类别名称")
    private String categoryName;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("红点")
    private Integer redDot;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("红点是否系统更改")
    private Integer redDotSystem;

}
