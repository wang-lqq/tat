package com.example.work.service;

import com.example.work.entity.WorkRepairReport;
import com.example.work.param.WorkRepairReportPageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 * 联络-维修单表 服务类
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
public interface WorkRepairReportService extends BaseService<WorkRepairReport> {

    /**
     * 保存
     *
     * @param workRepairReport
     * @return
     * @throws Exception
     */
    boolean saveWorkRepairReport(WorkRepairReport workRepairReport) throws Exception;

    /**
     * 修改
     *
     * @param workRepairReport
     * @return
     * @throws Exception
     */
    boolean updateWorkRepairReport(WorkRepairReport workRepairReport) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteWorkRepairReport(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param workRepairReportQueryParam
     * @return
     * @throws Exception
     */
    Paging<WorkRepairReport> getWorkRepairReportPageList(WorkRepairReportPageParam workRepairReportPageParam) throws Exception;

}
