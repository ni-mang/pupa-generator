package com.nimang.pupa.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProProject;
import com.nimang.pupa.base.model.proProject.ProProjectAddBO;
import com.nimang.pupa.base.model.proProject.ProProjectEditBO;
import com.nimang.pupa.base.model.proProject.ProProjectQueryBO;
import com.nimang.pupa.base.model.proProject.ProProjectVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 项目-业务接口
 * @author JustHuman
 * @date 2023-04-26
 */
public interface BizProProjectService {

    /**
     * 新增
     * @param addBO ProProjectAddBO 新增数据
     * @return Long ID
     */
    Long add(ProProjectAddBO addBO);

    /**
     * 修改
     * @param editBO ProProjectEditBO 修改数据
     * @return Boolean
     */
    Boolean edit(ProProjectEditBO editBO);

    /**
     * 克隆
     * @param id Long 项目-ID
     * @return Long ID
     */
    Long clone(Long id);

    /**
     * 根据主键删除
     * @param id Long 项目-ID
     * @return Boolean
     */
    Boolean remove(Long id);

    /**
     * 根据主键批量删除
     * @param ids List<Long> 项目-ID集合
     * @return Boolean
     */
    Boolean removeBatch(List<Long> ids);

    /**
     * 根据主键获取
     * @param id Long 项目-ID
     * @return ProProject
     */
    ProProject get(Long id);

    /**
     * 条件查询（可分页）
     * @param queryBO ProProjectQueryBO 查询参数
     * @return Page<ProProject>
     */
    Page<ProProject> query(ProProjectQueryBO queryBO);

    /**
     * 数据装配
     * @param proProject 源对象
     * @return ProProjectVO
     */
    ProProjectVO assemble(ProProject proProject);

    /**
     * 数据装配-集合
     * @param list 源对象集合
     * @return List<ProProjectVO>
     */
    List<ProProjectVO> assembleList(List<ProProject> list);

    /**
     * 数据装配-分页
     * @param page 源分页对象
     * @return Page<ProProjectVO>
     */
    Page<ProProjectVO> assemblePage(Page<ProProject> page);


    /**
     * 按配置ID导出项目
     * @param projectIds
     */
    void export(List<Long> projectIds);

    /**
     * 按搜索条件导出项目
     * @param queryBO
     */
    void export(ProProjectQueryBO queryBO);

    /**
     * 导入配置
     * @param file
     * @return
     */
    Integer importAll(MultipartFile file);
}
