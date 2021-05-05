package com.example.sb.service.impl;

import java.util.ArrayList;
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
        wrapper.ne(SbPermission::getStatus, -1);
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
		LambdaQueryWrapper<SbPermission> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByAsc(SbPermission::getId);
		wrapper.ne(SbPermission::getStatus, -1);
		List<SbPermission> sbPermissions = list(wrapper);
		
		List<SbPermissionTreeVo> trees = new ArrayList<>();
		for (SbPermission sbPermission : sbPermissions) {
			SbPermissionTreeVo vo = new SbPermissionTreeVo();
			BeanUtil.copyProperties(sbPermission, vo);
			if(sbPermission.getParentId() == 0) {
				vo.setSpread(true);
			}
			trees.add(vo);
		}
		trees = listToTree2(trees);
		return trees;
	}
    
	@Override
	public List<SbPermissionTreeVo> openTree(Integer id) {
		if(id != null && id != 0) {
			return treeById(id);
		}
		LambdaQueryWrapper<SbPermission> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByAsc(SbPermission::getId);
		wrapper.ne(SbPermission::getStatus, -1);
		List<SbPermission> sbPermissions = list(wrapper);
		
		List<SbPermissionTreeVo> trees = new ArrayList<>();
		for (SbPermission sbPermission : sbPermissions) {
			SbPermissionTreeVo vo = new SbPermissionTreeVo();
			BeanUtil.copyProperties(sbPermission, vo);
			if(sbPermission.getParentId() == 0) {
				vo.setSpread(true);
			}
			trees.add(vo);
		}
		trees = listToTree2(trees);
		return trees;
	}
	
	public List<SbPermissionTreeVo> listToTree2(List<SbPermissionTreeVo> list) {
	    List<SbPermissionTreeVo> tree = new ArrayList<>();
	    for (SbPermissionTreeVo user : list) {
	        //找到根节点
	        if (user.getParentId() == null || user.getParentId().equals(0)) { // db01 db02
	            tree.add(findChildren(user, list));
	        }
	    }
	    return tree;
	}
	
	/**
	 * 查找子节点
	 * @param user
	 * @param list
	 * @return
	 */
	private SbPermissionTreeVo findChildren(SbPermissionTreeVo user, List<SbPermissionTreeVo> list) {
	    List<SbPermissionTreeVo> children = new ArrayList<>();
	    for (SbPermissionTreeVo node : list) {
	        if (node.getParentId().equals(user.getId())) {
	            //递归调用
	            children.add(findChildren(node, list));
	        }
	    }
	    if(CollectionUtil.isNotEmpty(children)) {
	    	user.setChecked(false);
	    }
	    user.setChildren(children);
	    return user;
	}

	@Override
	public List<SbPermissionTreeVo> treeById(Integer id) {
		SbPermission sb = sbPermissionMapper.selectById(id);
		SbPermission parentSb = new SbPermission();
		SbPermission parentSbs = new SbPermission();
		if(sb.getParentId() != null && sb.getParentId() != 0) {
			parentSb = sbPermissionMapper.selectById(sb.getParentId());
		}
		if(parentSb.getParentId() != null && parentSb.getParentId() != 0) {
			parentSbs = sbPermissionMapper.selectById(parentSb.getParentId());
		}
		
		LambdaQueryWrapper<SbPermission> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByAsc(SbPermission::getId);
		wrapper.ne(SbPermission::getStatus, -1);
		List<SbPermission> sbPermissions = list(wrapper);
		
		List<SbPermissionTreeVo> trees = new ArrayList<>();
		for (SbPermission sbPermission : sbPermissions) {
			SbPermissionTreeVo vo = new SbPermissionTreeVo();
			BeanUtil.copyProperties(sbPermission, vo);
			if(sb.getParentId() != null && vo.getId() == sb.getParentId().intValue()) {
				vo.setSpread(true);
			}
			if(parentSb.getParentId() != null && vo.getId() == parentSb.getParentId().intValue()) {
				vo.setSpread(true);
			}
			if(parentSbs.getParentId() != null && vo.getId() == parentSbs.getParentId().intValue()) {
				vo.setSpread(true);
			}
			trees.add(vo);
		}
		trees = listToTree2(trees);
		return trees;
	}
}
