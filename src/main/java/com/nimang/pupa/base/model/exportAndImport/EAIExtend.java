package com.nimang.pupa.base.model.exportAndImport;

import lombok.Data;

import java.io.Serializable;

@Data
public class EAIExtend implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 键名
     */
    private String name;

    /**
     * 释义
     */
    private String comments;

    /**
     * 作用域
     * 0:项目,1:项目成员,2:数据源,3:表单,4:字段,
     */
    private Integer scope;

    /**
     * 状态
     * 0:禁用,1:启用
     */
    private Integer status;
}
