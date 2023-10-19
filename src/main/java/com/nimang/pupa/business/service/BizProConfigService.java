package com.nimang.pupa.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProConfig;
import com.nimang.pupa.base.entity.ProExtend;
import com.nimang.pupa.base.entity.ProMapper;
import com.nimang.pupa.base.entity.ProTemplate;
import com.nimang.pupa.base.model.exportAndImport.EAIConfig;
import com.nimang.pupa.base.model.gen.GenDataInfo;
import com.nimang.pupa.common.pojo.StatusChangeBO;
import com.nimang.pupa.base.model.proConfig.ProConfigAddBO;
import com.nimang.pupa.base.model.proConfig.ProConfigEditBO;
import com.nimang.pupa.base.model.proConfig.ProConfigQueryBO;
import com.nimang.pupa.base.model.proConfig.ProConfigVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 配置-业务接口
 * @author JustHuman
 * @date 2023-04-21
 */
public interface BizProConfigService {

    /**
     * 新增
     * @param addBO ProConfigAddBO 新增数据
     * @return Long ID
     */
    Long add(ProConfigAddBO addBO);

    /**
     * 修改
     * @param editBO ProConfigEditBO 修改数据
     * @return Boolean
     */
    Boolean edit(ProConfigEditBO editBO);

    /**
     * 状态变更
     * @param changeBO StatusChangeBO 修改数据
     * @return Boolean
     */
    Boolean change(StatusChangeBO changeBO);

    /**
     * 克隆
     * @param configId Long 配置ID
     * @return Long ID
     */
    Long clone(Long configId);

    /**
     * 根据主键删除
     * @param id Long 配置-ID
     * @return Boolean
     */
    Boolean remove(Long id);

    /**
     * 根据主键获取
     * @param id Long 配置-ID
     * @return ProConfig
     */
    ProConfig get(Long id);

    /**
     * 获取参数说明
     * @param id Long 配置-ID
     * @return String
     */
    List<GenDataInfo> showConfigInfo(Long id);

    /**
     * 条件查询（可分页）
     * @param queryBO ProConfigQueryBO 查询参数
     * @return Page<ProConfig>
     */
    Page<ProConfig> query(ProConfigQueryBO queryBO);

    /**
     * 下拉选择
     * @return List<ProConfig>
     */
    List<ProConfig> listForSelect();

    /**
     * 数据装配
     * @param proConfig 源对象
     * @return ProConfigVO
     */
    ProConfigVO assemble(ProConfig proConfig);

    /**
     * 数据装配-集合
     * @param list 源对象集合
     * @return List<ProConfigVO>
     */
    List<ProConfigVO> assembleList(List<ProConfig> list);

    /**
     * 数据装配-分页
     * @param page 源分页对象
     * @return Page<ProConfigVO>
     */
    Page<ProConfigVO> assemblePage(Page<ProConfig> page);

    /**
     * 按配置ID导出相应配置
     * @param configIds
     */
    void export(List<Long> configIds);

    /**
     * 按搜索条件导出配置
     * @param queryBO
     */
    void export(ProConfigQueryBO queryBO);

    /**
     * 生成导出数据
     * @param configList
     * @return
     */
    List<EAIConfig> doExport(List<ProConfig> configList);

    /**
     * 导入文件
     * @param file
     * @return
     */
    Integer importAll(MultipartFile file);

    /**
     * 组装数据
     * @param eaiConfigList
     * @param configList
     * @param extendList
     * @param templateList
     */
    void buildData(List<EAIConfig> eaiConfigList, List<ProConfig> configList, List<ProMapper> mapperList, List<ProExtend> extendList, List<ProTemplate> templateList);
}
