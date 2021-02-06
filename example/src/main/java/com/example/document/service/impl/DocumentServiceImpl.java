package com.example.document.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.document.entity.Category;
import com.example.document.entity.Document;
import com.example.document.entity.OperationLogVo;
import com.example.document.mapper.CategoryMapper;
import com.example.document.mapper.DocumentMapper;
import com.example.document.param.DocumentPageParam;
import com.example.document.service.DocumentService;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.log.entity.SysOperationLog;
import io.geekidea.springbootplus.framework.log.mapper.SysOperationLogMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 文档 服务实现类
 *
 * @author wanglonglong
 * @since 2021-01-01
 */
@Slf4j
@Service
public class DocumentServiceImpl extends BaseServiceImpl<DocumentMapper, Document> implements DocumentService {
    @Autowired
    private SysOperationLogMapper sysOperationLogMapper;
    @Autowired
    private DocumentMapper documentMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveDocument(Document document) throws Exception {
        return super.save(document);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateDocument(Document document) throws Exception {
        return super.updateById(document);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteDocument(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<Document> getDocumentPageList(DocumentPageParam documentPageParam) throws Exception {
        Page<Document> page = new PageInfo<>(documentPageParam, OrderItem.desc(getLambdaColumn(Document::getCreateTime)));
        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        IPage<Document> iPage = documentMapper.selectPage(page, wrapper);
        return new Paging<Document>(iPage);
    }

	@Override
	public Paging<Document> getDocumentPageList(DocumentPageParam documentPageParam,
			LambdaQueryWrapper<Document> wrapper) throws Exception {
		Page<Document> page = new PageInfo<>(documentPageParam, OrderItem.desc(getLambdaColumn(Document::getCreateTime)));
		
		List<Integer> categoryIdList = new ArrayList<>();
		List<Category> categoryList = categoryMapper.selectList(Wrappers.emptyWrapper());
		for (Category category : categoryList) {
			categoryIdList.add(category.getId());
		}
		wrapper.in(Document::getCategoryId, categoryIdList);
        IPage<Document> iPage = documentMapper.selectPage(page, wrapper);
        return new Paging<Document>(iPage);
	}

	@Override
	public List<Map<String, Object>> getList(Document document) {
		List<Map<String, Object>> list = new ArrayList<>();
		
		LambdaQueryWrapper<Category> wrapperCategory = new LambdaQueryWrapper<>();
		wrapperCategory.orderByDesc(Category::getSort);
		List<Category> categories = categoryMapper.selectList(wrapperCategory);
		for (Category category : categories) {
			LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Document::getCategoryId, category.getId()).orderByDesc(Document::getSort);
			wrapper.select(Document::getId,Document::getTitle, Document::getRedDot);
			if(!StringUtils.isEmpty(document.getTitle())) {
				wrapper.like(Document::getTitle, document.getTitle());
			}
			List<Document> documents = documentMapper.selectList(wrapper);
			if(!CollectionUtils.isEmpty(documents)) {
				Map<String, Object> data = new HashMap<>();
				data.put("categoryName", category.getName());
				data.put("documents", documents);
				list.add(data);
			}
		}
		return list;
	}

	@Override
	public Paging<SysOperationLog> getPageLogList(DocumentPageParam documentPageParam) {
        Page<SysOperationLog> page = new PageInfo<>(documentPageParam,OrderItem.desc(getLambdaColumn(Document::getCreateTime)));
        LambdaQueryWrapper<SysOperationLog> wrapper = new LambdaQueryWrapper<>();
        
        String keyword = documentPageParam.getKeyword();
    	if(!StringUtils.isEmpty(keyword)) {
    		keyword = StringEscapeUtils.unescapeHtml4(keyword);
    		JSONObject obj = JSONObject.parseObject(keyword);
    		String logPlatform = obj.getString("logPlatform");
    		String ip = obj.getString("ip");
    		String documentId = obj.getString("documentId");
    		String date = obj.getString("date");
    		
     		if("reception".equals(logPlatform)) {
    			// 前台日志
     			wrapper.isNull(SysOperationLog::getUserName);
     			wrapper.eq(SysOperationLog::getName, "文档详情");
     			if(!StringUtils.isEmpty(ip)) {
     				wrapper.like(SysOperationLog::getIp, ip);
     			}
     			if(!StringUtils.isEmpty(documentId)) {
     				wrapper.eq(SysOperationLog::getPath, "/api/a/document/info/"+documentId);
     			}
     			if(!StringUtils.isEmpty(date)) {
     				String starDateStr = date.substring(0, date.lastIndexOf("/")).trim();
     				String endDateStr = date.substring(date.lastIndexOf("/")+1).trim();
     				wrapper.ge(SysOperationLog::getCreateTime, starDateStr);
     				wrapper.le(SysOperationLog::getCreateTime, endDateStr+" 23:59:59");
     			}
    		}else if("backstage".equals(logPlatform)) {
    			// 后台日志
    			wrapper.isNotNull(SysOperationLog::getUserName);
    		}
    	}
        IPage<SysOperationLog> iPage = sysOperationLogMapper.selectPage(page, wrapper);
        List<SysOperationLog> logs = iPage.getRecords();
        
        List<String> documentIds = new ArrayList<>();
        for (SysOperationLog sysOperationLog : logs) {
        	String documentId = sysOperationLog.getPath().substring(sysOperationLog.getPath().lastIndexOf("/")+1);
        	documentIds.add(documentId);
        	sysOperationLog.setPath(documentId);
		}
        if(!CollectionUtils.isEmpty(documentIds)) {
        	List<Document> documents = documentMapper.selectBatchIds(documentIds);
        	for (SysOperationLog sysOperationLog : logs) {
        		for (Document document : documents) {
        			if(sysOperationLog.getPath().equals(String.valueOf(document.getId()))) {
        				sysOperationLog.setPath(document.getTitle());
        			}
        		}
        	}
        }
        return new Paging<SysOperationLog>(iPage);
	}

	@Override
	public Map<String, Object> chart(String starDateStr, String endDateStr) {
		Date startDate = DateUtil.offsetDay(new Date(), -29);
		Date endDate = new Date();
		String startDateStr = DateUtil.formatDate(startDate);
		
		List<OperationLogVo> operationLogVos = new ArrayList<>();
		if(!StringUtils.isEmpty(starDateStr) && !StringUtils.isEmpty(endDateStr)) {
			startDate = DateUtil.parseDate(starDateStr);
			endDate = DateUtil.parseDate(endDateStr);
			operationLogVos = documentMapper.getVisitCount(starDateStr, endDateStr);
		}else {
			operationLogVos = documentMapper.getVisitCount(startDateStr, null);
		}
		List<Map<String, Object>> series = new ArrayList<>();
		List<Integer> seriesCount = new ArrayList<>();
		
		List<String> rangeList = new ArrayList<>();
		List<DateTime> range = DateUtil.rangeToList(startDate,endDate,DateField.DAY_OF_YEAR);
		for (DateTime dateTime : range) {
			boolean b = true;
			String str = DateUtil.formatDate(dateTime);
			rangeList.add(str);
			
			for (OperationLogVo operationLogVo : operationLogVos) {
				if(str.equals(operationLogVo.getVisitTime())) {
					b = false;
					seriesCount.add(operationLogVo.getVisits());
				}
			}
			
			if(b) {
				seriesCount.add(0);
			}
		}
		// series
		String legendName = "总访问量";
		Map<String, Object> seriesItem = new HashMap<>();
		seriesItem.put("name", legendName);
		seriesItem.put("type", "line");
		seriesItem.put("data", seriesCount);
		series.add(seriesItem);
		// legend
		Map<String, Object> legend = new HashMap<>();
		List<String> legendData = new ArrayList<>();
		legendData.add(legendName);
		legend.put("data", legendData);
		// xAxis
		Map<String, Object> xAxis = new HashMap<>();
		xAxis.put("boundaryGap", false);
		xAxis.put("data", rangeList);
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("series", series);
		data.put("legend", legend);
		data.put("xAxis", xAxis);
		return data;
	}
	
	@Override
	public Map<String, Object> chart2(Integer id,String starDateStr, String endDateStr) {
		Document document = documentMapper.selectById(id);
		Date startDate = DateUtil.offsetDay(new Date(), -29);
		String startDateStr = DateUtil.formatDate(startDate);
		String pathStr = "/api/a/document/info/"+id;
		Date endDate = new Date();
		List<OperationLogVo> operationLogVos = new ArrayList<>();
		
		if(!StringUtils.isEmpty(starDateStr) && !StringUtils.isEmpty(endDateStr)) {
			startDate = DateUtil.parseDate(starDateStr);
			endDate = DateUtil.parseDate(endDateStr);
			operationLogVos = documentMapper.getVisit(pathStr, starDateStr, endDateStr);
		}else {
			operationLogVos = documentMapper.getVisit(pathStr, startDateStr, null);
		}
		
		List<Map<String, Object>> series = new ArrayList<>();
		List<Integer> seriesCount = new ArrayList<>();
		List<String> rangeList = new ArrayList<>();
		List<DateTime> range = DateUtil.rangeToList(startDate, endDate, DateField.DAY_OF_YEAR);
		for (DateTime dateTime : range) {
			boolean b = true;
			String str = DateUtil.formatDate(dateTime);
			rangeList.add(str);
			
			for (OperationLogVo operationLogVo : operationLogVos) {
				if(str.equals(operationLogVo.getVisitTime())) {
					b = false;
					seriesCount.add(operationLogVo.getVisits());
				}
			}
			
			if(b) {
				seriesCount.add(0);
			}
		}
		// series
		Map<String, Object> seriesItem = new HashMap<>();
		seriesItem.put("name", document.getTitle());
		seriesItem.put("type", "line");
		seriesItem.put("data", seriesCount);
		series.add(seriesItem);
		// legend
		Map<String, Object> legend = new HashMap<>();
		List<String> legendData = new ArrayList<>();
		legendData.add(document.getTitle());
		legend.put("data", legendData);
		// xAxis
		Map<String, Object> xAxis = new HashMap<>();
		xAxis.put("boundaryGap", false);
		xAxis.put("data", rangeList);
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("series", series);
		data.put("legend", legend);
		data.put("xAxis", xAxis);
		return data;
	}

	@Override
	public List<Document> getForward(String starDateStr, String endDateStr) {
		QueryWrapper<Document> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("id","title");
		
		Date startDate = DateUtil.offsetDay(new Date(), -29);
		String startDateStr = DateUtil.formatDate(startDate);
		if(StringUtils.isEmpty(starDateStr)) {
			starDateStr = startDateStr;
		}
		List<Integer> idList = new ArrayList<>();
		List<OperationLogVo> operationLogVos = documentMapper.getForward(starDateStr, endDateStr);
		for (OperationLogVo operationLogVo : operationLogVos) {
			String documentId = operationLogVo.getPath().substring(operationLogVo.getPath().lastIndexOf("/")+1);
			idList.add(Integer.parseInt(documentId));
		}
		queryWrapper.in("id", idList);
		List<Document> documents = documentMapper.selectList(queryWrapper);
		documents.sort(Comparator.comparingInt(o -> idList.indexOf(o.getId())));
		return documents;
	}

	@Override
	public List<SysOperationLog> export(JSONObject obj) {
        LambdaQueryWrapper<SysOperationLog> wrapper = new LambdaQueryWrapper<>();
		String ip = obj.getString("ip");
		String documentId = obj.getString("documentId");
		String date = obj.getString("date");
		// 前台日志
		wrapper.isNull(SysOperationLog::getUserName);
		wrapper.eq(SysOperationLog::getName, "文档详情");
		if(!StringUtils.isEmpty(ip)) {
			wrapper.like(SysOperationLog::getIp, ip);
		}
		if(!StringUtils.isEmpty(documentId)) {
			wrapper.eq(SysOperationLog::getPath, "/api/a/document/info/"+documentId);
		}
		if(!StringUtils.isEmpty(date)) {
			String starDateStr = date.substring(0, date.lastIndexOf("/")).trim();
			String endDateStr = date.substring(date.lastIndexOf("/")+1).trim();
			wrapper.ge(SysOperationLog::getCreateTime, starDateStr);
			wrapper.le(SysOperationLog::getCreateTime, endDateStr+" 23:59:59");
		}
        List<SysOperationLog> logs = sysOperationLogMapper.selectList(wrapper);
        
        List<String> documentIds = new ArrayList<>();
        for (SysOperationLog sysOperationLog : logs) {
        	String docId = sysOperationLog.getPath().substring(sysOperationLog.getPath().lastIndexOf("/")+1);
        	documentIds.add(docId);
        	sysOperationLog.setPath(docId);
		}
        if(!CollectionUtils.isEmpty(documentIds)) {
        	List<Document> documents = documentMapper.selectBatchIds(documentIds);
        	for (SysOperationLog sysOperationLog : logs) {
        		for (Document document : documents) {
        			if(sysOperationLog.getPath().equals(String.valueOf(document.getId()))) {
        				sysOperationLog.setPath(document.getTitle());
        			}
        		}
        	}
        }
        return logs;
	}
}
