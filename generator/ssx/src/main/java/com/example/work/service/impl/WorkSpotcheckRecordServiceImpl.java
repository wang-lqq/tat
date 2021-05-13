package com.example.work.service.impl;

import com.example.work.entity.WorkSpotcheckRecord;
import com.example.work.mapper.WorkSpotcheckRecordMapper;
import com.example.work.service.WorkSpotcheckRecordService;
import com.example.work.param.WorkSpotcheckRecordPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  服务实现类
 *
 * @author wanglonglong
 * @since 2021-05-13
 */
@Slf4j
@Service
public class WorkSpotcheckRecordServiceImpl extends BaseServiceImpl<WorkSpotcheckRecordMapper, WorkSpotcheckRecord> implements WorkSpotcheckRecordService {

    @Autowired
    private WorkSpotcheckRecordMapper workSpotcheckRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWorkSpotcheckRecord(WorkSpotcheckRecord workSpotcheckRecord) throws Exception {
        return super.save(workSpotcheckRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateWorkSpotcheckRecord(WorkSpotcheckRecord workSpotcheckRecord) throws Exception {
        return super.updateById(workSpotcheckRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWorkSpotcheckRecord(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<WorkSpotcheckRecord> getWorkSpotcheckRecordPageList(WorkSpotcheckRecordPageParam workSpotcheckRecordPageParam) throws Exception {
        Page<WorkSpotcheckRecord> page = new PageInfo<>(workSpotcheckRecordPageParam, OrderItem.desc(getLambdaColumn(WorkSpotcheckRecord::getCreateTime)));
        LambdaQueryWrapper<WorkSpotcheckRecord> wrapper = new LambdaQueryWrapper<>();
        IPage<WorkSpotcheckRecord> iPage = workSpotcheckRecordMapper.selectPage(page, wrapper);
        return new Paging<WorkSpotcheckRecord>(iPage);
    }

}
