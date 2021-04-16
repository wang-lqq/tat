package com.example.sb.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.example.sb.entity.SbComputers;
import com.example.sb.param.SbComputersPageParam;

import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author wanglonglong
 * @since 2021-04-01
 */
public interface SbComputersService extends BaseService<SbComputers> {

    /**
     * 保存
     *
     * @param sbComputers
     * @return
     * @throws Exception
     */
    boolean saveSbComputers(SbComputers sbComputers) throws Exception;

    /**
     * 修改
     *
     * @param sbComputers
     * @return
     * @throws Exception
     */
    boolean updateSbComputers(SbComputers sbComputers) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSbComputers(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param sbComputersQueryParam
     * @return
     * @throws Exception
     */
    Paging<SbComputers> getSbComputersPageList(SbComputersPageParam sbComputersPageParam) throws Exception;

    boolean updateSbComputersPermission(List<Integer> permissionIds, Integer computersId) throws Exception;

	void setComputersSoftware(JSONObject jsonObject, Long computersId);

	void updateSoftware(JSONObject jsonObject, Long computersId);
}
