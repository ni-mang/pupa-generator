package com.nimang.pupa.base.model.exportAndImport;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EAIProject implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 配置
     */
    EAIConfig eaiConfig;

    /**
     * 项目名
     */
    private String name;

    /**
     * 所有者ID
     */
    private Long ownerId;

    /**
     * 项目说明
     */
    private String comments;

    /**
     * 扩展配置
     */
    private String extend;

    private List<EAIDatasource> datasourceList = new ArrayList<>();
}
