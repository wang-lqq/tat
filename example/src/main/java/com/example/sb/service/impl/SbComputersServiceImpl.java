package com.example.sb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.SetUtils.SetView;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sb.entity.SbComputers;
import com.example.sb.entity.SbComputersPermission;
import com.example.sb.entity.SbComputersSoftware;
import com.example.sb.entity.SbSoftware;
import com.example.sb.mapper.SbComputersMapper;
import com.example.sb.mapper.SbComputersSoftwareMapper;
import com.example.sb.mapper.SbSoftwareMapper;
import com.example.sb.param.SbComputersPageParam;
import com.example.sb.service.SbComputersPermissionService;
import com.example.sb.service.SbComputersService;

import cn.hutool.core.date.DateUtil;
import io.geekidea.springbootplus.framework.common.exception.DaoException;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import lombok.extern.slf4j.Slf4j;

/**
 *  服务实现类
 *
 * @author wanglonglong
 * @since 2021-04-01
 */
@Slf4j
@Service
public class SbComputersServiceImpl extends BaseServiceImpl<SbComputersMapper, SbComputers> implements SbComputersService {

    @Autowired
    private SbComputersMapper sbComputersMapper;
    @Autowired
    private SbComputersPermissionService sbComputersPermissionService;
    @Autowired
    private SbSoftwareMapper sbSoftwareMapper;
    @Autowired
    private SbComputersSoftwareMapper sbComputersSoftwareMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSbComputers(SbComputers sbComputers) throws Exception {
        return super.save(sbComputers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSbComputers(SbComputers sbComputers) throws Exception {
        return super.updateById(sbComputers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSbComputers(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<SbComputers> getSbComputersPageList(SbComputersPageParam sbComputersPageParam) throws Exception {
        Page<SbComputers> page = new PageInfo<>(sbComputersPageParam, OrderItem.desc(getLambdaColumn(SbComputers::getCreateTime)));
        LambdaQueryWrapper<SbComputers> wrapper = new LambdaQueryWrapper<>();
        
        String keyword = sbComputersPageParam.getKeyword();
    	if(!StringUtils.isEmpty(keyword)) {
    		keyword = StringEscapeUtils.unescapeHtml4(keyword);
    		JSONObject obj = JSONObject.parseObject(keyword);
    		String fullName = obj.getString("fullName");
    		String ipAddress = obj.getString("ipAddress");
    		String departmentId = obj.getString("departmentId");
    		String newAssetcode = obj.getString("newAssetcode");
    		if(!StringUtils.isEmpty(fullName)) {
    			wrapper.like(SbComputers::getFullName, fullName);
    		}
			if(!StringUtils.isEmpty(ipAddress)) {
				wrapper.like(SbComputers::getIpAddress, ipAddress);
    		}
			if(!StringUtils.isEmpty(departmentId)) {
				wrapper.eq(SbComputers::getDepartmentId, departmentId);
			}
			if(!StringUtils.isEmpty(newAssetcode)) {
				wrapper.like(SbComputers::getNewAssetcode, newAssetcode);
			}
    	}
        IPage<SbComputers> iPage = sbComputersMapper.selectPage(page, wrapper);
        return new Paging<SbComputers>(iPage);
    }
    
    
    public boolean updateSbComputersPermission(List<Integer> permissionIds, Integer computersId) throws Exception {
        // 获取之前的权限id集合
    	LambdaQueryWrapper<SbComputersPermission> wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(SbComputersPermission::getComputersId, computersId);
    	List<SbComputersPermission> sbComputersPermissions = sbComputersPermissionService.list(wrapper);
    	List<Integer> beforeList = new ArrayList<>();
    	for (SbComputersPermission sbComputersPermission : sbComputersPermissions) {
    		beforeList.add(sbComputersPermission.getPermissionId());
		}
        // 差集计算
        // before：1,2,3,4,5,6
        // after： 1,2,3,4,7,8
        // 删除5,6 新增7,8
        // 此处真实删除，去掉deleted字段的@TableLogic注解
        Set<Integer> beforeSet = new HashSet<>(beforeList);
        Set<Integer> afterSet = new HashSet<>(permissionIds);
        SetView<Integer> deleteSet = SetUtils.difference(beforeSet, afterSet);
        SetView<Integer> addSet = SetUtils.difference(afterSet, beforeSet);
        log.debug("deleteSet = " + deleteSet);
        log.debug("addSet = " + addSet);

        if (CollectionUtils.isNotEmpty(deleteSet)) {
            // 删除权限关联
            LambdaUpdateWrapper<SbComputersPermission> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SbComputersPermission::getComputersId, computersId);
            updateWrapper.in(SbComputersPermission::getPermissionId, deleteSet);
            boolean deleteResult = sbComputersPermissionService.remove(updateWrapper);
            if (!deleteResult) {
                throw new DaoException("删除角色权限关系失败");
            }
        }

        if (CollectionUtils.isNotEmpty(addSet)) {
            // 新增权限关联
            boolean addResult = sbComputersPermissionService.saveSysRolePermissionBatch(computersId, addSet);
            if (!addResult) {
                throw new DaoException("新增角色权限关系失败");
            }
        }
        return true;
    }

	@Override
	public void setComputersSoftware(JSONObject jsonObject, Long computersId) {
		LambdaQueryWrapper<SbSoftware> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SbSoftware::getStatus, 1);
		List<SbSoftware> list = sbSoftwareMapper.selectList(wrapper);
		
		for (SbSoftware sbSoftware : list) {
			String value = jsonObject.getString(sbSoftware.getSoftwareEn());
			if(!StringUtils.isEmpty(value)) {
				SbComputersSoftware sbComputersSoftware = new SbComputersSoftware();
				sbComputersSoftware.setComputersId(computersId.intValue());
				sbComputersSoftware.setSoftwareId(sbSoftware.getId());
				sbComputersSoftware.setSoftwareValue(value);
				sbComputersSoftware.setCreateTime(new Date());
				sbComputersSoftware.setUpdateTime(new Date());
				sbComputersSoftwareMapper.insert(sbComputersSoftware);
			}
		}
	}

	@Override
	public void updateSoftware(JSONObject jsonObject, Long computersId) {
		LambdaQueryWrapper<SbSoftware> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SbSoftware::getStatus, 1);
		List<SbSoftware> list = sbSoftwareMapper.selectList(wrapper);
		
		LambdaQueryWrapper<SbComputersSoftware> wr = new LambdaQueryWrapper<>();
		wr.eq(SbComputersSoftware::getComputersId, computersId);
		List<SbComputersSoftware> computersSoftwares = sbComputersSoftwareMapper.selectList(wr);
		
		List<SbComputersSoftware> updateList = new ArrayList<>();
		for (SbSoftware sbSoftware : list) {
			String value = jsonObject.getString(sbSoftware.getSoftwareEn());
//			if(!StringUtils.isEmpty(value)) {
				SbComputersSoftware sbComputersSoftware = new SbComputersSoftware();
				sbComputersSoftware.setComputersId(computersId.intValue());
				sbComputersSoftware.setSoftwareId(sbSoftware.getId());
				sbComputersSoftware.setSoftwareValue(value);
				sbComputersSoftware.setCreateTime(new Date());
				sbComputersSoftware.setUpdateTime(new Date());
				updateList.add(sbComputersSoftware);
//			}
		}
		
		for (SbComputersSoftware sbComputersSoftware : computersSoftwares) {
			for (SbComputersSoftware up : updateList) {
				if(sbComputersSoftware.getSoftwareId() == up.getSoftwareId()) {
					if(!sbComputersSoftware.getSoftwareValue().equals(up.getSoftwareValue())) {
						sbComputersSoftware.setSoftwareValue(up.getSoftwareValue());
						sbComputersSoftwareMapper.updateById(sbComputersSoftware);
					}
				}
			}
		}
		
		List<SbComputersSoftware> saveList = updateList.stream()
		        .filter(item -> !computersSoftwares.stream()
		        .map(e -> e.getSoftwareId())
		        .collect(Collectors.toList())
		        .contains(item.getSoftwareId()))
		        .collect(Collectors.toList());
		for (SbComputersSoftware sbComputersSoftware : saveList) {
			sbComputersSoftwareMapper.insert(sbComputersSoftware);
		}
	}

	@Override
	public List<JSONObject> getList(JSONObject obj) {
        LambdaQueryWrapper<SbComputers> wrapper = new LambdaQueryWrapper<>();
        
		String fullName = obj.getString("fullName");
		String ipAddress = obj.getString("ipAddress");
		String departmentId = obj.getString("departmentId");
		String newAssetcode = obj.getString("newAssetcode");
		if(!StringUtils.isEmpty(fullName)) {
			wrapper.like(SbComputers::getFullName, fullName);
		}
		if(!StringUtils.isEmpty(ipAddress)) {
			wrapper.like(SbComputers::getIpAddress, ipAddress);
		}
		if(!StringUtils.isEmpty(departmentId)) {
			wrapper.eq(SbComputers::getDepartmentId, departmentId);
		}
		if(!StringUtils.isEmpty(newAssetcode)) {
			wrapper.like(SbComputers::getNewAssetcode, newAssetcode);
		}
		wrapper.eq(SbComputers::getStatus, 0);
		List<SbComputers> list = sbComputersMapper.selectList(wrapper);
		
		List<JSONObject> array = new ArrayList<>();
		for (SbComputers sbComputers : list) {
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(sbComputers);
			jsonObj.put("startDate", DateUtil.formatDate(sbComputers.getStartDate()));
			array.add(jsonObj);
		}
        return array;
	}
}
