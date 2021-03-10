package com.example.work.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.work.entity.WorkRepairParts;
import com.example.work.entity.WorkRepairReport;
import com.example.work.enums.EmailEnum;
import com.example.work.enums.StatusEnum;
import com.example.work.param.WorkRepairReportPageParam;
import com.example.work.service.MailService;
import com.example.work.service.WorkRepairPartsService;
import com.example.work.service.WorkRepairReportService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import io.geekidea.springbootplus.config.properties.SpringBootPlusProperties;
import io.geekidea.springbootplus.framework.common.api.ApiResult;
import io.geekidea.springbootplus.framework.common.controller.BaseController;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.validator.groups.Add;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;
import io.geekidea.springbootplus.framework.log.annotation.Module;
import io.geekidea.springbootplus.framework.log.annotation.OperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.geekidea.springbootplus.framework.shiro.util.CurrentUserUtil;
import io.geekidea.springbootplus.framework.shiro.vo.LoginSysUserVo;
import io.geekidea.springbootplus.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 联络-维修单表 控制器
 *
 * @author wanglonglong
 * @since 2021-02-26
 */
@Slf4j
@RestController
@RequestMapping("/workRepairReport")
@Module("work")
@Api(value = "联络-维修单表API", tags = {"联络-维修单表"})
public class WorkRepairReportController extends BaseController {

    @Autowired
    private WorkRepairReportService workRepairReportService;
    @Autowired
    private WorkRepairPartsService workRepairPartsService;
    @Autowired
    private MailService mailService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SpringBootPlusProperties springBootPlusProperties;

    /**
     * 添加联络-维修单表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加联络-维修单表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加联络-维修单表", response = ApiResult.class)
    public ApiResult<Boolean> addWorkRepairReport(@Validated(Add.class) @RequestBody WorkRepairReport workRepairReport) throws Exception {
    	boolean flag = false;
    	if(workRepairReport.getId() != null && workRepairReport.getId() != 0) {
    		flag = workRepairReportService.updateById(workRepairReport);
    	}else {
    		LoginSysUserVo vo = CurrentUserUtil.getUserIfLogin();
    		String format = DateUtil.format(new Date(),"yyyyMMddHHmmss");
    		String numbers = RandomUtil.randomNumbers(5);
    		String liushui = "W"+format+numbers;
    		workRepairReport.setWorkOrderNo(liushui);
    		workRepairReport.setDepartmentId(vo.getDepartmentId().intValue());
    		workRepairReport.setDepartmentName(vo.getDepartmentName());
    		flag = workRepairReportService.saveWorkRepairReport(workRepairReport);
    	}
        return ApiResult.result(flag);
    }

    /**
     * 修改联络-维修单表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改联络-维修单表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改联络-维修单表", response = ApiResult.class)
    public ApiResult<Boolean> updateWorkRepairReport(@Validated(Update.class) @RequestBody WorkRepairReport workRepairReport) throws Exception {
    	boolean flag = true;
    	WorkRepairReport wrr = new WorkRepairReport();
    	if(workRepairReport.getId() != null && workRepairReport.getId() != 0) {
    		wrr = workRepairReportService.getById(workRepairReport.getId());
    	}
        if(!StringUtils.isEmpty(workRepairReport.getWorkOrderNo())) {
        	if(StatusEnum.UNDER_REPAIR.getCode() == workRepairReport.getStatus()) { // 维修中
        		String progress = 5/(double)10*100 +"%";
        		workRepairReport.setProgress(progress);
        		
        		LambdaQueryWrapper<WorkRepairReport> wp = new LambdaQueryWrapper<>();
    			wp.eq(WorkRepairReport::getWorkOrderNo, workRepairReport.getWorkOrderNo());
    			flag = workRepairReportService.update(workRepairReport, wp);
        	}
        	if(StatusEnum.REPAIR_COMPLETE.getCode() == workRepairReport.getStatus()) { // 维修完成
        		LambdaQueryWrapper<WorkRepairReport> wrapper = new LambdaQueryWrapper<>();
        		wrapper.eq(WorkRepairReport::getWorkOrderNo, workRepairReport.getWorkOrderNo());
        		WorkRepairReport wr = workRepairReportService.getOne(wrapper);
        		if(wr.getStatus() == StatusEnum.UNDER_REPAIR.getCode() || wr.getStatus() == StatusEnum.AGREE.getCode()) { // 维修中才更新维修完成
        			String progress = 7.5/(double)10*100 +"%";
        			workRepairReport.setProgress(progress);
        			workRepairReport.setRepairCompletionTime(new Date());
        			
        			LambdaQueryWrapper<WorkRepairReport> wp = new LambdaQueryWrapper<>();
        			wp.eq(WorkRepairReport::getWorkOrderNo, workRepairReport.getWorkOrderNo());
        			flag = workRepairReportService.update(workRepairReport, wp);
        			// 发送邮件->报修审核人
        			String email = sysUserService.getReportExamineEmail(wr.getDepartmentId().longValue(), EmailEnum.REPORT_COMPLETE.getRoleCode());
        			Map<String, Object> data = object2Map(wr);
        			data.put("createTime", DateUtil.format(wr.getCreateTime(),"yyyy-MM-dd HH:mm"));
        			data.put("completionTime", DateUtil.formatDate(wr.getCompletionTime()));
        			data.put("repairCompletionTime", DateUtil.formatDate(new Date()));
        	    	mailService.sendMail(email, EmailEnum.REPORT_COMPLETE.getSubject(), EmailEnum.REPORT_COMPLETE.getTemplate(), data);
        		}else if(wr.getStatus() == StatusEnum.REPAIR_COMPLETE.getCode()) {// 维修完成->维修完成
        			LambdaQueryWrapper<WorkRepairReport> wp = new LambdaQueryWrapper<>();
        			wp.eq(WorkRepairReport::getWorkOrderNo, workRepairReport.getWorkOrderNo());
        			flag = workRepairReportService.update(workRepairReport, wp);
        		}else if(wr.getStatus() == StatusEnum.COMPLETE_AGREE.getCode()) {// 维修完成->维修审核
        			workRepairReport.setStatus(null);
        			LambdaQueryWrapper<WorkRepairReport> wp = new LambdaQueryWrapper<>();
        			wp.eq(WorkRepairReport::getWorkOrderNo, workRepairReport.getWorkOrderNo());
        			flag = workRepairReportService.update(workRepairReport, wp);
        		}
        	}
        }else if((!StringUtils.isEmpty(workRepairReport.getEnclosure())) ||
        		(StringUtils.isEmpty(workRepairReport.getEnclosure()) && !StringUtils.isEmpty(wrr.getEnclosure()))){
        	WorkRepairReport wr = workRepairReportService.getById(workRepairReport.getId());
        	String oleEnclosure = wr.getEnclosure();
        	String newEnclosure = workRepairReport.getEnclosure();
        	List<String> oleEnclosureList = new ArrayList<>();
        	List<String> newEnclosureList = new ArrayList<>();
        	if(!StringUtils.isEmpty(oleEnclosure)) {
        		oleEnclosureList = new ArrayList<>(Arrays.asList(oleEnclosure.split(",")));
        	}
        	if(!StringUtils.isEmpty(newEnclosure)) {
        		newEnclosureList = new ArrayList<>(Arrays.asList(newEnclosure.split(",")));
        	}
        	// 删除旧文件
        	oleEnclosureList.removeAll(newEnclosureList);
        	for (String str : oleEnclosureList) {
        		String imgPath = springBootPlusProperties.getUploadPath()+str.substring(str.lastIndexOf("/")+1);
        		File file = new File(imgPath);
        		if(file.exists()) {
        			file.delete();
        		}
			}
        	workRepairReportService.updateById(workRepairReport);
        }else {
        	flag = workRepairReportService.updateWorkRepairReport(workRepairReport);
        }
        return ApiResult.result(flag);
    }

    /**
     * 删除联络-维修单表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除联络-维修单表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除联络-维修单表", response = ApiResult.class)
    public ApiResult<Boolean> deleteWorkRepairReport(@PathVariable("id") Long id) throws Exception {
        boolean flag = workRepairReportService.deleteWorkRepairReport(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取联络-维修单表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "联络-维修单表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "联络-维修单表详情", response = WorkRepairReport.class)
    public ApiResult<Map<String, Object>> getWorkRepairReport(@PathVariable("id") Long id) throws Exception {
        WorkRepairReport workRepairReport = workRepairReportService.getById(id);
        LambdaQueryWrapper<WorkRepairParts> wp = new LambdaQueryWrapper<>();
        wp.eq(WorkRepairParts::getWorkOrderNo, workRepairReport.getWorkOrderNo());
        List<WorkRepairParts> list = workRepairPartsService.list(wp);
        
        List<Map<String, Object>> partList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
        	double countMoney = 0;
        	for (WorkRepairParts workRepairParts : list) {
        		partList.add(object2Map(workRepairParts));
        		countMoney += workRepairParts.getMoney();
        	}
        	
        	WorkRepairParts countParts = new WorkRepairParts();
        	countParts.setMoney(countMoney);
        	Map<String, Object> countPartsMap = object2Map(countParts);
        	countPartsMap.put("unitPrice", "维修总费用");
        	partList.add(countPartsMap);
        }
        Map<String, Object> data = object2Map(workRepairReport);
        data.put("createTime", DateUtil.format(workRepairReport.getCreateTime(),"yyyy-MM-dd HH:mm"));
        if(workRepairReport.getCompletionTime() != null) {
        	data.put("completionTime", DateUtil.formatDate(workRepairReport.getCompletionTime()));
        }
        if(workRepairReport.getRepairCompletionTime() != null) {
        	data.put("repairCompletionTime", DateUtil.formatDate(workRepairReport.getRepairCompletionTime()));
        }
        data.put("repairParts", partList);
        if(workRepairReport.getAgency() == null || workRepairReport.getAgency() == 0) {
        	data.put("agency", "社内");
        }
        if(workRepairReport.getAgency() != null && workRepairReport.getAgency() == 1) {
        	data.put("agency", "社外");
        }
        if(workRepairReport.getStatus() == StatusEnum.COMPLETE_AGREE.getCode() || workRepairReport.getStatus() == StatusEnum.REPAIR_COMPLETE.getCode()) {
        	data.put("status", "已完成");
        }
        return ApiResult.ok(data);
    }

    /**
     * 联络-维修单表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "联络-维修单表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "联络-维修单表分页列表", response = WorkRepairReport.class)
    public ApiResult<Paging<WorkRepairReport>> getWorkRepairReportPageList(@Validated @RequestBody WorkRepairReportPageParam workRepairReportPageParam) throws Exception {
    	Paging<WorkRepairReport> paging = workRepairReportService.getWorkRepairReportPageList(workRepairReportPageParam);
        return ApiResult.ok(paging);
    }
    
    /**
     * 联络-维修单表提交审核
     */
    @PostMapping("/repairExamine")
    @OperationLog(name = "联络-维修单表提交审核", type = OperationLogType.UPDATE)
    @ApiOperation(value = "联络-维修单表提交审核", response = WorkRepairReport.class)
    public ApiResult<Boolean> repairExamine(@RequestBody WorkRepairReport workRepairReport) throws Exception {
    	LoginSysUserVo vo = CurrentUserUtil.getUserIfLogin();
    	if(workRepairReport.getStatus() == StatusEnum.AGREE.getCode()) { // 同意审核
    		// 算进度
    		String progress = (2.5/(double)10)*100+"%";
    		workRepairReport.setProgress(progress);
    		workRepairReport.setRepairAuditUserId(vo.getId().intValue());
    		workRepairReport.setRepairAuditFullName(vo.getNickname());
    		workRepairReport.setRepairAuditTime(new Date());
    		// 发送邮件
    		WorkRepairReport wr = workRepairReportService.getById(workRepairReport.getId());
    		wr.setRepairAuditFullName("已审核->"+vo.getNickname());
    		Map<String, Object> data = object2Map(wr);
    		data.put("createTime", DateUtil.format(wr.getCreateTime(),"yyyy-MM-dd HH:mm"));
			data.put("completionTime", DateUtil.formatDate(wr.getCompletionTime()));
    		String[] to = sysUserService.getRepairEmail(EmailEnum.REPORT_ORDER.getRoleCode());
//    		mailService.sendMail(to, EmailEnum.REPORT_ORDER.getSubject(), EmailEnum.REPORT_ORDER.getTemplate(), data);
    	}
    	if(workRepairReport.getStatus() == StatusEnum.COMPLETE_AGREE.getCode()) { // 维修部门长同意审核
    		// 算进度
    		String progress = (10/(double)10)*100+"%";
    		workRepairReport.setProgress(progress);
    		workRepairReport.setRepairExamineUserId(vo.getId().intValue());
    		workRepairReport.setRepairExamineFullName(vo.getNickname());
    		workRepairReport.setRepairExamineTime(new Date());
    	}
    	if(workRepairReport.getStatus() == StatusEnum.REFUSE.getCode()) {
    		workRepairReport.setRepairAuditUserId(vo.getId().intValue());
    		workRepairReport.setRepairAuditFullName(vo.getNickname());
    		workRepairReport.setRepairAuditTime(new Date());
    	}
    	boolean flag = workRepairReportService.updateById(workRepairReport);
        return ApiResult.ok(flag);
    }
    
    
    /**
     * 工单打印
     */
    @PostMapping("/print")
    @OperationLog(name = "打印", type = OperationLogType.download)
    @ApiOperation(value = "打印", response = WorkRepairReport.class)
    public ApiResult<Boolean> print(@RequestBody WorkRepairReport workRepairReport) throws Exception {
        //填充创建pdf
        PdfReader reader = null;
        PdfStamper stamp = null;
        try {
        	String path = "C:/WINDOWS/Fonts/simhei.ttf";//windows里的字体资源路径
            BaseFont bf = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font = new Font(bf, 10f, Font.NORMAL, BaseColor.BLACK);
//        	File file = new File("D:\\demo.pdf");
//    		//创建文件
//    		file.createNewFile();
			/*
			 * ClassPathResource classPathResource = new
			 * ClassPathResource("templates/套打模版.pdf"); InputStream inputStream
			 * =classPathResource.getInputStream();
			 */
//        	InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/tdmb.pdf");
            reader = new PdfReader("E:/tdmb.pdf");
            //创建生成报告名称
            String root = springBootPlusProperties.getUploadPath();
            if (!new File(root).exists()) {
            	new File(root).mkdirs();
            }
            File deskFile = new File("D:\\demo.pdf");
//            boolean b = deskFile.createNewFile();
            stamp = new PdfStamper(reader, new FileOutputStream(deskFile));
            //取出报表模板中的所有字段
            AcroFields form = stamp.getAcroFields();
            form.addSubstitutionFont(bf);
            // 填充数据
            stamp.setFormFlattening(true);
            form.setField("telephone", "17137831663");
            form.setField("workOrderNo", "W2021030416183259105");
            form.setField("category", "A");
            form.setField("fullName", "葛小伦");
//            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stamp != null) {
                stamp.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return ApiResult.ok();
    }
    
    private Map<String, Object> object2Map(Object object){
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        Set<Entry<String,Object>> entrySet = jsonObject.entrySet();
        Map<String, Object> map=new LinkedHashMap<String,Object>();
        for (Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}

