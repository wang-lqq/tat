package com.example.document.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.document.entity.Document;
import com.example.document.param.DocumentPageParam;

import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 * 文档 服务类
 *
 * @author wanglonglong
 * @since 2021-01-01
 */
public interface DocumentService extends BaseService<Document> {

    /**
     * 保存
     *
     * @param document
     * @return
     * @throws Exception
     */
    boolean saveDocument(Document document) throws Exception;

    /**
     * 修改
     *
     * @param document
     * @return
     * @throws Exception
     */
    boolean updateDocument(Document document) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteDocument(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param documentQueryParam
     * @return
     * @throws Exception
     */
    Paging<Document> getDocumentPageList(DocumentPageParam documentPageParam) throws Exception;
    
    
    Paging<Document> getDocumentPageList(DocumentPageParam documentPageParam, LambdaQueryWrapper<Document> wrapper) throws Exception;

    List<Map<String, Object>> getList(Document document);
}
