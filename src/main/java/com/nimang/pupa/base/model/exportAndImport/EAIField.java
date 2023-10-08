package com.nimang.pupa.base.model.exportAndImport;

import lombok.Data;

import java.io.Serializable;

@Data
public class EAIField implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 顺位
     */
    private Long ordinalPosition;

    /**
     * 默认值
     */
    private String columnDefault;

    /**
     * 是否允许为空
     * NO/YES
     */
    private String isNullable;

    /**
     * 物理类型
     */
    private String dataType;

    /**
     * 字符最大长度
     */
    private Long characterMaximumLength;

    /**
     * 字符有效长度
     */
    private Long characterOctetLength;

    /**
     * 数字精度
     */
    private Long numericPrecision;

    /**
     * 数字刻度
     */
    private Long numericScale;

    /**
     * 时间精度
     */
    private Long datetimePrecision;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * 键
     */
    private String columnKey;

    /**
     * 主键类型
     * 常用区分主键值是否自增
     */
    private String idType;

    /**
     * 附加
     * 常用区分主键值来源
     */
    private String extra;

    /**
     * 属性类型
     */
    private String attrType;

    /**
     * 属性名
     */
    private String attrName;

    /**
     * 中文名
     */
    private String columnCn;

    /**
     * 注释
     */
    private String columnNotes;

    /**
     * 是否主键
     * 0:否,1:是
     */
    private Boolean primary;

    /**
     * 是否必填
     * 0:否,1:是
     */
    private Boolean requiredFlag;

    /**
     * 无符号
     * 0:否,1:是
     */
    private Boolean unsigned;

    /**
     * 下限
     */
    private String boundMin;

    /**
     * 上限
     */
    private String boundMax;

    /**
     * 扩展配置
     */
    private String extend;

    /**
     * 存在标识
     * 0:否,1:是
     */
    private Boolean existFlag;

    /**
     * 插入标识
     * 0:否,1:是
     */
    private Boolean insertFlag;

    /**
     * 展示标识
     * 0:否,1:是
     */
    private Boolean viewFlag;

    /**
     * 查询标识
     * 0:否,1:是
     */
    private Boolean queryFlag;

    /**
     * 枚举
     */
    private String enumName;
}
