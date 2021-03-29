package com.example.sb.service;

import com.example.sb.entity.SbComputer;
import com.example.sb.param.SbComputerPageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author wanglonglong
 * @since 2021-03-29
 */
public interface SbComputerService extends BaseService<SbComputer> {

    /**
     * 保存
     *
     * @param sbComputer
     * @return
     * @throws Exception
     */
    boolean saveSbComputer(SbComputer sbComputer) throws Exception;

    /**
     * 修改
     *
     * @param sbComputer
     * @return
     * @throws Exception
     */
    boolean updateSbComputer(SbComputer sbComputer) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSbComputer(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param sbComputerQueryParam
     * @return
     * @throws Exception
     */
    Paging<SbComputer> getSbComputerPageList(SbComputerPageParam sbComputerPageParam) throws Exception;

}
