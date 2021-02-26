package com.example.work.service;

import com.example.work.entity.WorkRepairParts;
import com.example.work.param.WorkRepairPartsPageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 * 维修配件表 服务类
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
public interface WorkRepairPartsService extends BaseService<WorkRepairParts> {

    /**
     * 保存
     *
     * @param workRepairParts
     * @return
     * @throws Exception
     */
    boolean saveWorkRepairParts(WorkRepairParts workRepairParts) throws Exception;

    /**
     * 修改
     *
     * @param workRepairParts
     * @return
     * @throws Exception
     */
    boolean updateWorkRepairParts(WorkRepairParts workRepairParts) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteWorkRepairParts(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param workRepairPartsQueryParam
     * @return
     * @throws Exception
     */
    Paging<WorkRepairParts> getWorkRepairPartsPageList(WorkRepairPartsPageParam workRepairPartsPageParam) throws Exception;

}
