package com.nimang.pupa.business.service;

import com.nimang.pupa.base.model.gen.GenTemplate;

import java.util.List;

public interface BizGenService {
    /**
     * 全库生成
     * @param sourceId Long 数据源ID
     * @return Integer 同步表数量
     */
    void genAll(Long sourceId);

    /**
     * 生成指定表
     * @param tableIds List<Long> 表ID集合
     * @return Integer 同步表数量
     */
    void genTables(List<Long> tableIds);

    /**
     * 预览指定表生成的内容
     * @param tableId Long 表ID
     * @return List<GenTemplate> 渲染结果
     */
    List<GenTemplate> previewTable(Long tableId);


}
