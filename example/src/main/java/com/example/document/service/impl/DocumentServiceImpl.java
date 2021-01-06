package com.example.document.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.document.entity.Document;
import com.example.document.mapper.DocumentMapper;
import com.example.document.param.DocumentPageParam;
import com.example.document.service.DocumentService;

import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import lombok.extern.slf4j.Slf4j;

/**
 * 文档 服务实现类
 *
 * @author wanglonglong
 * @since 2021-01-01
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

	@Override
	public Paging<Document> getDocumentPageList(DocumentPageParam documentPageParam,
			LambdaQueryWrapper<Document> wrapper) throws Exception {
		Page<Document> page = new PageInfo<>(documentPageParam, OrderItem.desc(getLambdaColumn(Document::getCreateTime)));
        IPage<Document> iPage = documentMapper.selectPage(page, wrapper);
        return new Paging<Document>(iPage);
	}
}
