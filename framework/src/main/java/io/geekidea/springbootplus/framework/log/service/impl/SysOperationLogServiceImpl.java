/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.geekidea.springbootplus.framework.log.service.impl;

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

import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.log.entity.SysOperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.geekidea.springbootplus.framework.log.mapper.SysOperationLogMapper;
import io.geekidea.springbootplus.framework.log.param.SysOperationLogPageParam;
import io.geekidea.springbootplus.framework.log.service.SysOperationLogService;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统操作日志 服务实现类
 *
 * @author geekidea
 * @since 2020-03-19
 */
@Slf4j
@Service
public class SysOperationLogServiceImpl extends BaseServiceImpl<SysOperationLogMapper, SysOperationLog> implements SysOperationLogService {

    @Autowired
    private SysOperationLogMapper sysOperationLogMapper;
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysOperationLog(SysOperationLog sysOperationLog) throws Exception {
        return super.save(sysOperationLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysOperationLog(SysOperationLog sysOperationLog) throws Exception {
        return super.updateById(sysOperationLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysOperationLog(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<SysOperationLog> getSysOperationLogPageList(SysOperationLogPageParam sysOperationLogPageParam) throws Exception {
        Page<SysOperationLog> page = new PageInfo<>(sysOperationLogPageParam,OrderItem.desc(getLambdaColumn(SysOperationLog::getCreateTime)));
        LambdaQueryWrapper<SysOperationLog> wrapper = new LambdaQueryWrapper<>();
        
        String keyword = sysOperationLogPageParam.getKeyword();
    	if(!StringUtils.isEmpty(keyword)) {
    		keyword = StringEscapeUtils.unescapeHtml4(keyword);
    		JSONObject obj = JSONObject.parseObject(keyword);
    		String ip = obj.getString("ip");
    		String userId = obj.getString("userId");
    		String date = obj.getString("date");
    		String type = obj.getString("type");
    		
    		if(!StringUtils.isEmpty(ip)) {
 				wrapper.like(SysOperationLog::getIp, ip);
 			}
    		if(!StringUtils.isEmpty(userId)) {
 				wrapper.eq(SysOperationLog::getUserId, userId);
 			}
    		if(!StringUtils.isEmpty(date)) {
 				String starDateStr = date.substring(0, date.lastIndexOf("/")).trim();
 				String endDateStr = date.substring(date.lastIndexOf("/")+1).trim();
 				wrapper.ge(SysOperationLog::getCreateTime, starDateStr);
 				wrapper.le(SysOperationLog::getCreateTime, endDateStr+" 23:59:59");
 			}
    		if(!StringUtils.isEmpty(type)) {
 				wrapper.eq(SysOperationLog::getType, type);
 			}
 			wrapper.isNotNull(SysOperationLog::getUserName);
    	}
        IPage<SysOperationLog> iPage = sysOperationLogMapper.selectPage(page, wrapper);
        List<SysOperationLog> operationLogs = iPage.getRecords();
        for (SysOperationLog sysOperationLog : operationLogs) {
        	sysOperationLog.setParam(StringEscapeUtils.unescapeHtml4(sysOperationLog.getParam()));
		}
        return new Paging<SysOperationLog>(iPage);
    }

	@Override
	public List<SysOperationLog> export(JSONObject obj) {
        LambdaQueryWrapper<SysOperationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysOperationLog::getCreateTime);
        wrapper.isNotNull(SysOperationLog::getUserName);
        
		String ip = obj.getString("ip");
		String userId = obj.getString("userId");
		String date = obj.getString("date");
		String type = obj.getString("type");
		if(!StringUtils.isEmpty(ip)) {
			wrapper.like(SysOperationLog::getIp, ip);
		}
		if(!StringUtils.isEmpty(userId)) {
			wrapper.eq(SysOperationLog::getUserId, userId);
		}
		if(!StringUtils.isEmpty(date)) {
			String starDateStr = date.substring(0, date.lastIndexOf("/")).trim();
			String endDateStr = date.substring(date.lastIndexOf("/")+1).trim();
			wrapper.ge(SysOperationLog::getCreateTime, starDateStr);
			wrapper.le(SysOperationLog::getCreateTime, endDateStr+" 23:59:59");
		}
		if(!StringUtils.isEmpty(type)) {
			wrapper.eq(SysOperationLog::getType, type);
		}
    	List<SysOperationLog> operationLogs = sysOperationLogMapper.selectList(wrapper);
    	operationLogs.forEach(operationLog ->{
    		operationLog.setParam(StringEscapeUtils.unescapeHtml4(operationLog.getParam()));
    		operationLog.setRemark(OperationLogType.getDesc(operationLog.getType()).getDesc());
    	});
        return operationLogs;
	}
}
