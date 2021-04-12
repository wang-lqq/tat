package com.example.sb.service;

import java.util.List;

import com.example.sb.entity.SbPermission;
import com.example.sb.param.SbPermissionPageParam;
import com.example.sb.vo.SbPermissionTreeVo;

import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author wanglonglong
 * @since 2021-04-07
 */
public interface SbPermissionService extends BaseService<SbPermission> {

    /**
     * 保存
     *
     * @param sbPermission
     * @return
     * @throws Exception
     */
    boolean saveSbPermission(SbPermission sbPermission) throws Exception;

    /**
     * 修改
     *
     * @param sbPermission
     * @return
     * @throws Exception
     */
    boolean updateSbPermission(SbPermission sbPermission) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSbPermission(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param sbPermissionQueryParam
     * @return
     * @throws Exception
     */
    Paging<SbPermission> getSbPermissionPageList(SbPermissionPageParam sbPermissionPageParam) throws Exception;

	List<SbPermissionTreeVo> tree();
	
	List<SbPermissionTreeVo> list2Tree(List<SbPermissionTreeVo> list, Integer parentId);

	List<SbPermissionTreeVo> openTree();
}
