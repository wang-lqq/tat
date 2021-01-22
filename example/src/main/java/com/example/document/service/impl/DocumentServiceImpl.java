package com.example.document.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.document.entity.Category;
import com.example.document.entity.Document;
import com.example.document.mapper.CategoryMapper;
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
    @Autowired
    private CategoryMapper categoryMapper;

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
		
		List<Integer> categoryIdList = new ArrayList<>();
		List<Category> categoryList = categoryMapper.selectList(Wrappers.emptyWrapper());
		for (Category category : categoryList) {
			categoryIdList.add(category.getId());
		}
		wrapper.in(Document::getCategoryId, categoryIdList);
        IPage<Document> iPage = documentMapper.selectPage(page, wrapper);
        return new Paging<Document>(iPage);
	}

	@Override
	public List<Map<String, Object>> getList(Document document) {
		List<Map<String, Object>> list = new ArrayList<>();
		
		LambdaQueryWrapper<Category> wrapperCategory = new LambdaQueryWrapper<>();
		wrapperCategory.orderByDesc(Category::getSort);
		List<Category> categories = categoryMapper.selectList(wrapperCategory);
		for (Category category : categories) {
			LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Document::getCategoryId, category.getId()).orderByDesc(Document::getSort);
			if(!StringUtils.isEmpty(document.getTitle())) {
				wrapper.like(Document::getTitle, document.getTitle());
			}
			List<Document> documents = documentMapper.selectList(wrapper);
			if(!CollectionUtils.isEmpty(documents)) {
				Map<String, Object> data = new HashMap<>();
				data.put("categoryName", category.getName());
				data.put("documents", documents);
				list.add(data);
			}
		}
		return list;
	}
}
