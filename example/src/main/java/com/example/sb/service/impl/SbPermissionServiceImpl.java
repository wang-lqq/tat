package com.example.sb.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sb.entity.SbPermission;
import com.example.sb.mapper.SbPermissionMapper;
import com.example.sb.param.SbPermissionPageParam;
import com.example.sb.service.SbPermissionService;
import com.example.sb.vo.SbPermissionTreeVo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import lombok.extern.slf4j.Slf4j;

/**
 *  服务实现类
 *
 * @author wanglonglong
 * @since 2021-04-07
 */
@Slf4j
@Service
public class SbPermissionServiceImpl extends BaseServiceImpl<SbPermissionMapper, SbPermission> implements SbPermissionService {

    @Autowired
    private SbPermissionMapper sbPermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSbPermission(SbPermission sbPermission) throws Exception {
        return super.save(sbPermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSbPermission(SbPermission sbPermission) throws Exception {
        return super.updateById(sbPermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSbPermission(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<SbPermission> getSbPermissionPageList(SbPermissionPageParam sbPermissionPageParam) throws Exception {
        Page<SbPermission> page = new PageInfo<>(sbPermissionPageParam, OrderItem.desc(getLambdaColumn(SbPermission::getCreateTime)));
        LambdaQueryWrapper<SbPermission> wrapper = new LambdaQueryWrapper<>();
        
        String keyword = sbPermissionPageParam.getKeyword();
    	if(!StringUtils.isEmpty(keyword)) {
    		keyword = StringEscapeUtils.unescapeHtml4(keyword);
    		JSONObject obj = JSONObject.parseObject(keyword);
    		String title = obj.getString("title");
			if(!StringUtils.isEmpty(title)) {
				wrapper.like(SbPermission::getTitle, title);
			}
    	}
    	IPage<SbPermission> iPage = sbPermissionMapper.selectPage(page, wrapper);
        return new Paging<SbPermission>(iPage);
    }

	@Override
	public List<SbPermissionTreeVo> tree() {
		List<SbPermission> sbPermissions = list();
		List<SbPermissionTreeVo> trees = new ArrayList<>();
		for (SbPermission sbPermission : sbPermissions) {
			SbPermissionTreeVo vo = new SbPermissionTreeVo();
			BeanUtil.copyProperties(sbPermission, vo);
			trees.add(vo);
		}
		trees = list2Tree(trees, 0);
		return trees;
	}
    
    public List<SbPermissionTreeVo> list2Tree(List<SbPermissionTreeVo> list, Integer pId) {
        List<SbPermissionTreeVo> tree = new ArrayList<>();
        Iterator<SbPermissionTreeVo> it = list.iterator();
        while (it.hasNext()) {
        	SbPermissionTreeVo m = it.next();
            if (m.getParentId() == pId) {
                tree.add(m);
                // 已添加的元素删除掉
                it.remove();
            }
        }
        // 寻找子元素
        tree.forEach(n -> n.children = list2Tree(list, n.getId()));
        return tree;
    }

	@Override
	public List<SbPermissionTreeVo> openTree() {
		List<SbPermission> sbPermissions = list();
		List<SbPermissionTreeVo> trees = new ArrayList<>();
		for (SbPermission sbPermission : sbPermissions) {
			SbPermissionTreeVo vo = new SbPermissionTreeVo();
			BeanUtil.copyProperties(sbPermission, vo);
			vo.setSpread(true);
			trees.add(vo);
		}
		trees = list2Tree(trees, 0);
		return trees;
	}
}
