package com.example.document.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.document.entity.Category;
import com.example.document.entity.Document;
import com.example.document.param.CategoryPageParam;
import com.example.document.param.DocumentPageParam;

import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 * 类别 服务类
 *
 * @author wanglonglong
 * @since 2021-01-01
 */
public interface CategoryService extends BaseService<Category> {

    /**
     * 保存
     *
     * @param category
     * @return
     * @throws Exception
     */
    boolean saveCategory(Category category) throws Exception;

    /**
     * 修改
     *
     * @param category
     * @return
     * @throws Exception
     */
    boolean updateCategory(Category category) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCategory(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param categoryQueryParam
     * @return
     * @throws Exception
     */
    Paging<Category> getCategoryPageList(CategoryPageParam categoryPageParam) throws Exception;
    
    Paging<Category> getCategoryPageList(CategoryPageParam categoryPageParam, LambdaQueryWrapper<Category> wrapper) throws Exception;
}
