package com.example.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.document.entity.Users;
import com.example.document.param.UsersPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 *  Mapper 接口
 *
 * @author wanglonglong
 * @since 2021-01-07
 */
@Repository
public interface UsersMapper extends BaseMapper<Users> {


}
