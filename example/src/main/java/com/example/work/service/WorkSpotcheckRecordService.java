package com.example.work.service;

import com.alibaba.fastjson.JSONObject;
import com.example.work.entity.WorkSpotcheckRecord;
import com.example.work.param.WorkSpotcheckRecordPageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author wanglonglong
 * @since 2021-05-13
 */
public interface WorkSpotcheckRecordService extends BaseService<WorkSpotcheckRecord> {

    /**
     * 保存
     *
     * @param workSpotcheckRecord
     * @return
     * @throws Exception
     */
    boolean saveWorkSpotcheckRecord(WorkSpotcheckRecord workSpotcheckRecord) throws Exception;

    /**
     * 修改
     *
     * @param workSpotcheckRecord
     * @return
     * @throws Exception
     */
    boolean updateWorkSpotcheckRecord(WorkSpotcheckRecord workSpotcheckRecord) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteWorkSpotcheckRecord(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param workSpotcheckRecordQueryParam
     * @return
     * @throws Exception
     */
    Paging<WorkSpotcheckRecord> getWorkSpotcheckRecordPageList(WorkSpotcheckRecordPageParam workSpotcheckRecordPageParam) throws Exception;

	boolean addList(JSONObject jsonObject);

}
