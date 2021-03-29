package com.example.work.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.geekidea.springbootplus.framework.core.pagination.BasePageOrderParam;

/**
 * <pre>
 * 配件表 分页参数对象
 * </pre>
 *
 * @author wanglonglong
 * @date 2021-03-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "配件表分页参数")
public class WorkPartsPageParam extends BasePageOrderParam {
    private static final long serialVersionUID = 1L;
}
