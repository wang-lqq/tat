package com.example.sb.service;

import com.example.sb.entity.SbSoftware;
import com.example.sb.param.SbSoftwarePageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author wanglonglong
 * @since 2021-04-12
 */
public interface SbSoftwareService extends BaseService<SbSoftware> {

    /**
     * 保存
     *
     * @param sbSoftware
     * @return
     * @throws Exception
     */
    boolean saveSbSoftware(SbSoftware sbSoftware) throws Exception;

    /**
     * 修改
     *
     * @param sbSoftware
     * @return
     * @throws Exception
     */
    boolean updateSbSoftware(SbSoftware sbSoftware) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSbSoftware(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param sbSoftwareQueryParam
     * @return
     * @throws Exception
     */
    Paging<SbSoftware> getSbSoftwarePageList(SbSoftwarePageParam sbSoftwarePageParam) throws Exception;

}
