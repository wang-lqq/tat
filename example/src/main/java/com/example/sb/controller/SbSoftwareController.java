package com.example.sb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.sb.entity.SbComputersSoftware;
import com.example.sb.entity.SbSoftware;
import com.example.sb.param.SbSoftwarePageParam;
import com.example.sb.service.SbComputersSoftwareService;
import com.example.sb.service.SbSoftwareService;

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
 *  控制器
 *
 * @author wanglonglong
 * @since 2021-04-12
 */
@Slf4j
@RestController
@RequestMapping("/sbSoftware")
@Module("sb")
@Api(value = "API", tags = {""})
public class SbSoftwareController extends BaseController {

    @Autowired
    private SbSoftwareService sbSoftwareService;
    
    @Autowired
    private SbComputersSoftwareService sbComputersSoftwareService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addSbSoftware(@Validated(Add.class) @RequestBody SbSoftware sbSoftware) throws Exception {
    	sbSoftware.setUpdateTime(new Date());
    	if(!StringUtils.isEmpty(sbSoftware.getSoftwareName())){
    		String softwareName= HtmlUtils.htmlUnescape(sbSoftware.getSoftwareName());
    		sbSoftware.setSoftwareName(softwareName);
    	}
        boolean flag = sbSoftwareService.saveSbSoftware(sbSoftware);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateSbSoftware(@Validated(Update.class) @RequestBody SbSoftware sbSoftware) throws Exception {
    	if(!StringUtils.isEmpty(sbSoftware.getSoftwareName())){
    		String softwareName= HtmlUtils.htmlUnescape(sbSoftware.getSoftwareName());
    		sbSoftware.setSoftwareName(softwareName);
    	}
    	sbSoftware.setUpdateTime(new Date());
        boolean flag = sbSoftwareService.updateSbSoftware(sbSoftware);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteSbSoftware(@PathVariable("id") Long id) throws Exception {
    	// 逻辑删除
    	SbSoftware sbSoftware = sbSoftwareService.getById(id);
    	sbSoftware.setUpdateTime(new Date());
    	sbSoftware.setStatus(-1);
    	boolean flag = sbSoftwareService.updateSbSoftware(sbSoftware);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = SbSoftware.class)
    public ApiResult<SbSoftware> getSbSoftware(@PathVariable("id") Long id) throws Exception {
        SbSoftware sbSoftware = sbSoftwareService.getById(id);
        return ApiResult.ok(sbSoftware);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = SbSoftware.class)
    public ApiResult<Paging<SbSoftware>> getSbSoftwarePageList(@Validated @RequestBody SbSoftwarePageParam sbSoftwarePageParam) throws Exception {
        Paging<SbSoftware> paging = sbSoftwareService.getSbSoftwarePageList(sbSoftwarePageParam);
        return ApiResult.ok(paging);
    }
    
    /**
     * 分页列表
     */
    @PostMapping("/list")
    @OperationLog(name = "列表", type = OperationLogType.LIST)
    @ApiOperation(value = "列表", response = SbSoftware.class)
    public ApiResult<List<SbSoftware>> list() throws Exception {
    	LambdaQueryWrapper<SbSoftware> wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(SbSoftware::getStatus, 1);
    	wrapper.eq(SbSoftware::getType, "input");
    	wrapper.orderByDesc(SbSoftware::getSort);
    	List<SbSoftware> list = sbSoftwareService.list(wrapper);// 文本框list
    	
    	LambdaQueryWrapper<SbSoftware> textWr = new LambdaQueryWrapper<>();
    	textWr.eq(SbSoftware::getStatus, 1);
    	textWr.eq(SbSoftware::getType, "textarea");
    	textWr.orderByDesc(SbSoftware::getSort);
    	List<SbSoftware> listText = sbSoftwareService.list(textWr);// 文本域list
    	
    	
    	list.addAll(listText);
        return ApiResult.ok(list);
    }
    
    /**
     * 列表
     */
    @GetMapping("/listAndValue/{computersId}")
    @OperationLog(name = "列表", type = OperationLogType.LIST)
    @ApiOperation(value = "列表", response = SbSoftware.class)
    public ApiResult<List<JSONObject>> listAndValue(@PathVariable("computersId") Long computersId) throws Exception {
    	LambdaQueryWrapper<SbSoftware> wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(SbSoftware::getStatus, 1);
    	wrapper.eq(SbSoftware::getType, "input");
    	wrapper.orderByDesc(SbSoftware::getSort);
    	List<SbSoftware> list = sbSoftwareService.list(wrapper);// 文本框list
    	
    	LambdaQueryWrapper<SbSoftware> textWr = new LambdaQueryWrapper<>();
    	textWr.eq(SbSoftware::getStatus, 1);
    	textWr.eq(SbSoftware::getType, "textarea");
    	textWr.orderByDesc(SbSoftware::getSort);
    	List<SbSoftware> listText = sbSoftwareService.list(textWr);// 文本域list
    	
    	list.addAll(listText);
    	
    	LambdaQueryWrapper<SbComputersSoftware> wr = new LambdaQueryWrapper<>();
    	wr.eq(SbComputersSoftware::getComputersId, computersId);
    	List<SbComputersSoftware> sbComputersSoftwares = sbComputersSoftwareService.list(wr);
    	
    	List<JSONObject> array = new ArrayList<>();
    	for (SbSoftware sbSoftware : list) {
    		JSONObject jo= (JSONObject) JSONObject.toJSON(sbSoftware);
			for (SbComputersSoftware sbComputersSoftware : sbComputersSoftwares) {
				if(sbComputersSoftware.getSoftwareId().equals(sbSoftware.getId())) {
					jo.put("value", sbComputersSoftware.getSoftwareValue());
				}
			}
			if(StringUtils.isEmpty(jo.getString("value"))) {
				jo.put("value", "");
			}
			array.add(jo);
		}
        return ApiResult.ok(array);
    }
}

