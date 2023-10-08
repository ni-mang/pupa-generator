package com.nimang.pupa.base.model.exportAndImport;

import lombok.Data;

import java.io.Serializable;

@Data
public class EAITemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 内容
     */
    private String content;

    /**
     * 内容语言
     */
    private String contentLang;

    /**
     * 名称
     */
    private String name;

    /**
     * 模板引擎类型
     */
    private Integer tempType;

    /**
     * 文件生成路径
     */
    private String path;

    /**
     * 备注
     */
    private String notes;

    /**
     * 状态
     * 0:禁用,1:启用
     */
    private Integer status;

}
