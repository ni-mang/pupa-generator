package com.nimang.pupa.base.model.proField;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 表字段-结果VO
 * @author JustHuman
 * @date 2023-04-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProFieldVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull
    private Long id;

    /**
     * 项目ID
     */
    @NotNull
    private Long projectId;

    /**
     * 数据源ID
     */
    @NotNull
    private Long sourceId;

    /**
     * 表单ID
     */
    @NotNull
    private Long tableId;

    /**
     * 列名
     */
    @NotBlank
    private String columnName;

    /**
     * 顺位
     */
    @NotNull
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
     * 附加
     * 常用区分主键值来源
     */
    private String extra;

    /**
     * 属性名
     */
    @NotBlank
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
    @NotNull
    private Boolean primary;

    /**
     * 主键类型
     * 常用区分主键值是否自增
     */
    private String idType;

    /**
     * 是否必填
     * 0:否,1:是
     */
    @NotNull
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
    private List<ProExtendValueBO> extendList;

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