package com.nimang.pupa.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProTemplate;
import com.nimang.pupa.base.model.proTemplate.ProTemplateAddBO;
import com.nimang.pupa.base.model.proTemplate.ProTemplateEditBO;
import com.nimang.pupa.base.model.proTemplate.ProTemplateQueryBO;
import com.nimang.pupa.base.model.proTemplate.ProTemplateVO;
import com.nimang.pupa.common.pojo.StatusChangeBO;

import java.util.List;

/**
 * 模板-业务接口
 * @author LinLaichun
 * @date 2023-04-21
 */
public interface BizProTemplateService {

    /**
     * 新增
     * @param addBO ProTemplateAddBO 新增数据
     * @return Long ID
     */
    Long add(ProTemplateAddBO addBO);

    /**
     * 修改
     * @param editBO ProTemplateEditBO 修改数据
     * @return Boolean
     */
    Boolean edit(ProTemplateEditBO editBO);

    /**
     * 状态变更
     * @param changeBO StatusChangeBO 修改数据
     * @return Boolean
     */
    Boolean change(StatusChangeBO changeBO);

    /**
     * 根据主键删除
     * @param id Long 模板-ID
     * @return Boolean
     */
    Boolean remove(Long id);

    /**
     * 根据主键批量删除
     * @param ids List<Long> 模板-ID集合
     * @return Boolean
     */
    Boolean removeBatch(List<Long> ids);

    /**
     * 根据主键获取
     * @param id Long 模板-ID
     * @return ProTemplate
     */
    ProTemplate get(Long id);

    /**
     * 条件查询（可分页）
     * @param queryBO ProTemplateQueryBO 查询参数
     * @return Page<ProTemplate>
     */
    Page<ProTemplate> query(ProTemplateQueryBO queryBO);

    /**
     * 数据装配
     * @param proTemplate 源对象
     * @return ProTemplateVO
     */
    ProTemplateVO assemble(ProTemplate proTemplate);

    /**
     * 数据装配-集合
     * @param list 源对象集合
     * @return List<ProTemplateVO>
     */
    List<ProTemplateVO> assembleList(List<ProTemplate> list);

    /**
     * 数据装配-分页
     * @param page 源分页对象
     * @return Page<ProTemplateVO>
     */
    Page<ProTemplateVO> assemblePage(Page<ProTemplate> page);
}
