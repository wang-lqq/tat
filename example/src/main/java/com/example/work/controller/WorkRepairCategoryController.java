package com.example.work.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.work.entity.WorkRepairCategory;
import com.example.work.param.WorkRepairCategoryPageParam;
import com.example.work.service.WorkRepairCategoryService;

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
 * 报修类别表 控制器
 *
 * @author wanglonglong
 * @since 2021-02-20
 */
@Slf4j
@RestController
@RequestMapping("/workRepairCategory")
@Module("work")
@Api(value = "报修类别表API", tags = {"报修类别表"})
public class WorkRepairCategoryController extends BaseController {

    @Autowired
    private WorkRepairCategoryService workRepairCategoryService;

    /**
     * 添加报修类别表
     */
    @PostMapping("/add")
    @OperationLog(name = "添加报修类别表", type = OperationLogType.ADD)
    @ApiOperation(value = "添加报修类别表", response = ApiResult.class)
    public ApiResult<Boolean> addWorkRepairCategory(@Validated(Add.class) @RequestBody WorkRepairCategory workRepairCategory) throws Exception {
        boolean flag = workRepairCategoryService.saveWorkRepairCategory(workRepairCategory);
        return ApiResult.result(flag);
    }

    /**
     * 修改报修类别表
     */
    @PostMapping("/update")
    @OperationLog(name = "修改报修类别表", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改报修类别表", response = ApiResult.class)
    public ApiResult<Boolean> updateWorkRepairCategory(@Validated(Update.class) @RequestBody WorkRepairCategory workRepairCategory) throws Exception {
        boolean flag = workRepairCategoryService.updateWorkRepairCategory(workRepairCategory);
        return ApiResult.result(flag);
    }

    /**
     * 删除报修类别表
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除报修类别表", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除报修类别表", response = ApiResult.class)
    public ApiResult<Boolean> deleteWorkRepairCategory(@PathVariable("id") Long id) throws Exception {
        boolean flag = workRepairCategoryService.deleteWorkRepairCategory(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取报修类别表详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "报修类别表详情", type = OperationLogType.INFO)
    @ApiOperation(value = "报修类别表详情", response = WorkRepairCategory.class)
    public ApiResult<WorkRepairCategory> getWorkRepairCategory(@PathVariable("id") Long id) throws Exception {
        WorkRepairCategory workRepairCategory = workRepairCategoryService.getById(id);
        return ApiResult.ok(workRepairCategory);
    }

    /**
     * 报修类别表分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "报修类别表分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "报修类别表分页列表", response = WorkRepairCategory.class)
    public ApiResult<Paging<WorkRepairCategory>> getWorkRepairCategoryPageList(@Validated @RequestBody WorkRepairCategoryPageParam workRepairCategoryPageParam) throws Exception {
        Paging<WorkRepairCategory> paging = workRepairCategoryService.getWorkRepairCategoryPageList(workRepairCategoryPageParam);
        return ApiResult.ok(paging);
    }
    
    /**
     * 报修类别表列表
     */
    @PostMapping("/getList")
    @OperationLog(name = "报修类别表列表", type = OperationLogType.LIST)
    @ApiOperation(value = "报修类别表列表", response = WorkRepairCategory.class)
    public ApiResult<List<WorkRepairCategory>> getWorkRepairCategoryList() throws Exception {
    	List<WorkRepairCategory> list = workRepairCategoryService.list();
        return ApiResult.ok(list);
    }
}

