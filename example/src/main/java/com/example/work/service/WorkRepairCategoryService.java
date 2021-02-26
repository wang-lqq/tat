package com.example.work.service;

import com.example.work.entity.WorkRepairCategory;
import com.example.work.param.WorkRepairCategoryPageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 * 报修类别表 服务类
 *
 * @author wanglonglong
 * @since 2021-02-20
 */
public interface WorkRepairCategoryService extends BaseService<WorkRepairCategory> {

    /**
     * 保存
     *
     * @param workRepairCategory
     * @return
     * @throws Exception
     */
    boolean saveWorkRepairCategory(WorkRepairCategory workRepairCategory) throws Exception;

    /**
     * 修改
     *
     * @param workRepairCategory
     * @return
     * @throws Exception
     */
    boolean updateWorkRepairCategory(WorkRepairCategory workRepairCategory) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteWorkRepairCategory(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param workRepairCategoryQueryParam
     * @return
     * @throws Exception
     */
    Paging<WorkRepairCategory> getWorkRepairCategoryPageList(WorkRepairCategoryPageParam workRepairCategoryPageParam) throws Exception;

}
