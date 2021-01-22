package com.example.document.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.document.entity.Category;
import com.example.document.entity.Document;
import com.example.document.mapper.CategoryMapper;
import com.example.document.mapper.DocumentMapper;
import com.example.document.param.CategoryPageParam;
import com.example.document.service.CategoryService;

import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import lombok.extern.slf4j.Slf4j;

/**
 * 类别 服务实现类
 *
 * @author wanglonglong
 * @since 2021-01-01
 */
@Slf4j
@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    
    @Autowired
    private DocumentMapper documentMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCategory(Category category) throws Exception {
        return super.save(category);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCategory(Category category) throws Exception {
    	boolean b = super.updateById(category);
    	if(b) {
    		Map<String, Object> columnMap = new HashMap<>();
        	columnMap.put("category_id", category.getId());
        	List<Document> list = documentMapper.selectByMap(columnMap);
        	for (Document document : list) {
        		document.setCategoryName(category.getName());
        		documentMapper.updateById(document);
    		}
    	}
    	return b;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCategory(Long id) throws Exception {
    	// 分类有文档不能删除
    	LambdaQueryWrapper<Document> queryWrapper = new LambdaQueryWrapper<>();
    	queryWrapper.eq(Document::getCategoryId, id);
    	Document document = documentMapper.selectOne(queryWrapper);
    	if(document != null) {
    		return false;
    	}
        return super.removeById(id);
    }

    @Override
    public Paging<Category> getCategoryPageList(CategoryPageParam categoryPageParam) throws Exception {
        Page<Category> page = new PageInfo<>(categoryPageParam, OrderItem.desc(getLambdaColumn(Category::getCreateTime)));
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        IPage<Category> iPage = categoryMapper.selectPage(page, wrapper);
        return new Paging<Category>(iPage);
    }

	@Override
	public Paging<Category> getCategoryPageList(CategoryPageParam categoryPageParam,
			LambdaQueryWrapper<Category> wrapper) throws Exception {
		Page<Category> page = new PageInfo<>(categoryPageParam, OrderItem.desc(getLambdaColumn(Category::getCreateTime)));
        IPage<Category> iPage = categoryMapper.selectPage(page, wrapper);
        return new Paging<Category>(iPage);
	}

}
