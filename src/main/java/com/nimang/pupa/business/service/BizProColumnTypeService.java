package com.nimang.pupa.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProColumnType;
import com.nimang.pupa.base.model.proColumnType.ProColumnTypeAddBO;
import com.nimang.pupa.base.model.proColumnType.ProColumnTypeEditBO;
import com.nimang.pupa.base.model.proColumnType.ProColumnTypeQueryBO;
import com.nimang.pupa.base.model.proColumnType.ProColumnTypeVO;

import java.util.List;

/**
 * 列类型-业务接口
 * @author LinLaichun
 * @date 2023-09-08
 */
public interface BizProColumnTypeService {

    /**
     * 新增
     * @param addBO ProColumnTypeAddBO 新增数据
     * @return Long ID
     */
    Long add(ProColumnTypeAddBO addBO);

    /**
     * 修改
     * @param editBO ProColumnTypeEditBO 修改数据
     * @return Boolean
     */
    Boolean edit(ProColumnTypeEditBO editBO);

    /**
     * 根据主键删除
     * @param id Long 列类型-ID
     * @return Boolean
     */
    Boolean remove(Long id);

    /**
     * 根据主键批量删除
     * @param ids List<Long> 列类型-ID集合
     * @return Boolean
     */
    Boolean removeBatch(List<Long> ids);

    /**
     * 根据主键获取
     * @param id Long 列类型-ID
     * @return ProColumnType
     */
    ProColumnType get(Long id);

    /**
     * 条件查询（可分页）
     * @param queryBO ProColumnTypeQueryBO 查询参数
     * @return Page<ProColumnType>
     */
    Page<ProColumnType> query(ProColumnTypeQueryBO queryBO);

    /**
     * 数据装配
     * @param proColumnType 源对象
     * @return ProColumnTypeVO
     */
    ProColumnTypeVO assemble(ProColumnType proColumnType);

    /**
     * 数据装配-集合
     * @param list 源对象集合
     * @return List<ProColumnTypeVO>
     */
    List<ProColumnTypeVO> assembleList(List<ProColumnType> list);

    /**
     * 数据装配-分页
     * @param page 源分页对象
     * @return Page<ProColumnTypeVO>
     */
    Page<ProColumnTypeVO> assemblePage(Page<ProColumnType> page);
}
