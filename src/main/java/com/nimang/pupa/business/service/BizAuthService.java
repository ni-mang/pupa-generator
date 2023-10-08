package com.nimang.pupa.business.service;

import com.nimang.pupa.base.model.sysUser.*;

/**
 * 用户认证-业务接口
 * @date 2023-04-12
 */
public interface BizAuthService {

    /**
     * 注册
     * @param baseBO UserBaseBO 注册数据
     * @return Boolean 结果
     */
    Boolean register(UserBaseBO baseBO);

    /**
     * 变更信息
     * @param editBO UserEditBO 修改数据
     * @return Boolean
     * @date 2023-04-14
     */
    Boolean modify(UserEditBO editBO);

    /**
     * 登录
     * @param baseBO UserBaseBO 登录数据
     * @return UserVO 用户信息
     */
    UserVO login(UserBaseBO baseBO);

    /**
     * 获取当前登录用户
     * @return UserVO 用户信息
     */
    UserVO getCurrentUser();

    /**
     * 修改密码
     * @param modifyPwdBO 密码数据
     * @return Boolean 结果
     */
    Boolean modifyPwd(ModifyPwdBO modifyPwdBO);

    /**
     * 重置密码-管理员操作
     * @param resetPwdBO 重置数据
     * @return Boolean 结果
     */
    Boolean resetPwd(ResetPwdBO resetPwdBO);
}
