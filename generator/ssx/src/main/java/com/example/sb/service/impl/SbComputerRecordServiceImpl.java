package com.example.sb.service.impl;

import com.example.sb.entity.SbComputerRecord;
import com.example.sb.mapper.SbComputerRecordMapper;
import com.example.sb.service.SbComputerRecordService;
import com.example.sb.param.SbComputerRecordPageParam;
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
 * @since 2021-03-29
 */
@Slf4j
@Service
public class SbComputerRecordServiceImpl extends BaseServiceImpl<SbComputerRecordMapper, SbComputerRecord> implements SbComputerRecordService {

    @Autowired
    private SbComputerRecordMapper sbComputerRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSbComputerRecord(SbComputerRecord sbComputerRecord) throws Exception {
        return super.save(sbComputerRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSbComputerRecord(SbComputerRecord sbComputerRecord) throws Exception {
        return super.updateById(sbComputerRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSbComputerRecord(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<SbComputerRecord> getSbComputerRecordPageList(SbComputerRecordPageParam sbComputerRecordPageParam) throws Exception {
        Page<SbComputerRecord> page = new PageInfo<>(sbComputerRecordPageParam, OrderItem.desc(getLambdaColumn(SbComputerRecord::getCreateTime)));
        LambdaQueryWrapper<SbComputerRecord> wrapper = new LambdaQueryWrapper<>();
        IPage<SbComputerRecord> iPage = sbComputerRecordMapper.selectPage(page, wrapper);
        return new Paging<SbComputerRecord>(iPage);
    }

}
