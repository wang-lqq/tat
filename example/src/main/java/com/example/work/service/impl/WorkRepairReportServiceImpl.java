package com.example.work.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.work.entity.WorkRepairReport;
import com.example.work.enums.EmailEnum;
import com.example.work.mapper.WorkRepairReportMapper;
import com.example.work.param.WorkRepairReportPageParam;
import com.example.work.service.MailService;
import com.example.work.service.WorkRepairReportService;

import cn.hutool.core.date.DateUtil;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.shiro.util.CurrentUserUtil;
import io.geekidea.springbootplus.framework.shiro.vo.LoginSysUserVo;
import io.geekidea.springbootplus.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;

/**
 * 联络-维修单表 服务实现类
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
@Slf4j
@Service
public class WorkRepairReportServiceImpl extends BaseServiceImpl<WorkRepairReportMapper, WorkRepairReport> implements WorkRepairReportService {

    @Autowired
    private WorkRepairReportMapper workRepairReportMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MailService mailService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWorkRepairReport(WorkRepairReport workRepairReport) throws Exception {
    	LoginSysUserVo userVo = CurrentUserUtil.getUserIfLogin();
    	
    	boolean b = super.save(workRepairReport);
    	WorkRepairReport wr = workRepairReportMapper.selectById(workRepairReport.getId());
    	// 发送邮件->给部门长
    	LoginSysUserVo vo = CurrentUserUtil.getUserIfLogin();
    	String email = sysUserService.getReportExamineEmail(vo.getDepartmentId(), EmailEnum.REPORT_EXAMINE.getRoleCode());
    	if(userVo.getRoleCode().equals(EmailEnum.REPORT_ORDER.getRoleCode())) {
    		email = sysUserService.getReportExamineEmail(vo.getDepartmentId(), EmailEnum.REPORT_ORDER_EXAMINE.getRoleCode());
    	}
    	Map<String, Object> data = object2Map(wr);
    	data.put("createTime", DateUtil.format(wr.getCreateTime(),"yyyy-MM-dd HH:mm"));
		data.put("completionTime", DateUtil.formatDate(wr.getCompletionTime()));
    	try {
			mailService.sendMail(email, EmailEnum.REPORT_EXAMINE.getSubject()+" "+wr.getWorkOrderNo(), EmailEnum.REPORT_EXAMINE.getTemplate(), data);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return b;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateWorkRepairReport(WorkRepairReport workRepairReport) throws Exception {
        return super.updateById(workRepairReport);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteWorkRepairReport(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<WorkRepairReport> getWorkRepairReportPageList(WorkRepairReportPageParam workRepairReportPageParam) throws Exception {
    	LoginSysUserVo userVo = CurrentUserUtil.getUserIfLogin();
    	Long departmentId = userVo.getDepartmentId();
    	String type = "";
    	
    	LambdaQueryWrapper<WorkRepairReport> wrapper = new LambdaQueryWrapper<>();
    	String keyword = workRepairReportPageParam.getKeyword();
    	if(!StringUtils.isEmpty(keyword)) {
    		keyword = StringEscapeUtils.unescapeHtml4(keyword);
    		JSONObject obj = JSONObject.parseObject(keyword);
    		String status = obj.getString("status");
    		String fullName = obj.getString("fullName");
    		String assetCode = obj.getString("assetCode");
    		String equipmentName = obj.getString("equipmentName");
    		String servicePersonal = obj.getString("servicePersonal");
    		String agency = obj.getString("agency");
    		String workOrderNo = obj.getString("workOrderNo");
    		String machineNumber = obj.getString("machineNumber");
    		String model = obj.getString("model");
    		String category = obj.getString("category");
    		String date = obj.getString("date");
    		String departmentIdStr = obj.getString("departmentId");
    		type = obj.getString("type");
    		if(!StringUtils.isEmpty(status)) {
    			wrapper.eq(WorkRepairReport::getStatus, status);
    		}
    		if(!StringUtils.isEmpty(fullName)) {
    			wrapper.like(WorkRepairReport::getFullName, fullName);
    		}
    		if(!StringUtils.isEmpty(assetCode)) {
    			wrapper.like(WorkRepairReport::getAssetCode, assetCode);
    		}
    		if(!StringUtils.isEmpty(equipmentName)) {
    			wrapper.like(WorkRepairReport::getEquipmentName, equipmentName);
    		}
    		if(!StringUtils.isEmpty(servicePersonal)) {
    			wrapper.like(WorkRepairReport::getServicePersonal, servicePersonal);
    		}
    		if(!StringUtils.isEmpty(agency)) {
    			wrapper.eq(WorkRepairReport::getAgency, agency);
    		}
    		if(!StringUtils.isEmpty(workOrderNo)) {
    			wrapper.like(WorkRepairReport::getWorkOrderNo, workOrderNo);
    		}
    		if(!StringUtils.isEmpty(model)) {
    			wrapper.like(WorkRepairReport::getModel, model);
    		}
    		if(!StringUtils.isEmpty(machineNumber)) {
    			wrapper.like(WorkRepairReport::getMachineNumber, machineNumber);
    		}
    		if(!StringUtils.isEmpty(category)) {
    			wrapper.eq(WorkRepairReport::getCategory, category);
    		}
    		if(!StringUtils.isEmpty(date)) {
    			Date startDate = DateUtil.parse(date.substring(0, date.lastIndexOf("/")).trim(),"yyyy-MM");
    			Date endDate = DateUtil.parse(date.substring(date.lastIndexOf("/")+1).trim(),"yyyy-MM");
    			wrapper.ge(WorkRepairReport::getCreateTime, startDate);
    			wrapper.le(WorkRepairReport::getCreateTime, endDate);
        	}
    		if(!StringUtils.isEmpty(departmentIdStr)) {
    			wrapper.eq(WorkRepairReport::getDepartmentId, departmentIdStr);
    		}
    	}
    	String roleCode = userVo.getRoleCode();
    	if(!roleCode.contains("admin") && !roleCode.contains("repair")) {
    		wrapper.eq(WorkRepairReport::getDepartmentId, departmentId.intValue());
    	}
    	if(roleCode.contains("repair") && "1".equals(type)) {
    		wrapper.eq(WorkRepairReport::getDepartmentId, departmentId.intValue());
    	}
    	if("2".equals(type)) {
    		wrapper.ge(WorkRepairReport::getStatus, 1);
    	}
    	Page<WorkRepairReport> page = new PageInfo<>(workRepairReportPageParam, OrderItem.desc(getLambdaColumn(WorkRepairReport::getCreateTime)));
        IPage<WorkRepairReport> iPage = workRepairReportMapper.selectPage(page, wrapper);
        return new Paging<WorkRepairReport>(iPage);
    }
    
    private Map<String, Object> object2Map(Object object){
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        Set<Entry<String,Object>> entrySet = jsonObject.entrySet();
        Map<String, Object> map=new HashMap<String,Object>();
        for (Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
