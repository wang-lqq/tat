package com.example.work.service;

import com.example.work.entity.WorkParts;
import com.example.work.param.WorkPartsPageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 * 配件表 服务类
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
public interface WorkPartsService extends BaseService<WorkParts> {

    /**
     * 保存
     *
     * @param workParts
     * @return
     * @throws Exception
     */
    boolean saveWorkParts(WorkParts workParts) throws Exception;

    /**
     * 修改
     *
     * @param workParts
     * @return
     * @throws Exception
     */
    boolean updateWorkParts(WorkParts workParts) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteWorkParts(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param workPartsQueryParam
     * @return
     * @throws Exception
     */
    Paging<WorkParts> getWorkPartsPageList(WorkPartsPageParam workPartsPageParam) throws Exception;

}
