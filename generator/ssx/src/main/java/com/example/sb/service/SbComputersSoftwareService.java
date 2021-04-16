package com.example.sb.service;

import com.example.sb.entity.SbComputersSoftware;
import com.example.sb.param.SbComputersSoftwarePageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author wanglonglong
 * @since 2021-04-12
 */
public interface SbComputersSoftwareService extends BaseService<SbComputersSoftware> {

    /**
     * 保存
     *
     * @param sbComputersSoftware
     * @return
     * @throws Exception
     */
    boolean saveSbComputersSoftware(SbComputersSoftware sbComputersSoftware) throws Exception;

    /**
     * 修改
     *
     * @param sbComputersSoftware
     * @return
     * @throws Exception
     */
    boolean updateSbComputersSoftware(SbComputersSoftware sbComputersSoftware) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSbComputersSoftware(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param sbComputersSoftwareQueryParam
     * @return
     * @throws Exception
     */
    Paging<SbComputersSoftware> getSbComputersSoftwarePageList(SbComputersSoftwarePageParam sbComputersSoftwarePageParam) throws Exception;

}
