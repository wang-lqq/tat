package com.example.work.service;

import com.example.work.entity.WorkSpotcheckReportform;
import com.example.work.param.WorkSpotcheckReportformPageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author wanglonglong
 * @since 2021-05-20
 */
public interface WorkSpotcheckReportformService extends BaseService<WorkSpotcheckReportform> {

    /**
     * 保存
     *
     * @param workSpotcheckReportform
     * @return
     * @throws Exception
     */
    boolean saveWorkSpotcheckReportform(WorkSpotcheckReportform workSpotcheckReportform) throws Exception;

    /**
     * 修改
     *
     * @param workSpotcheckReportform
     * @return
     * @throws Exception
     */
    boolean updateWorkSpotcheckReportform(WorkSpotcheckReportform workSpotcheckReportform) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteWorkSpotcheckReportform(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param workSpotcheckReportformQueryParam
     * @return
     * @throws Exception
     */
    Paging<WorkSpotcheckReportform> getWorkSpotcheckReportformPageList(WorkSpotcheckReportformPageParam workSpotcheckReportformPageParam) throws Exception;

}
