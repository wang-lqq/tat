package com.example.work.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.geekidea.springbootplus.framework.core.pagination.BasePageOrderParam;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author wanglonglong
 * @date 2021-05-20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class WorkSpotcheckReportformPageParam extends BasePageOrderParam {
    private static final long serialVersionUID = 1L;
}
