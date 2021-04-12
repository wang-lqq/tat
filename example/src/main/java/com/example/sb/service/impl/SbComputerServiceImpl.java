package com.example.sb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sb.entity.SbComputer;
import com.example.sb.entity.SbComputerRecord;
import com.example.sb.mapper.SbComputerMapper;
import com.example.sb.mapper.SbComputerRecordMapper;
import com.example.sb.param.SbComputerPageParam;
import com.example.sb.service.SbComputerService;

import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import lombok.extern.slf4j.Slf4j;

/**
 *  服务实现类
 *
 * @author wanglonglong
 * @since 2021-03-29
 */
@Slf4j
@Service
public class SbComputerServiceImpl extends BaseServiceImpl<SbComputerMapper, SbComputer> implements SbComputerService {

    @Autowired
    private SbComputerMapper sbComputerMapper;
    
    @Autowired
    private SbComputerRecordMapper sbComputerRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSbComputer(SbComputer sbComputer) throws Exception {
        return super.save(sbComputer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSbComputer(SbComputer sbComputer) throws Exception {
        return super.updateById(sbComputer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSbComputer(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<JSONObject> getSbComputerPageList(SbComputerPageParam sbComputerPageParam) throws Exception {
    	
    	
    	
        Page<SbComputer> page = new PageInfo<>(sbComputerPageParam, OrderItem.desc(getLambdaColumn(SbComputer::getCreateTime)));
        LambdaQueryWrapper<SbComputer> wrapper = new LambdaQueryWrapper<>();
        IPage<SbComputer> iPage = sbComputerMapper.selectPage(page, wrapper);
        
        String keyword = sbComputerPageParam.getKeyword();
    	if(!StringUtils.isEmpty(keyword)) {
    		keyword = StringEscapeUtils.unescapeHtml4(keyword);
    		JSONObject obj = JSONObject.parseObject(keyword);
    		String fullName = obj.getString("fullName");
    		String ipAddress = obj.getString("ipAddress");
    		String departmentId = obj.getString("departmentId");
    		if(!StringUtils.isEmpty(fullName)) {
    			wrapper.like(null, departmentId);
    		}
			if(!StringUtils.isEmpty(ipAddress)) {
				wrapper.like(null, departmentId);
    		}
			if(!StringUtils.isEmpty(departmentId)) {
				wrapper.like(null, departmentId);
			}
    	}
        
        List<JSONObject> objs = new ArrayList<>();
        List<SbComputer> computers = iPage.getRecords();
        for (SbComputer sbComputer : computers) {
        	LambdaQueryWrapper<SbComputerRecord> wp = new LambdaQueryWrapper<>();
        	wp.eq(SbComputerRecord::getMac, sbComputer.getMac());
        	wp.orderByDesc(SbComputerRecord::getCreateTime);
        	
        	List<SbComputerRecord> sbComputerRecords = sbComputerRecordMapper.selectList(wp);
        	SbComputerRecord  sbComputerRecord = sbComputerRecords.get(0);
        	JSONObject obj = JSONObject.parseObject(JSONObject.toJSONString(sbComputerRecord));
        	obj.put("hostModel", sbComputer.getHostModel());
        	obj.put("oaManagementCode", sbComputer.getOaManagementCode());
        	obj.put("startDate", sbComputer.getStartDate());
        	objs.add(obj);
		}
        Paging<JSONObject> objPage = new Paging<JSONObject>();
        objPage.setTotal(iPage.getTotal());
        objPage.setRecords(objs);
        objPage.setPageIndex(iPage.getCurrent());
        objPage.setPageSize(iPage.getSize());
        return objPage;
    }

}
