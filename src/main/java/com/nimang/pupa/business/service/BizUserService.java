package com.nimang.pupa.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.SysUser;
import com.nimang.pupa.base.model.sysUser.*;

import java.util.List;

/**
 * 用户信息-业务接口
 * @date 2023-04-12
 */
public interface BizUserService {

    /**
     * 新增
     * @param baseBO UserBaseBO 新增数据
     * @return Long ID
     */
    Long add(UserBaseBO baseBO);

    /**
     * 修改
     * @param editBO UserEditBO 修改数据
     * @return Boolean
     */
    Boolean edit(UserOpEditBO editBO);

    /**
     * 状态变更
     * @param bo ChangeStatusBO 状态数据
     * @return Boolean
     */
    Boolean changeStatus(ChangeStatusBO bo);

    /**
     * 根据主键删除
     * @param id Long 用户信息-ID
     * @return Boolean
     */
    Boolean remove(Long id);

    /**
     * 根据主键批量删除
     * @param ids List<Long> 用户信息-ID集合
     * @return Boolean
     */
    Boolean removeBatch(List<Long> ids);

    /**
     * 根据主键获取
     * @param id Long 用户信息-ID
     * @return SysUser
     */
    SysUser get(Long id);

    /**
     * 条件查询（可分页）
     * @param queryBO UserQueryBO 查询参数
     * @return Page<SysUser>
     */
    Page<SysUser> query(UserQueryBO queryBO);

    /**
     * 数据装配
     * @param user 源对象
     * @return UserVO
     */
    UserVO assemble(SysUser user);

    /**
     * 数据装配-集合
     * @param list 源对象集合
     * @return List<UserVO>
     */
    List<UserVO> assembleList(List<SysUser> list);

    /**
     * 数据装配-分页
     * @param page 源分页对象
     * @return Page<UserVO>
     */
    Page<UserVO> assemblePage(Page<SysUser> page);
}
