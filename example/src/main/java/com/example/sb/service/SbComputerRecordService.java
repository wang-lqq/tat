package com.example.sb.service;

import com.example.sb.entity.SbComputerRecord;
import com.example.sb.param.SbComputerRecordPageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author wanglonglong
 * @since 2021-03-29
 */
public interface SbComputerRecordService extends BaseService<SbComputerRecord> {

    /**
     * 保存
     *
     * @param sbComputerRecord
     * @return
     * @throws Exception
     */
    boolean saveSbComputerRecord(SbComputerRecord sbComputerRecord) throws Exception;

    /**
     * 修改
     *
     * @param sbComputerRecord
     * @return
     * @throws Exception
     */
    boolean updateSbComputerRecord(SbComputerRecord sbComputerRecord) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSbComputerRecord(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param sbComputerRecordQueryParam
     * @return
     * @throws Exception
     */
    Paging<SbComputerRecord> getSbComputerRecordPageList(SbComputerRecordPageParam sbComputerRecordPageParam) throws Exception;

}
