package com.example.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.document.entity.Category;
import com.example.document.param.CategoryPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 类别 Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-01-01
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {


}
