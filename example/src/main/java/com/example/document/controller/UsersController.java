package com.example.document.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.document.entity.Users;
import com.example.document.param.UsersPageParam;
import com.example.document.service.UsersService;

import io.geekidea.springbootplus.framework.common.api.ApiCode;
import io.geekidea.springbootplus.framework.common.api.ApiResult;
import io.geekidea.springbootplus.framework.common.controller.BaseController;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.validator.groups.Add;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;
import io.geekidea.springbootplus.framework.log.annotation.Module;
import io.geekidea.springbootplus.framework.log.annotation.OperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 *  控制器
 *
 * @author wanglonglong
 * @since 2021-01-07
 */
@Slf4j
@RestController
@RequestMapping("/users")
@Module("document")
@Api(value = "API", tags = {""})
public class UsersController extends BaseController {

    @Autowired
    private UsersService usersService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addUsers(@Validated(Add.class) @RequestBody Users users) throws Exception {
        boolean flag = usersService.saveUsers(users);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateUsers(@Validated(Update.class) @RequestBody Users users) throws Exception {
        boolean flag = usersService.updateUsers(users);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteUsers(@PathVariable("id") Long id) throws Exception {
        boolean flag = usersService.deleteUsers(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = Users.class)
    public ApiResult<Users> getUsers(@PathVariable("id") Long id) throws Exception {
        Users users = usersService.getById(id);
        return ApiResult.ok(users);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = Users.class)
    public ApiResult<Paging<Users>> getUsersPageList(@Validated @RequestBody UsersPageParam usersPageParam) throws Exception {
        Paging<Users> paging = usersService.getUsersPageList(usersPageParam);
        return ApiResult.ok(paging);
    }
    
    /**
     * 登陆
     */
    @PostMapping("/login")
    @OperationLog(name = "登陆", type = OperationLogType.OTHER_QUERY)
    @ApiOperation(value = "登陆", response = ApiResult.class)
    public ApiResult<Users> login(@Validated(Add.class) @RequestBody Users user) throws Exception {
    	Users users = usersService.login(user);
    	if(users == null) {
    		return ApiResult.result(ApiCode.LOGIN_EXCEPTION, null);
    	}
        return ApiResult.ok(users);
    }
}

