package com.example.work.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.example.work.entity.WorkSpotcheckPlan;
import com.example.work.param.WorkSpotcheckPlanPageParam;

import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author wanglonglong
 * @since 2021-05-06
 */
public interface WorkSpotcheckPlanService extends BaseService<WorkSpotcheckPlan> {

    /**
     * 保存
     *
     * @param workSpotcheckPlan
     * @return
     * @throws Exception
     */
    boolean saveWorkSpotcheckPlan(WorkSpotcheckPlan workSpotcheckPlan) throws Exception;

    /**
     * 修改
     *
     * @param workSpotcheckPlan
     * @return
     * @throws Exception
     */
    boolean updateWorkSpotcheckPlan(WorkSpotcheckPlan workSpotcheckPlan) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteWorkSpotcheckPlan(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param workSpotcheckPlanQueryParam
     * @return
     * @throws Exception
     */
    Paging<WorkSpotcheckPlan> getWorkSpotcheckPlanPageList(WorkSpotcheckPlanPageParam workSpotcheckPlanPageParam) throws Exception;

    List<JSONObject> getDate(JSONObject jsonObject);

	boolean addList(JSONObject jsonObject);
}
