package com.example.document.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.document.entity.Document;
import com.example.document.service.DocumentService;

import io.geekidea.springbootplus.framework.common.api.ApiResult;
import io.geekidea.springbootplus.framework.common.controller.BaseController;
import io.geekidea.springbootplus.framework.log.annotation.Module;
import io.geekidea.springbootplus.framework.log.annotation.OperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


	
@Slf4j
@RestController
@RequestMapping("/a/document")
@Module("document")
@Api(value = "文档API", tags = {"文档"})
public class ApiDocumentController extends BaseController {
	
    
    @Autowired
    private DocumentService documentService;
    
    /**
     * 文档列表
     */
    @PostMapping("/getList")
    @OperationLog(name = "文档列表", type = OperationLogType.LIST)
    @ApiOperation(value = "文档列表", response = Document.class)
    public ApiResult<List<Map<String, Object>>> getList(@Validated @RequestBody Document document
    		) throws Exception {
    	List<Map<String, Object>> data = documentService.getList(document);
        return ApiResult.ok(data);
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
}
