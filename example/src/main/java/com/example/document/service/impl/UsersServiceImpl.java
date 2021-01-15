package com.example.document.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.document.entity.Users;
import com.example.document.mapper.UsersMapper;
import com.example.document.param.UsersPageParam;
import com.example.document.service.UsersService;

import cn.hutool.core.collection.CollectionUtil;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import lombok.extern.slf4j.Slf4j;

/**
 *  服务实现类
 *
 * @author wanglonglong
 * @since 2021-01-07
 */
@Slf4j
@Service
public class UsersServiceImpl extends BaseServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUsers(Users users) throws Exception {
        return super.save(users);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUsers(Users users) throws Exception {
        return super.updateById(users);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteUsers(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<Users> getUsersPageList(UsersPageParam usersPageParam) throws Exception {
        Page<Users> page = new PageInfo<>(usersPageParam, OrderItem.desc(getLambdaColumn(Users::getCreateTime)));
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        IPage<Users> iPage = usersMapper.selectPage(page, wrapper);
        return new Paging<Users>(iPage);
    }

	@Override
	public Users login(Users user) {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("username", user.getUsername());
		columnMap.put("password", user.getPassword());
		List<Users> list = usersMapper.selectByMap(columnMap);
		if(CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

}
