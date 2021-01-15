package com.example.document.controller;

import java.util.Date;

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
import com.example.document.entity.Document;
import com.example.document.param.DocumentPageParam;
import com.example.document.service.CategoryService;
import com.example.document.service.DocumentService;

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
 * 文档 控制器
 *
 * @author wanglonglong
 * @since 2021-01-01
 */
@Slf4j
@RestController
@RequestMapping("/document")
@Module("document")
@Api(value = "文档API", tags = {"文档"})
public class DocumentController extends BaseController {

    @Autowired
    private DocumentService documentService;
    
    @Autowired
    private CategoryService categoryService;

    /**
     * 添加文档
     */
    @PostMapping("/insert")
    @OperationLog(name = "添加文档", type = OperationLogType.ADD)
    @ApiOperation(value = "添加文档", response = ApiResult.class)
    public ApiResult<Boolean> addDocument(@Validated(Add.class) @RequestBody Document document) throws Exception {
    	String htmlStr =StringEscapeUtils.unescapeHtml4(document.getContent());
    	Category category = categoryService.getById(document.getCategoryId());
    	document.setContent(htmlStr);
    	document.setCategoryName(category.getName());
    	document.setUpdateTime(new Date());
    	boolean flag = false;
    	if(document.getId() != null && document.getId() != 0){
    		flag = documentService.updateDocument(document);
    	}else {
    		flag = documentService.saveDocument(document);
    	}
        return ApiResult.result(flag);
    }

    /**
     * 修改文档
     */
    @PostMapping("/update")
    @OperationLog(name = "修改文档", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改文档", response = ApiResult.class)
    public ApiResult<Boolean> updateDocument(@Validated(Update.class) @RequestBody Document document) throws Exception {
    	if(document.getCategoryId() != null && document.getCategoryId() != 0) {
    		Category category = categoryService.getById(document.getCategoryId());
        	document.setCategoryName(category.getName());
    	}
    	document.setUpdateTime(new Date());
    	boolean flag = documentService.updateDocument(document);
        return ApiResult.result(flag);
    }

    /**
     * 删除文档
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除文档", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除文档", response = ApiResult.class)
    public ApiResult<Boolean> deleteDocument(@PathVariable("id") Long id) throws Exception {
        boolean flag = documentService.deleteDocument(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取文档详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "文档详情", type = OperationLogType.INFO)
    @ApiOperation(value = "文档详情", response = Document.class)
    public ApiResult<Document> getDocument(@PathVariable("id") Long id) throws Exception {
        Document document = documentService.getById(id);
        return ApiResult.ok(document);
    }

    /**
     * 文档分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "文档分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "文档分页列表", response = Document.class)
    public ApiResult<Paging<Document>> getDocumentPageList(@Validated @RequestBody DocumentPageParam documentPageParam
    		) throws Exception {
    	LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
    	String keyword = documentPageParam.getKeyword();
    	if(!StringUtils.isEmpty(keyword)) {
    		keyword = StringEscapeUtils.unescapeHtml4(keyword);
    		JSONObject obj = JSONObject.parseObject(keyword);
    		Document document = new Document();
    		if(!StringUtils.isEmpty(obj.getString("CategoryId"))) {
    			document.setCategoryId(Integer.parseInt(obj.getString("CategoryId")));
    		}
    		if(!StringUtils.isEmpty(obj.getString("title"))) {
    			wrapper.like(Document::getTitle, obj.getString("title")).or().like(Document::getContent, obj.getString("title"));
    		}
    		wrapper.setEntity(document);
    	}
    	Paging<Document> paging = documentService.getDocumentPageList(documentPageParam, wrapper);
        return ApiResult.ok(paging);
    }

}

