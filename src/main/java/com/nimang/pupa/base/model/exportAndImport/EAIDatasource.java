package com.nimang.pupa.base.model.exportAndImport;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EAIDatasource implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 连接名
     */
    private String name;

    /**
     * 数据库品牌
     */
    private Integer brand;

    /**
     * 主机地址
     */
    private String mainAddr;

    /**
     * 端口
     */
    private String port;

    /**
     * 库
     */
    private String schema;

    /**
     * 链接后缀
     * 如：serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=true
     */
    private String urlSuffix;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 忽略前缀
     */
    private String prefix;

    /**
     * 列字段类型映射
     */
    private String mapper;

    /**
     * 扩展配置
     */
    private String extend;

    private List<EAITable> tableList = new ArrayList<>();
}
