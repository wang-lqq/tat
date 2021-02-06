/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.geekidea.springbootplus.scheduled;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.document.entity.Document;
import com.example.document.service.DocumentService;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author geekidea
 * @date 2020/3/16
 **/
@Slf4j
@Component
public class HelloScheduled {
	
    @Autowired
    private DocumentService documentService;

    /**
     * 每天凌晨1点执行一次：0 0 1 * * ?
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void hello() throws Exception {
    	LambdaQueryWrapper<Document> queryWrapper = new LambdaQueryWrapper<>();
    	queryWrapper.eq(Document::getRedDot, 1).eq(Document::getRedDotSystem, 1);
    	List<Document> list = documentService.list(queryWrapper);
    	for (Document document : list) {
    		Date now = new Date();
    		// 偏移3天
    		Date crateDate = DateUtil.offsetDay(document.getCreateTime(), 3);
    		if(now.compareTo(crateDate) >= 0) {
    			document.setRedDot(0);
    			document.setUpdateTime(now);
    			documentService.updateById(document);
    		}
		}
        log.debug("HelloScheduled...");
    }
}
