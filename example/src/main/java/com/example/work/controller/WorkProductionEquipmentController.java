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
import com.example.sb.entity.SbComputers;
import com.example.sb.entity.SbComputersPermission;
import com.example.sb.entity.SbComputersSoftware;
import com.example.sb.entity.SbPermission;
import com.example.sb.service.SbComputersPermissionService;
import com.example.sb.service.SbComputersService;
import com.example.sb.service.SbComputersSoftwareService;
import com.example.sb.service.SbPermissionService;
import com.example.work.entity.WorkProductionEquipment;
import com.example.work.param.WorkProductionEquipmentPageParam;
import com.example.work.service.WorkProductionEquipmentService;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import io.geekidea.springbootplus.framework.common.api.ApiResult;
import io.geekidea.springbootplus.framework.common.controller.BaseController;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.validator.groups.Add;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;
import io.geekidea.springbootplus.framework.log.annotation.Module;
import io.geekidea.springbootplus.framework.log.annotation.OperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.geekidea.springbootplus.system.entity.SysDepartment;
import io.geekidea.springbootplus.system.service.SysDepartmentService;
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
    @Autowired
    private SysDepartmentService sysDepartmentService;
    @Autowired
    private SbComputersService sbComputersService;
    @Autowired
    private SbComputersSoftwareService sbComputersSoftwareService;
    @Autowired
    private SbComputersPermissionService sbComputersPermissionService;


    /**
     * 添加设备表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加设备表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加设备表", response = ApiResult.class)
    public ApiResult<Boolean> addWorkProductionEquipment(@Validated(Add.class) @RequestBody WorkProductionEquipment workProductionEquipment) throws Exception {
    	if(workProductionEquipment.getDepartmentId() != null && workProductionEquipment.getDepartmentId() != 0) {
	    		workProductionEquipment.setDepartmentName(sysDepartmentService.getById(workProductionEquipment.getDepartmentId()).getName());
    	}
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
		ExcelReader reader = ExcelUtil.getReader(FileUtil.file("C:\\Users\\ssx_it08112\\Desktop\\JL-IT-03 SSX电脑客户端使用统计2021.04.06-导入数据库使用.xlsx"));
		List<List<Object>> read = reader.read(1, reader.getRowCount());
		for (int i = 0; i < read.size(); i++) {
			List<Object> objs = read.get(i);
			System.out.println(objs);
			if(objs.get(0) != null) {
				String v1 = objs.get(1).toString();
				String v2 = objs.get(2).toString();
				String v3 = objs.get(3).toString();
				String v4 = objs.get(4).toString();
				String v5 = objs.get(5).toString();
				String v6 = objs.get(6).toString();
				String v7 = objs.get(7).toString();
				String v8 = objs.get(8).toString();
				String v9 = objs.get(9).toString();
				String v10 = objs.get(10).toString();
				String v11 = objs.get(11).toString();
				String v12 = objs.get(12).toString();
				
				Map<String, Object> columnMap = new HashMap<>();
				columnMap.put("name", v1);
				List<SysDepartment> departments = sysDepartmentService.listByMap(columnMap);
				Integer departmentId = departments.get(0).getId().intValue();
				SbComputers sbComputers = new SbComputers();
				sbComputers.setDepartmentName(v1);
				sbComputers.setDepartmentId(departmentId);
				sbComputers.setFullName(v2);
				sbComputers.setIpAddress(v3);
				sbComputers.setDomainUsername(v4);
				sbComputers.setComputerName(v5);
				sbComputers.setMac(v6);
				sbComputers.setHostModel(v7);
				if("2019.5/30".equals(v8.toString())) {
					v8 = "2019-05-30 00:00:00";
				}
				if(!StringUtils.isEmpty(v8)) {
					sbComputers.setStartDate(DateUtil.parse(v8, "yyyy-MM-dd HH:mm:ss"));
				}
				
				sbComputers.setNewAssetcode(v9);
				sbComputers.setOaManagementCode(v10);
				sbComputers.setOperatingSystem(v11);
				sbComputers.setAdministratorPassword(v12);
				sbComputersService.save(sbComputers);
				
				int o = 13;
				for (int j = 1; j <= 36; j++) {
					String str = objs.get(o).toString();
					SbComputersSoftware sbs = new SbComputersSoftware();
					sbs.setComputersId(sbComputers.getId().intValue());
					sbs.setSoftwareId(j);
					sbs.setSoftwareValue(str);
					sbs.setUpdateTime(new Date());
					sbComputersSoftwareService.save(sbs);
					o++;
				}
			}
//			if(!StringUtils.isEmpty(v1)) {
//				SbPermission sb = new SbPermission();
//				
//				LambdaQueryWrapper<SbPermission> wrapper = new LambdaQueryWrapper<>();
//				wrapper.eq(SbPermission::getTitle, v2);
//				SbPermission  parentSb = sbPermissionService.getOne(wrapper);
//				if(parentSb != null) {
//					sb.setParentId(parentSb.getId().longValue());
//				}else {
//					sb.setParentId((long)0);
//				}
//				sb.setTitle(v1);
//				sb.setStatus(0);
//				sb.setCreateTime(new Date());
//				sb.setUpdateTime(new Date());
//				sbPermissionService.save(sb);
//				if(v3.equals("1")) {
//					// 创建只读
//					SbPermission son = new SbPermission();
//					son.setTitle("只读");
//					son.setStatus(0);
//					son.setParentId(sb.getId().longValue());
//					son.setCreateTime(new Date());
//					son.setUpdateTime(new Date());
//					sbPermissionService.save(son);
//					// 创建读写
//					SbPermission sonw = new SbPermission();
//					sonw.setTitle("读写");
//					sonw.setStatus(0);
//					sonw.setParentId(sb.getId().longValue());
//					sonw.setCreateTime(new Date());
//					sonw.setUpdateTime(new Date());
//					sbPermissionService.save(sonw);
//				}
//			}
		}
		return ApiResult.ok();
    }
    
    /**
     * 数据导入
     */
    @PostMapping("/dataImports")
    @OperationLog(name = "数据导入", type = OperationLogType.excel_import)
    @ApiOperation(value = "数据导入", response = WorkProductionEquipment.class)
    public ApiResult<?> dataImports() throws Exception {
		ExcelReader reader = ExcelUtil.getReader(FileUtil.file("C:\\Users\\ssx_it08112\\Desktop\\（02）JL-IT-07 SSX 服务器文件夹权限-2021年4月6日 - 导入数据库使用.xlsx"));
		List<List<Object>> readTitle = reader.read(4, reader.getRowCount());
		
		List<List<Object>> read = reader.read(5, reader.getRowCount());
		
		for (int i = 0; i < read.size(); i++) {
			List<Object> objs = read.get(i);
			System.out.println(objs);
			if(objs.get(0) != null) {
				
				String ip = objs.get(3).toString();
				if(i == 15) {
					ip = "172.19.6.200";
				}
				if(i == 110) {
					ip = "172.19.8.139";
				}
				if(i == 222) {
					ip = "172.19.8.98";
				}
				Map<String, Object> columnMap = new HashMap<>();
				columnMap.put("ip_address", ip);
				List<SbComputers> sbComputers = sbComputersService.listByMap(columnMap);
				if(CollectionUtil.isEmpty(sbComputers)) {
					System.out.println("IP 匹配不存在");
					continue;
				}
				if(CollectionUtil.isNotEmpty(sbComputers) && sbComputers.size() > 1) {
					System.out.println("IP 匹配重复");
//					continue;
				}
				
				
				for (int j = 6; j < objs.size(); j++) {
					if(j >= 159) {
						System.out.println(objs);
						continue;
					}
					// 字段名
					String field = readTitle.get(0).get(j).toString();
					if(field.equals("资材课订单回签")) {
						field = "ssx-db02资材课订单回签";
					}
					if(j == 23) {
						field = "资材课订单回签";
					}
					
					Map<String, Object> fields = new HashMap<>();
					fields.put("title", field);
					List<SbPermission> sbPermissions = sbPermissionService.listByMap(fields);
					if(CollectionUtil.isEmpty(sbPermissions)) {
						System.out.println("权限 匹配不存在:"+field);
						continue;
					}
					if(CollectionUtil.isNotEmpty(sbPermissions) && sbPermissions.size() > 1) {
						System.out.println("权限 匹配重复:"+field);
						continue;
					}
					
					Integer id = sbPermissions.get(0).getId().intValue();
					
					// 值
					if(objs.get(j) != null) {
						String v = objs.get(j).toString();
						if(!StringUtils.isEmpty(v)) {
							SbComputersPermission sbComputersPermission = new SbComputersPermission();
							if(v.equals("◎")) {
								// 读 id加1
								int newId = id + 1;
								sbComputersPermission.setPermissionId(newId);
								sbComputersPermission.setComputersId(sbComputers.get(0).getId().intValue());
								sbComputersPermission.setUpdateTime(new Date());
								sbComputersPermissionService.save(sbComputersPermission);
								
								SbComputersPermission s1 = new SbComputersPermission();
								s1.setComputersId(sbComputers.get(0).getId().intValue());
								s1.setPermissionId(id);
								s1.setUpdateTime(new Date());
								sbComputersPermissionService.save(s1);
								
								SbPermission parentSb = sbPermissionService.getById(id);
								if(parentSb.getParentId() != 0) {
									SbComputersPermission s2 = new SbComputersPermission();
									s2.setComputersId(sbComputers.get(0).getId().intValue());
									s2.setPermissionId(parentSb.getParentId().intValue());
									s2.setUpdateTime(new Date());
									sbComputersPermissionService.save(s2);
									
									SbPermission parentSbs = sbPermissionService.getById(parentSb.getParentId());
									if(parentSbs.getParentId() != 0) {
										SbComputersPermission s3 = new SbComputersPermission();
										s3.setComputersId(sbComputers.get(0).getId().intValue());
										s3.setPermissionId(parentSbs.getParentId().intValue());
										s3.setUpdateTime(new Date());
										sbComputersPermissionService.save(s3);
									}else {
										System.out.println("二级");
									}
								}
							}
							if(v.equals("●")) {
								// 读写 id加2   id=3
								int newId = id + 2;
								sbComputersPermission.setPermissionId(newId);
								sbComputersPermission.setComputersId(sbComputers.get(0).getId().intValue());
								sbComputersPermission.setUpdateTime(new Date());
								sbComputersPermissionService.save(sbComputersPermission);
								
								SbComputersPermission s1 = new SbComputersPermission();
								s1.setComputersId(sbComputers.get(0).getId().intValue());
								s1.setPermissionId(id);
								s1.setUpdateTime(new Date());
								sbComputersPermissionService.save(s1);
								
								SbPermission parentSb = sbPermissionService.getById(id);
								if(parentSb.getParentId() != 0) {
									SbComputersPermission s2 = new SbComputersPermission();
									s2.setComputersId(sbComputers.get(0).getId().intValue());
									s2.setPermissionId(parentSb.getParentId().intValue());
									s2.setUpdateTime(new Date());
									sbComputersPermissionService.save(s2);
									
									SbPermission parentSbs = sbPermissionService.getById(parentSb.getParentId());
									if(parentSbs.getParentId() != 0) {
										SbComputersPermission s3 = new SbComputersPermission();
										s3.setComputersId(sbComputers.get(0).getId().intValue());
										s3.setPermissionId(parentSbs.getParentId().intValue());
										s3.setUpdateTime(new Date());
										sbComputersPermissionService.save(s3);
									}else {
										System.out.println("二级");
									}
								}
							}
							if(v.equals("0")) {
								System.out.println("is 0");
							}
						}
					}else {
						System.out.println("is null");
					}
				}
			}
		}
		return ApiResult.ok();
    }
}

