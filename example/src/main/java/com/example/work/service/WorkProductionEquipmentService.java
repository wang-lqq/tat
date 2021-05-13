package com.example.work.service;

import com.example.work.entity.WorkProductionEquipment;
import com.example.work.param.WorkProductionEquipmentPageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 * 设备表 服务类
 *
 * @author wanglonglong
 * @since 2021-02-20
 */
public interface WorkProductionEquipmentService extends BaseService<WorkProductionEquipment> {

    /**
     * 保存
     *
     * @param workProductionEquipment
     * @return
     * @throws Exception
     */
    boolean saveWorkProductionEquipment(WorkProductionEquipment workProductionEquipment) throws Exception;

    /**
     * 修改
     *
     * @param workProductionEquipment
     * @return
     * @throws Exception
     */
    boolean updateWorkProductionEquipment(WorkProductionEquipment workProductionEquipment) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteWorkProductionEquipment(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param workProductionEquipmentQueryParam
     * @return
     * @throws Exception
     */
    Paging<WorkProductionEquipment> getWorkProductionEquipmentPageList(WorkProductionEquipmentPageParam workProductionEquipmentPageParam) throws Exception;

	Paging<WorkProductionEquipment> getPlanPageList(WorkProductionEquipmentPageParam workProductionEquipmentPageParam);

}
