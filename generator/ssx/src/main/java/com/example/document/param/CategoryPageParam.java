package com.example.document.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.geekidea.springbootplus.framework.core.pagination.BasePageOrderParam;

/**
 * <pre>
 * 类别 分页参数对象
 * </pre>
 *
 * @author wanglonglong
 * @date 2021-01-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "类别分页参数")
public class CategoryPageParam extends BasePageOrderParam {
    private static final long serialVersionUID = 1L;
}
