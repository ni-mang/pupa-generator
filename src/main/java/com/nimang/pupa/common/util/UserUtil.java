package com.nimang.pupa.common.util;

import cn.dev33.satoken.stp.StpUtil;
import com.nimang.pupa.base.entity.SysUser;

public class UserUtil {
    public static String USER_KEY = "CURRENT_USER";

    /**
     * 保存/更新当前用户信息
     * @param user
     */
    public static void set(SysUser user){
        StpUtil.getSession().set(USER_KEY, user);
    }

    /**
     * 获取当前用户
     * @return SysUser
     */
    public static SysUser get(){
        return (SysUser) StpUtil.getSession().get(USER_KEY);
    }

    /**
     * 获取当前用户ID
     * @return String
     */
    public static Long getId(){
        SysUser user = get();
        return user.getId();
    }


}
