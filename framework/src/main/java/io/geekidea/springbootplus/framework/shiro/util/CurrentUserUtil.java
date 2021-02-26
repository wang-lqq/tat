package io.geekidea.springbootplus.framework.shiro.util;

import io.geekidea.springbootplus.framework.shiro.vo.LoginSysUserVo;
/**
 * 
 * @author SSX_IT08112
 *
 */
public class CurrentUserUtil {
	private final static ThreadLocal<LoginSysUserVo> LOGIN_USER = new ThreadLocal<>();
	
	public static void setUser(LoginSysUserVo loginSysUserVo) {
        LOGIN_USER.set(loginSysUserVo);
    }
	
	public static LoginSysUserVo getUserIfLogin() {
        return LOGIN_USER.get();
    }
}
