package com.nimang.pupa.base.model.exportAndImport;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EAIConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 类型
     * 0:公共,1:私人
     */
    private Integer type;

    /**
     * 是否默认标志
     * 0:否,1:是
     */
    private Boolean defaultFlag;

    /**
     * 配置名
     */
    private String name;

    /**
     * 说明
     */
    private String comments;

    /**
     * 状态
     * 0:禁用,1:启用
     */
    private Integer status;

    private List<EAIMapper> mapperList = new ArrayList<>();

    private List<EAIExtend> extendList = new ArrayList<>();

    private List<EAITemplate> templateList = new ArrayList<>();

}
