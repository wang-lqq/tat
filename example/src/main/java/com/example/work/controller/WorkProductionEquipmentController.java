package com.example.work.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.sb.entity.SbPermission;
import com.example.sb.service.SbPermissionService;
import com.example.work.entity.WorkProductionEquipment;
import com.example.work.param.WorkProductionEquipmentPageParam;
import com.example.work.service.WorkProductionEquipmentService;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import io.geekidea.springbootplus.framework.common.api.ApiResult;
import io.geekidea.springbootplus.framework.common.controller.BaseController;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.validator.groups.Add;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;
import io.geekidea.springbootplus.framework.log.annotation.Module;
import io.geekidea.springbootplus.framework.log.annotation.OperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备表 控制器
 *
 * @author wanglonglong
 * @param <T>
 * @since 2021-02-20
 */
@Slf4j
@RestController
@RequestMapping("/workProductionEquipment")
@Module("work")
@Api(value = "设备表API", tags = {"设备表"})
public class WorkProductionEquipmentController<T> extends BaseController {

    @Autowired
    private WorkProductionEquipmentService workProductionEquipmentService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SbPermissionService sbPermissionService;

    /**
     * 添加设备表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加设备表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加设备表", response = ApiResult.class)
    public ApiResult<Boolean> addWorkProductionEquipment(@Validated(Add.class) @RequestBody WorkProductionEquipment workProductionEquipment) throws Exception {
    	boolean flag = workProductionEquipmentService.saveWorkProductionEquipment(workProductionEquipment);
        return ApiResult.result(flag);
    }

    /**
     * 修改设备表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改设备表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改设备表", response = ApiResult.class)
    public ApiResult<Boolean> updateWorkProductionEquipment(@Validated(Update.class) @RequestBody WorkProductionEquipment workProductionEquipment) throws Exception {
        boolean flag = workProductionEquipmentService.updateWorkProductionEquipment(workProductionEquipment);
        return ApiResult.result(flag);
    }

    /**
     * 删除设备表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除设备表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除设备表", response = ApiResult.class)
    public ApiResult<Boolean> deleteWorkProductionEquipment(@PathVariable("id") Long id) throws Exception {
        boolean flag = workProductionEquipmentService.deleteWorkProductionEquipment(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取设备表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "设备表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "设备表详情", response = WorkProductionEquipment.class)
    public ApiResult<WorkProductionEquipment> getWorkProductionEquipment(@PathVariable("id") Long id) throws Exception {
    	WorkProductionEquipment workProductionEquipment = workProductionEquipmentService.getById(id);
        return ApiResult.ok(workProductionEquipment);
    }

    /**
     * 设备表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "设备表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "设备表分页列表", response = WorkProductionEquipment.class)
    public ApiResult<Paging<WorkProductionEquipment>> getWorkProductionEquipmentPageList(@Validated @RequestBody WorkProductionEquipmentPageParam workProductionEquipmentPageParam) throws Exception {
    	Paging<WorkProductionEquipment> paging = workProductionEquipmentService.getWorkProductionEquipmentPageList(workProductionEquipmentPageParam);
    	return ApiResult.ok(paging);
    }
    
    /**
     * 资产编码查询设备
     */
    @PostMapping("/getByAssetCode")
    @OperationLog(name = "资产编码查询设备", type = OperationLogType.INFO)
    @ApiOperation(value = "资产编码查询设备", response = WorkProductionEquipment.class)
    public ApiResult<WorkProductionEquipment> getByAssetCode(@Validated @RequestBody WorkProductionEquipment workProductionEquipment) throws Exception {
    	LambdaQueryWrapper<WorkProductionEquipment> wrapper = new LambdaQueryWrapper<>();
    	if(StringUtils.isEmpty(workProductionEquipment.getAssetCode())) {
    		workProductionEquipment.setAssetCode(null);
    	}
    	wrapper.eq(WorkProductionEquipment::getAssetCode, workProductionEquipment.getAssetCode());
    	List<WorkProductionEquipment> list = workProductionEquipmentService.list(wrapper);
    	if(list.size() > 1 || list.size() ==0) {
    		return ApiResult.ok(new WorkProductionEquipment());
    	}
        return ApiResult.ok(list.get(0));
    }
    
    /**
     * 设备表列表
     */
    @PostMapping("/getList")
    @OperationLog(name = "设备表列表", type = OperationLogType.LIST)
    @ApiOperation(value = "设备表列表", response = WorkProductionEquipment.class)
    public ApiResult<List<WorkProductionEquipment>> getList(@Validated @RequestBody WorkProductionEquipment workProductionEquipment) throws Exception {
    	Object obj = redisTemplate.opsForValue().get("workProductionEquipment");
    	List<WorkProductionEquipment> list = new ArrayList<>();
    	if(obj == null) {
    		list = workProductionEquipmentService.list();
    		String str = JSONUtil.toJsonStr(list);
    		Duration expireDuration = Duration.ofSeconds(3600);
    		redisTemplate.opsForValue().set("workProductionEquipment", str, expireDuration);
    	}else {
    		JSONArray array = JSONUtil.parseArray(obj);
    		list = JSONUtil.toList(array, WorkProductionEquipment.class);
    	}
    	if(StringUtils.isEmpty(workProductionEquipment.getAssetCode())) {
    		return ApiResult.ok(null);
    	}
    	List<WorkProductionEquipment> data = new ArrayList<>();
    	for (WorkProductionEquipment productionEquipment : list) {
			if(productionEquipment.getAssetCode().toLowerCase().indexOf(workProductionEquipment.getAssetCode().toLowerCase())!=-1) {
				data.add(productionEquipment);
			}
			if(data.size() > 9) {
				break;
			}
		}
        return ApiResult.ok(data);
    }
    
    /**
     * 数据导入
     */
    @PostMapping("/dataImport")
    @OperationLog(name = "数据导入", type = OperationLogType.excel_import)
    @ApiOperation(value = "数据导入", response = WorkProductionEquipment.class)
    public ApiResult<?> dataImport() throws Exception {
//		ExcelReader reader = ExcelUtil.getReader(FileUtil.file("C:\\Users\\ssx_it08112\\Desktop\\7346377.xlsx"));
//		List<List<Object>> read = reader.read(2, reader.getRowCount());
//		for (int i = 2; i < read.size(); i++) {
//			List<Object> objs = read.get(i);
//			WorkProductionEquipment pro = new WorkProductionEquipment();
//			pro.setCategory("A");
//			pro.setEquipmentName(objs.get(1)+"");
//			pro.setMachineNumber(objs.get(2)+"");
//			pro.setModel(objs.get(3)+"");
//			pro.setAssetCode(objs.get(4)+"");
//			pro.setUpdateTime(new Date());
//			workProductionEquipmentService.save(pro);
//		}
    	
    	List<Map<String, String>> map =new ArrayList<>();
    	
    	Map<String, String> m1 =new HashMap<>();
    	m1.put("k", "ssx-db01");
    	m1.put("v", "");
    	m1.put("leval", "0");
    	
    	Map<String, String> m2 =new HashMap<>();
    	m2.put("k", "EINS制造");
    	m2.put("v", "ssx-db01");
    	m2.put("leval", "0");
    	
    	Map<String, String> m3 =new HashMap<>();
    	m3.put("k", "加工系各文件夹");
    	m3.put("v", "EINS制造");
    	m3.put("leval", "1");
    
    	map.add(m1);
    	map.add(m2);
    	map.add(m3);
    	
    	for (Map<String, String> m : map) {
    		String k = m.get("k");
    		String v = m.get("v");
    		String leval = m.get("leval");
    		SbPermission sb = new SbPermission();
    		
    		LambdaQueryWrapper<SbPermission> wrapper = new LambdaQueryWrapper<>();
    		wrapper.eq(SbPermission::getTitle, v);
    		SbPermission  parentSb = sbPermissionService.getOne(wrapper);
    		if(parentSb != null) {
    			sb.setParentId(parentSb.getId().longValue());
    		}else {
    			sb.setParentId((long)0);
    		}
    		sb.setTitle(k);
    		sb.setStatus(0);
    		sb.setCreateTime(new Date());
    		sb.setUpdateTime(new Date());
    		sbPermissionService.save(sb);
    		if(leval.equals("1")) {
    			// 创建只读
    			SbPermission son = new SbPermission();
    			son.setTitle("只读");
    			son.setStatus(0);
    			son.setParentId(sb.getId().longValue());
    			son.setCreateTime(new Date());
    			son.setUpdateTime(new Date());
        		sbPermissionService.save(son);
        		// 创建读写
    			SbPermission sonw = new SbPermission();
    			sonw.setTitle("读写");
    			sonw.setStatus(0);
    			sonw.setParentId(sb.getId().longValue());
    			sonw.setCreateTime(new Date());
    			sonw.setUpdateTime(new Date());
        		sbPermissionService.save(sonw);
    		}
		}
		return ApiResult.ok();
    }
}

