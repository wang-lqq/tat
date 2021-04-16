package com.example.sb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sb.entity.SbComputersPermission;
import com.example.sb.entity.SbPermission;
import com.example.sb.mapper.SbComputersPermissionMapper;
import com.example.sb.param.SbComputersPermissionPageParam;
import com.example.sb.service.SbComputersPermissionService;
import com.example.sb.service.SbPermissionService;
import com.example.sb.vo.SbPermissionTreeVo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.system.enums.StateEnum;
import lombok.extern.slf4j.Slf4j;

/**
 *  服务实现类
 *
 * @author wanglonglong
 * @since 2021-04-08
 */
@Slf4j
@Service
public class SbComputersPermissionServiceImpl extends BaseServiceImpl<SbComputersPermissionMapper, SbComputersPermission> implements SbComputersPermissionService {

    @Autowired
    private SbComputersPermissionMapper sbComputersPermissionMapper;
    
    @Autowired
    private SbPermissionService sbPermissionService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSbComputersPermission(SbComputersPermission sbComputersPermission) throws Exception {
        return super.save(sbComputersPermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSbComputersPermission(SbComputersPermission sbComputersPermission) throws Exception {
        return super.updateById(sbComputersPermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSbComputersPermission(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<SbComputersPermission> getSbComputersPermissionPageList(SbComputersPermissionPageParam sbComputersPermissionPageParam) throws Exception {
        Page<SbComputersPermission> page = new PageInfo<>(sbComputersPermissionPageParam, OrderItem.desc(getLambdaColumn(SbComputersPermission::getCreateTime)));
        LambdaQueryWrapper<SbComputersPermission> wrapper = new LambdaQueryWrapper<>();
        IPage<SbComputersPermission> iPage = sbComputersPermissionMapper.selectPage(page, wrapper);
        return new Paging<SbComputersPermission>(iPage);
    }
    
    public boolean saveSysRolePermissionBatch(Integer computersId, SetUtils.SetView addSet) {
        List<SbComputersPermission> list = new ArrayList<>();
        addSet.forEach(id -> {
        	SbComputersPermission sbComputersPermission = new SbComputersPermission();
        	sbComputersPermission
                    .setComputersId(computersId)
                    .setPermissionId((Integer) id)
                    .setStatus(StateEnum.ENABLE.getCode())
                    .setCreateTime(new Date())
                    .setUpdateTime(new Date());
        		save(sbComputersPermission);
        });
		return true;
    }

	@Override
	public List<SbPermissionTreeVo> getTreeByComputersId(Integer computersId) {
		LambdaQueryWrapper<SbComputersPermission> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SbComputersPermission::getComputersId, computersId);
		List<SbComputersPermission> computersPermissions = list(wrapper);
		
		List<SbPermission> sbPermissions = sbPermissionService.list();
		List<SbPermissionTreeVo> trees = new ArrayList<>();
		for (SbPermission sbPermission : sbPermissions) {
			SbPermissionTreeVo vo = new SbPermissionTreeVo();
			BeanUtil.copyProperties(sbPermission, vo);
			trees.add(vo);
			for (SbComputersPermission sbComputersPermission : computersPermissions) {
				if(sbComputersPermission.getPermissionId().equals(sbPermission.getId())) {
					vo.setSpread(true);
					vo.setChecked(true);
				}
			}
		}
		List<SbPermissionTreeVo> treeList = sbPermissionService.listToTree2(trees);
//		for (SbPermissionTreeVo sbPermissionTreeVo : treeList) {
//			List<SbPermissionTreeVo> oneChildren = sbPermissionTreeVo.getChildren();
//			if(CollectionUtil.isNotEmpty(oneChildren)) {
//				sbPermissionTreeVo.setChecked(false);
//				for (SbPermissionTreeVo oneChr : oneChildren) {
//					if(CollectionUtil.isNotEmpty(oneChr.getChildren())) {
//						oneChr.setChecked(false);
//						for (SbPermissionTreeVo twoChr : oneChr.getChildren()) {
//							if(CollectionUtil.isNotEmpty(twoChr.getChildren())) {
//								twoChr.setChecked(false);
//							}
//						}
//					}
//				}
//			}
//		}
		return treeList;
	}

	@Override
	public List<SbPermissionTreeVo> queryTreeByComputersId(Integer computersId) {
		LambdaQueryWrapper<SbComputersPermission> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SbComputersPermission::getComputersId, computersId);
		List<SbComputersPermission> computersPermissions = sbComputersPermissionMapper.selectList(wrapper);
		if(CollectionUtil.isEmpty(computersPermissions)) {
			return new ArrayList<SbPermissionTreeVo>();
		}
		List<Integer> list = new ArrayList<>();
		for (SbComputersPermission sbComputersPermission : computersPermissions) {
			list.add(sbComputersPermission.getPermissionId());
		}
		
		LambdaQueryWrapper<SbPermission> sbWrapper = new LambdaQueryWrapper<>();
		sbWrapper.in(SbPermission::getId, list);
		List<SbPermission> sbPermissions = sbPermissionService.list(sbWrapper);
		
		List<SbPermissionTreeVo> trees = new ArrayList<>();
		for (SbPermission sbPermission : sbPermissions) {
			SbPermissionTreeVo vo = new SbPermissionTreeVo();
			BeanUtil.copyProperties(sbPermission, vo);
			trees.add(vo);
			for (SbComputersPermission sbComputersPermission : computersPermissions) {
				System.out.println(sbComputersPermission.getPermissionId() +"-"+ sbPermission.getId());
				if(sbComputersPermission.getPermissionId().equals(sbPermission.getId())) {
					vo.setSpread(true);
					vo.setChecked(true);
				}
			}
		}
		List<SbPermissionTreeVo> treeList = sbPermissionService.listToTree2(trees);
		return treeList;
	}
}
