package com.nimang.pupa.business.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProField;
import com.nimang.pupa.base.model.proField.*;
import com.nimang.pupa.common.tool.webTool.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 表字段-业务接口
 * @author JustHuman
 * @date 2023-04-26
 */
public interface BizProFieldService {

    /**
     * 新增
     * @param addBO ProFieldAddBO 新增数据
     * @return Long ID
     */
    Long add(ProFieldAddBO addBO);

    /**
     * 修改
     * @param editBO ProFieldEditBO 修改数据
     * @return Boolean
     */
    Boolean edit(ProFieldEditBO editBO);

    /**
     * 变更标识
     * @param flagChangeBO ProFieldFlagChangeBO 修改数据
     * @return Boolean
     */
    Boolean change(ProFieldFlagChangeBO flagChangeBO);

    /**
     * 根据主键删除
     * @param id Long 表字段-ID
     * @return Boolean
     */
    Boolean remove(Long id);

    /**
     * 根据主键批量删除
     * @param ids List<Long> 表字段-ID集合
     * @return Boolean
     */
    Boolean removeBatch(List<Long> ids);

    /**
     * 根据主键获取
     * @param id Long 表字段-ID
     * @return ProField
     */
    ProField get(Long id);

    /**
     * 条件查询（可分页）
     * @param queryBO ProFieldQueryBO 查询参数
     * @return Page<ProField>
     */
    Page<ProField> query(ProFieldQueryBO queryBO);

    /**
     * 数据装配
     * @param proField 源对象
     * @return ProFieldVO
     */
    ProFieldVO assemble(ProField proField);

    /**
     * 数据装配-集合
     * @param list 源对象集合
     * @return List<ProFieldVO>
     */
    List<ProFieldVO> assembleList(List<ProField> list);

    /**
     * 数据装配-分页
     * @param page 源分页对象
     * @return Page<ProFieldVO>
     */
    Page<ProFieldVO> assemblePage(Page<ProField> page);
}
