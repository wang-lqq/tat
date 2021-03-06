package com.example.sysfile.service;

import com.example.sysfile.entity.SysFile;
import com.example.sysfile.param.SysFilePageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author wanglonglong
 * @since 2021-01-25
 */
public interface SysFileService extends BaseService<SysFile> {

    /**
     * 保存
     *
     * @param sysFile
     * @return
     * @throws Exception
     */
    boolean saveSysFile(SysFile sysFile) throws Exception;

    /**
     * 修改
     *
     * @param sysFile
     * @return
     * @throws Exception
     */
    boolean updateSysFile(SysFile sysFile) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysFile(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param sysFileQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysFile> getSysFilePageList(SysFilePageParam sysFilePageParam) throws Exception;

}
