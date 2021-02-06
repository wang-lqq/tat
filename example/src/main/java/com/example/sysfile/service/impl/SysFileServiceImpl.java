package com.example.sysfile.service.impl;

import com.example.sysfile.entity.SysFile;
import com.example.sysfile.mapper.SysFileMapper;
import com.example.sysfile.service.SysFileService;
import com.example.sysfile.param.SysFilePageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import io.geekidea.springbootplus.config.properties.SpringBootPlusProperties;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  服务实现类
 *
 * @author wanglonglong
 * @since 2021-01-20
 */
@Slf4j
@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    @Autowired
    private SysFileMapper sysFileMapper;
    
    @Autowired
    private SpringBootPlusProperties springBootPlusProperties;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysFile(SysFile sysFile) throws Exception {
        return super.save(sysFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysFile(SysFile sysFile) throws Exception {
    	SysFile oldSysFile = sysFileMapper.selectById(sysFile.getId());
    	// 删除旧的上传视频
    	if(!StringUtils.isEmpty(sysFile.getFileUrl()) && !StringUtils.isEmpty(oldSysFile.getFileUrl())) {
    		if(!sysFile.getFileUrl().equals(oldSysFile.getFileUrl())) {
    			int lastIndexOf = oldSysFile.getFileUrl().lastIndexOf("/");
    	    	String suffix = oldSysFile.getFileUrl().substring(lastIndexOf+1);
    			String str = springBootPlusProperties.getUploadPath() + suffix;
    			File file = new File(str);
    			FileUtils.deleteQuietly(file);
    		}
    	}
    	// 删除旧的上传封面
    	if(!StringUtils.isEmpty(sysFile.getFrontUrl()) && !StringUtils.isEmpty(oldSysFile.getFrontUrl())) {
    		if(!sysFile.getFrontUrl().equals(oldSysFile.getFrontUrl())) {
    			int lastIndexOf = oldSysFile.getFrontUrl().lastIndexOf("/");
    	    	String suffix = oldSysFile.getFrontUrl().substring(lastIndexOf+1);
    			String str = springBootPlusProperties.getUploadPath() + suffix;
    			File file = new File(str);
    			FileUtils.deleteQuietly(file);
    		}
    	}
    	sysFile.setUpdateTime(new Date());
        return super.updateById(sysFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysFile(Long id) throws Exception {
    	SysFile oldSysFile = sysFileMapper.selectById(id);
    	if(!StringUtils.isEmpty(oldSysFile.getFileUrl())) {
    		int lastIndexOf = oldSysFile.getFileUrl().lastIndexOf("/");
	    	String suffix = oldSysFile.getFileUrl().substring(lastIndexOf+1);
			String str = springBootPlusProperties.getUploadPath() + suffix;
			File file = new File(str);
			FileUtils.deleteQuietly(file);
    	}
    	if(!StringUtils.isEmpty(oldSysFile.getFrontUrl())) {
    		int lastIndexOf = oldSysFile.getFrontUrl().lastIndexOf("/");
	    	String suffix = oldSysFile.getFrontUrl().substring(lastIndexOf+1);
			String str = springBootPlusProperties.getUploadPath() + suffix;
			File file = new File(str);
			FileUtils.deleteQuietly(file);
    	}
        return super.removeById(id);
    }

    @Override
    public Paging<SysFile> getSysFilePageList(SysFilePageParam sysFilePageParam) throws Exception {
        Page<SysFile> page = new PageInfo<>(sysFilePageParam, OrderItem.desc(getLambdaColumn(SysFile::getCreateTime)));
        LambdaQueryWrapper<SysFile> wrapper = new LambdaQueryWrapper<>();
        IPage<SysFile> iPage = sysFileMapper.selectPage(page, wrapper);
        return new Paging<SysFile>(iPage);
    }

}
