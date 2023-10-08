package com.nimang.pupa.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProExtend;
import com.nimang.pupa.base.model.proExtend.*;
import com.nimang.pupa.common.pojo.StatusChangeBO;

import java.util.List;

/**
 * 扩展-业务接口
 * @author LinLaichun
 * @date 2023-04-21
 */
public interface BizProExtendService {

    /**
     * 新增
     * @param addBO ProExtendAddBO 新增数据
     * @return Long ID
     */
    Long add(ProExtendAddBO addBO);

    /**
     * 修改
     * @param editBO ProExtendEditBO 修改数据
     * @return Boolean
     */
    Boolean edit(ProExtendEditBO editBO);

    /**
     * 状态变更
     * @param changeBO StatusChangeBO 修改数据
     * @return Boolean
     */
    Boolean change(StatusChangeBO changeBO);

    /**
     * 根据主键删除
     * @param id Long 扩展-ID
     * @return Boolean
     */
    Boolean remove(Long id);

    /**
     * 根据主键批量删除
     * @param ids List<Long> 扩展-ID集合
     * @return Boolean
     */
    Boolean removeBatch(List<Long> ids);

    /**
     * 根据主键获取
     * @param id Long 扩展-ID
     * @return ProExtend
     */
    ProExtend get(Long id);

    /**
     * 条件查询（可分页）
     * @param queryBO ProExtendQueryBO 查询参数
     * @return Page<ProExtend>
     */
    Page<ProExtend> query(ProExtendQueryBO queryBO);

    /**
     * 展示所有扩展信息
     * @param configId 配置ID
     * @return List<ProExtendShowVO>
     */
    List<ProExtendShowVO> showAllExtend(Long configId);
    /**
     * 数据装配
     * @param proExtend 源对象
     * @return ProExtendVO
     */
    ProExtendVO assemble(ProExtend proExtend);

    /**
     * 数据装配-集合
     * @param list 源对象集合
     * @return List<ProExtendVO>
     */
    List<ProExtendVO> assembleList(List<ProExtend> list);

    /**
     * 数据装配-分页
     * @param page 源分页对象
     * @return Page<ProExtendVO>
     */
    Page<ProExtendVO> assemblePage(Page<ProExtend> page);
}
