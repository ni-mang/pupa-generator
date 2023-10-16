package com.nimang.pupa.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProTable;
import com.nimang.pupa.base.model.proTable.ProTableAddBO;
import com.nimang.pupa.base.model.proTable.ProTableEditBO;
import com.nimang.pupa.base.model.proTable.ProTableQueryBO;
import com.nimang.pupa.base.model.proTable.ProTableVO;

import java.util.List;

/**
 * 表单-业务接口
 * @author JustHuman
 * @date 2023-04-26
 */
public interface BizProTableService {

    /**
     * 新增
     * @param addBO ProTableAddBO 新增数据
     * @return Long ID
     */
    Long add(ProTableAddBO addBO);

    /**
     * 修改
     * @param editBO ProTableEditBO 修改数据
     * @return Boolean
     */
    Boolean edit(ProTableEditBO editBO);

    /**
     * 根据主键删除
     * @param id Long 表单-ID
     * @return Boolean
     */
    Boolean remove(Long id);

    /**
     * 根据主键批量删除
     * @param ids List<Long> 表单-ID集合
     * @return Boolean
     */
    Boolean removeBatch(List<Long> ids);

    /**
     * 根据主键获取
     * @param id Long 表单-ID
     * @return ProTable
     */
    ProTable get(Long id);

    /**
     * 条件查询（可分页）
     * @param queryBO ProTableQueryBO 查询参数
     * @return Page<ProTable>
     */
    Page<ProTable> query(ProTableQueryBO queryBO);

    /**
     * 数据装配
     * @param proTable 源对象
     * @return ProTableVO
     */
    ProTableVO assemble(ProTable proTable);

    /**
     * 数据装配-集合
     * @param list 源对象集合
     * @return List<ProTableVO>
     */
    List<ProTableVO> assembleList(List<ProTable> list);

    /**
     * 数据装配-分页
     * @param page 源分页对象
     * @return Page<ProTableVO>
     */
    Page<ProTableVO> assemblePage(Page<ProTable> page);
}
