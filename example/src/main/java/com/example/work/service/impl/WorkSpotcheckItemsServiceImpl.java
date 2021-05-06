package com.example.work.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.work.entity.WorkSpotcheckItems;
import com.example.work.mapper.WorkSpotcheckItemsMapper;
import com.example.work.param.WorkSpotcheckItemsPageParam;
import com.example.work.service.WorkSpotcheckItemsService;

import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import lombok.extern.slf4j.Slf4j;

/**
 *  服务实现类
 *
 * @author wanglonglong
 * @since 2021-05-05
 */
@Slf4j
@Service
public class WorkSpotcheckItemsServiceImpl extends BaseServiceImpl<WorkSpotcheckItemsMapper, WorkSpotcheckItems> implements WorkSpotcheckItemsService {

    @Autowired
    private WorkSpotcheckItemsMapper workSpotcheckItemsMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWorkSpotcheckItems(WorkSpotcheckItems workSpotcheckItems) throws Exception {
        return super.save(workSpotcheckItems);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateWorkSpotcheckItems(WorkSpotcheckItems workSpotcheckItems) throws Exception {
        return super.updateById(workSpotcheckItems);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWorkSpotcheckItems(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<WorkSpotcheckItems> getWorkSpotcheckItemsPageList(WorkSpotcheckItemsPageParam workSpotcheckItemsPageParam) throws Exception {
        Page<WorkSpotcheckItems> page = new PageInfo<>(workSpotcheckItemsPageParam, OrderItem.desc(getLambdaColumn(WorkSpotcheckItems::getCreateTime)));
        LambdaQueryWrapper<WorkSpotcheckItems> wrapper = new LambdaQueryWrapper<>();
        IPage<WorkSpotcheckItems> iPage = workSpotcheckItemsMapper.selectPage(page, wrapper);
        return new Paging<WorkSpotcheckItems>(iPage);
    }

	@Override
	public List<WorkSpotcheckItems> getList(JSONObject jsonObject) {
		
		return null;
	}
}
