package com.nimang.pupa.base.model.exportAndImport;

import lombok.Data;

import java.io.Serializable;

@Data
public class EAIMapper implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 数据库品牌
     */
    private Integer brand;

    /**
     * 语言
     */
    private String lang;

    /**
     * 映射规则
     * json
     */
    private String mapper;

    /**
     * 释义
     */
    private String comments;
}
