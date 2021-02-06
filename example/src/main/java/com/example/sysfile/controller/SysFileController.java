package com.example.sysfile.controller;

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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.sysfile.entity.SysFile;
import com.example.sysfile.param.SysFilePageParam;
import com.example.sysfile.service.SysFileService;

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
 * @since 2021-01-20
 */
@Slf4j
@RestController
@RequestMapping("/sysFile")
@Module("sysfile")
@Api(value = "API", tags = {""})
public class SysFileController extends BaseController {

    @Autowired
    private SysFileService sysFileService;

    /**
     * 添加
     */
    @PostMapping("/insert")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addSysFile(@Validated(Add.class) @RequestBody SysFile sysFile) throws Exception {
    	if(StringUtils.isEmpty(sysFile.getTitle()) || StringUtils.isEmpty(sysFile.getFileUrl())) {
    		return ApiResult.result(false);
    	}
    	int lastIndexOf = sysFile.getFileUrl().lastIndexOf(".");
    	String suffix = sysFile.getFileUrl().substring(lastIndexOf+1);
    	sysFile.setFileType(suffix);
        boolean flag = sysFileService.saveSysFile(sysFile);
        return ApiResult.result(flag);
    }
    
    /**
     * 添加
     */
    @PostMapping("/insertList")
    @OperationLog(name = "添加列表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加列表", response = ApiResult.class)
    public ApiResult<Boolean> insertList(@RequestBody List<SysFile> sysFileList) throws Exception {
    	for (SysFile sysFile : sysFileList) {
    		if(StringUtils.isEmpty(sysFile.getFileUrl())) {
    			continue;
    		}
    		if(sysFile.getId() != null && sysFile.getId() != 0) {
    			sysFile.setUpdateTime(new Date());
    			sysFileService.updateById(sysFile);
    		}else {
    			int lastIndexOf = sysFile.getFileUrl().lastIndexOf(".");
    			String suffix = sysFile.getFileUrl().substring(lastIndexOf+1);
    			sysFile.setFileType(suffix);
    			sysFileService.saveSysFile(sysFile);
    		}
		}
        return ApiResult.result(true);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateSysFile(@Validated(Update.class) @RequestBody SysFile sysFile) throws Exception {
        boolean flag = sysFileService.updateSysFile(sysFile);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysFile(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysFileService.deleteSysFile(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = SysFile.class)
    public ApiResult<SysFile> getSysFile(@PathVariable("id") Long id) throws Exception {
        SysFile sysFile = sysFileService.getById(id);
        return ApiResult.ok(sysFile);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = SysFile.class)
    public ApiResult<Paging<SysFile>> getSysFilePageList(@Validated @RequestBody SysFilePageParam sysFilePageParam) throws Exception {
        Paging<SysFile> paging = sysFileService.getSysFilePageList(sysFilePageParam);
        return ApiResult.ok(paging);
    }
    
    /**
     * 列表
     */
    @PostMapping("/getList")
    @OperationLog(name = "列表", type = OperationLogType.LIST)
    @ApiOperation(value = "列表", response = SysFile.class)
    public ApiResult<List<SysFile>> getSysFileList(@Validated @RequestBody SysFile sysFile) throws Exception {
    	if(sysFile.getDocumentId() == 0) {
    		return ApiResult.ok(null);
        }
    	LambdaQueryWrapper<SysFile> queryWrapper = new LambdaQueryWrapper<>();
    	queryWrapper.eq(SysFile::getDocumentId, sysFile.getDocumentId());
    	queryWrapper.orderByAsc(SysFile::getId);
    	List<SysFile> list = sysFileService.list(queryWrapper);
        return ApiResult.ok(list);
    }
}

