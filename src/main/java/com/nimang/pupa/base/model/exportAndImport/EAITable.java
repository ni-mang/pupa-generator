package com.nimang.pupa.base.model.exportAndImport;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EAITable implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 库
     */
    private String tableSchema;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 无前缀名
     */
    private String infixName;

    /**
     * 引擎
     */
    private String engine;

    /**
     * 含有数据行数
     */
    private Long tableRows;

    /**
     * 注释
     */
    private String tableComment;

    /**
     * 扩展配置
     */
    private String extend;

    /**
     * 存在标识
     * 0:否,1:是
     */
    private Boolean existFlag;

    private List<EAIField> fieldList = new ArrayList<>();
}
