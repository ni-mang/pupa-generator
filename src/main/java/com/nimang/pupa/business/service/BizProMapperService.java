package com.nimang.pupa.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProMapper;
import com.nimang.pupa.base.model.proMapper.ProMapperAddBO;
import com.nimang.pupa.base.model.proMapper.ProMapperEditBO;
import com.nimang.pupa.base.model.proMapper.ProMapperQueryBO;
import com.nimang.pupa.base.model.proMapper.ProMapperVO;

import java.util.List;

/**
 * 数据映射-业务接口
 * @author JustHuman
 * @date 2023-08-09
 */
public interface BizProMapperService {

    /**
     * 新增
     * @param addBO ProMapperAddBO 新增数据
     * @return Long ID
     */
    Long add(ProMapperAddBO addBO);

    /**
     * 修改
     * @param editBO ProMapperEditBO 修改数据
     * @return Boolean
     */
    Boolean edit(ProMapperEditBO editBO);

    /**
     * 根据主键删除
     * @param id Long 数据映射配置-ID
     * @return Boolean
     */
    Boolean remove(Long id);

    /**
     * 根据主键批量删除
     * @param ids List<Long> 数据映射配置-ID集合
     * @return Boolean
     */
    Boolean removeBatch(List<Long> ids);

    /**
     * 根据主键获取
     * @param id Long 数据映射配置-ID
     * @return ProMapper
     */
    ProMapper get(Long id);

    /**
     * 条件查询（可分页）
     * @param queryBO ProMapperQueryBO 查询参数
     * @return Page<ProMapper>
     */
    Page<ProMapper> query(ProMapperQueryBO queryBO);

    /**
     * 数据装配
     * @param proMapper 源对象
     * @return ProMapperVO
     */
    ProMapperVO assemble(ProMapper proMapper);

    /**
     * 数据装配-集合
     * @param list 源对象集合
     * @return List<ProMapperVO>
     */
    List<ProMapperVO> assembleList(List<ProMapper> list);

    /**
     * 数据装配-分页
     * @param page 源分页对象
     * @return Page<ProMapperVO>
     */
    Page<ProMapperVO> assemblePage(Page<ProMapper> page);
}
