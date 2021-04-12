package com.example.sb.service;

import java.util.List;

import org.apache.commons.collections4.SetUtils.SetView;

import com.example.sb.entity.SbComputersPermission;
import com.example.sb.param.SbComputersPermissionPageParam;
import com.example.sb.vo.SbPermissionTreeVo;

import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author wanglonglong
 * @since 2021-04-08
 */
public interface SbComputersPermissionService extends BaseService<SbComputersPermission> {

    /**
     * 保存
     *
     * @param sbComputersPermission
     * @return
     * @throws Exception
     */
    boolean saveSbComputersPermission(SbComputersPermission sbComputersPermission) throws Exception;

    /**
     * 修改
     *
     * @param sbComputersPermission
     * @return
     * @throws Exception
     */
    boolean updateSbComputersPermission(SbComputersPermission sbComputersPermission) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSbComputersPermission(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param sbComputersPermissionQueryParam
     * @return
     * @throws Exception
     */
    Paging<SbComputersPermission> getSbComputersPermissionPageList(SbComputersPermissionPageParam sbComputersPermissionPageParam) throws Exception;

	boolean saveSysRolePermissionBatch(Integer computersId, SetView<Integer> addSet);

	List<SbPermissionTreeVo> getTreeByComputersId(Integer computersId);

	List<SbPermissionTreeVo> queryTreeByComputersId(Integer computersId);
    
}
