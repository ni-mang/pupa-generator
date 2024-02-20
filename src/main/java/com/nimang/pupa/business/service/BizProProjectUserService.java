package com.nimang.pupa.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProProjectUser;
import com.nimang.pupa.base.entity.SysUser;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserAddBO;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserEditBO;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserVO;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserQueryBO;
import com.nimang.pupa.common.pojo.TransferVO;

import java.util.List;

/**
 * 项目成员-业务接口
 * @author JustHuman
 * @date 2023-04-26
 */
public interface BizProProjectUserService {

    /**
     * 新增
     * @param addBO ProProjectUserAddBO 新增数据
     * @return Long 项目ID
     */
    Boolean add(ProProjectUserAddBO addBO);

    /**
     * 修改
     * @param editBO ProProjectUserEditBO 修改数据
     * @return Boolean
     */
    Boolean edit(ProProjectUserEditBO editBO);

    /**
     * 授权转让
     * @param editBO
     * @return
     */
    Boolean transfer(ProProjectUserEditBO editBO);

    /**
     * 根据主键删除
     * @param id Long 项目成员-ID
     * @return Boolean
     */
    Boolean remove(Long id);

    /**
     * 根据主键获取
     * @param id Long 项目成员-ID
     * @return ProProjectUser
     */
    ProProjectUser get(Long id);

    /**
     * 条件查询（可分页）
     * @param queryBO ProProjectUserQueryBO 查询参数
     * @return Page<ProProjectUser>
     */
    Page<ProProjectUser> query(ProProjectUserQueryBO queryBO);

    /**
     * 获取项目成员选择下拉数据
     * @param queryBO
     * @return
     */
    List<SysUser> userForSelect(ProProjectUserQueryBO queryBO);

    /**
     * 获取项目成员穿梭框数据
     * @param queryBO
     * @return
     */
    TransferVO userForTransfer(ProProjectUserQueryBO queryBO);

    /**
     * 数据装配
     * @param proProjectUser 源对象
     * @return ProProjectUserVO
     */
    ProProjectUserVO assemble(ProProjectUser proProjectUser);

    /**
     * 数据装配-集合
     * @param list 源对象集合
     * @return List<ProProjectUserVO>
     */
    List<ProProjectUserVO> assembleList(List<ProProjectUser> list);

    /**
     * 数据装配-分页
     * @param page 源分页对象
     * @return Page<ProProjectUserVO>
     */
    Page<ProProjectUserVO> assemblePage(Page<ProProjectUser> page);
}
