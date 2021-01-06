package com.example.document.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.document.entity.Category;
import com.example.document.param.CategoryPageParam;
import com.example.document.service.CategoryService;

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
 * 类别 控制器
 *
 * @author wanglonglong
 * @since 2021-01-01
 */
@Slf4j
@RestController
@RequestMapping("/category")
@Module("document")
@Api(value = "类别API", tags = {"类别"})
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加类别
     */
    @PostMapping("/insert")
    @OperationLog(name = "添加类别", type = OperationLogType.ADD)
    @ApiOperation(value = "添加类别", response = ApiResult.class)
    public ApiResult<Boolean> addCategory(@Validated(Add.class) @RequestBody Category category) throws Exception {
    	category.setUpdateTime(new Date());
    	boolean flag = categoryService.saveCategory(category);
        return ApiResult.result(flag);
    }

    /**
     * 修改类别
     */
    @PostMapping("/update")
    @OperationLog(name = "修改类别", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改类别", response = ApiResult.class)
    public ApiResult<Boolean> updateCategory(@Validated(Update.class) @RequestBody Category category) throws Exception {
    	category.setUpdateTime(new Date());
    	boolean flag = categoryService.updateCategory(category);
        return ApiResult.result(flag);
    }

    /**
     * 删除类别
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除类别", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除类别", response = ApiResult.class)
    public ApiResult<Boolean> deleteCategory(@PathVariable("id") Long id) throws Exception {
        boolean flag = categoryService.deleteCategory(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取类别详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "类别详情", type = OperationLogType.INFO)
    @ApiOperation(value = "类别详情", response = Category.class)
    public ApiResult<Category> getCategory(@PathVariable("id") Long id) throws Exception {
        Category category = categoryService.getById(id);
        return ApiResult.ok(category);
    }

    /**
     * 类别分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "类别分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "类别分页列表", response = Category.class)
    public ApiResult<Paging<Category>> getCategoryPageList(@Validated @RequestBody CategoryPageParam categoryPageParam) throws Exception {
    	LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
    	String keyword = categoryPageParam.getKeyword();
    	if(!StringUtils.isEmpty(keyword)) {
    		keyword = StringEscapeUtils.unescapeHtml4(keyword);
    		JSONObject obj = JSONObject.parseObject(keyword);
    		if(!StringUtils.isEmpty(obj.getString("name"))) {
    			wrapper.like(Category::getName, obj.getString("name"));
    		}
    	}
    	Paging<Category> paging = categoryService.getCategoryPageList(categoryPageParam, wrapper);
        return ApiResult.ok(paging);
    }
    
    /**
     * 查询所有
     */
    @PostMapping("/getAll")
    @OperationLog(name = "类别列表", type = OperationLogType.LIST)
    @ApiOperation(value = "类别列表", response = Category.class)
    public ApiResult<List<Category>> getAll() throws Exception {
    	List<Category> categories = categoryService.list();
        return ApiResult.ok(categories);
    }
}

