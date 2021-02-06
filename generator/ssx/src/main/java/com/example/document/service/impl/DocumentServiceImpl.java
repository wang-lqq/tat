package com.example.document.service.impl;

import com.example.document.entity.Document;
import com.example.document.mapper.DocumentMapper;
import com.example.document.service.DocumentService;
import com.example.document.param.DocumentPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文档 服务实现类
 *
 * @author wanglonglong
 * @since 2021-01-26
 */
@Slf4j
@Service
public class DocumentServiceImpl extends BaseServiceImpl<DocumentMapper, Document> implements DocumentService {

    @Autowired
    private DocumentMapper documentMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveDocument(Document document) throws Exception {
        return super.save(document);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateDocument(Document document) throws Exception {
        return super.updateById(document);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteDocument(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<Document> getDocumentPageList(DocumentPageParam documentPageParam) throws Exception {
        Page<Document> page = new PageInfo<>(documentPageParam, OrderItem.desc(getLambdaColumn(Document::getCreateTime)));
        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        IPage<Document> iPage = documentMapper.selectPage(page, wrapper);
        return new Paging<Document>(iPage);
    }

}
