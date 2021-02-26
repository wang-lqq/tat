package com.system.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.geekidea.springbootplus.framework.core.pagination.BasePageOrderParam;

/**
 * <pre>
 * 系统用户 分页参数对象
 * </pre>
 *
 * @author wanglonglong
 * @date 2021-02-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统用户分页参数")
public class SysUserPageParam extends BasePageOrderParam {
    private static final long serialVersionUID = 1L;
}
