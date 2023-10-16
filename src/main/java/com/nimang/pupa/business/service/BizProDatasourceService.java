package com.nimang.pupa.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProDatasource;
import com.nimang.pupa.base.model.proDatasource.*;

import java.util.List;

/**
 * 数据源-业务接口
 * @author JustHuman
 * @date 2023-04-26
 */
public interface BizProDatasourceService {

    /**
     * 新增
     * @param addBO ProDatasourceAddBO 新增数据
     * @return Long ID
     */
    Long add(ProDatasourceAddBO addBO);

    /**
     * 修改
     * @param editBO ProDatasourceEditBO 修改数据
     * @return Boolean
     */
    Boolean edit(ProDatasourceEditBO editBO);

    /**
     * 根据主键删除
     * @param id Long 数据源-ID
     * @return Boolean
     */
    Boolean remove(Long id);

    /**
     * 根据主键批量删除
     * @param ids List<Long> 数据源-ID集合
     * @return Boolean
     */
    Boolean removeBatch(List<Long> ids);

    /**
     * 根据主键获取
     * @param id Long 数据源-ID
     * @return ProDatasource
     */
    ProDatasource get(Long id);

    /**
     * 条件查询（可分页）
     * @param queryBO ProDatasourceQueryBO 查询参数
     * @return Page<ProDatasource>
     */
    Page<ProDatasource> query(ProDatasourceQueryBO queryBO);

    /**
     * 数据装配
     * @param proDatasource 源对象
     * @return ProDatasourceVO
     */
    ProDatasourceVO assemble(ProDatasource proDatasource);

    /**
     * 数据装配-集合
     * @param list 源对象集合
     * @return List<ProDatasourceVO>
     */
    List<ProDatasourceVO> assembleList(List<ProDatasource> list);

    /**
     * 数据装配-分页
     * @param page 源分页对象
     * @return Page<ProDatasourceVO>
     */
    Page<ProDatasourceVO> assemblePage(Page<ProDatasource> page);

    /**
     * 执行同步
     * @param pullBO ProDatasourcePullBO 同步操作参数
     * @return Integer 同步表数量
     */
    Integer doPull(ProDatasourcePullBO pullBO);
}
