package com.example.work.service;

import com.example.work.entity.WorkSpotcheckItems;
import com.example.work.param.WorkSpotcheckItemsPageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author wanglonglong
 * @since 2021-05-05
 */
public interface WorkSpotcheckItemsService extends BaseService<WorkSpotcheckItems> {

    /**
     * 保存
     *
     * @param workSpotcheckItems
     * @return
     * @throws Exception
     */
    boolean saveWorkSpotcheckItems(WorkSpotcheckItems workSpotcheckItems) throws Exception;

    /**
     * 修改
     *
     * @param workSpotcheckItems
     * @return
     * @throws Exception
     */
    boolean updateWorkSpotcheckItems(WorkSpotcheckItems workSpotcheckItems) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteWorkSpotcheckItems(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param workSpotcheckItemsQueryParam
     * @return
     * @throws Exception
     */
    Paging<WorkSpotcheckItems> getWorkSpotcheckItemsPageList(WorkSpotcheckItemsPageParam workSpotcheckItemsPageParam) throws Exception;

}
