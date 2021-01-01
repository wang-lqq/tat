package com.example.document.service.impl;

import com.example.document.entity.Category;
import com.example.document.mapper.CategoryMapper;
import com.example.document.service.CategoryService;
import com.example.document.param.CategoryPageParam;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCategory(Category category) throws Exception {
        return super.save(category);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCategory(Category category) throws Exception {
        return super.updateById(category);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCategory(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<Category> getCategoryPageList(CategoryPageParam categoryPageParam) throws Exception {
        Page<Category> page = new PageInfo<>(categoryPageParam, OrderItem.desc(getLambdaColumn(Category::getCreateTime)));
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        IPage<Category> iPage = categoryMapper.selectPage(page, wrapper);
        return new Paging<Category>(iPage);
    }

}
