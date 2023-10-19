package com.nimang.pupa.base.model.proTable;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.nimang.pupa.common.tool.mp.annotation.MPQuery;
import com.nimang.pupa.common.tool.mp.enums.Compare;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 表单-结果VO
 * @author JustHuman
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProTableVO implements Serializable {
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
     * 库
     */
    @NotBlank
    private String tableSchema;

    /**
     * 表名
     */
    @NotBlank
    private String tableName;

    /**
     * 无前缀名
     */
    @NotBlank
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
     * 模块
     */
    private String module;

    /**
     * 中文名
     */
    private String cnName;

    /**
     * 注释
     */
    private String tableComment;

    /**
     * 存在标识
     * 0:否,1:是
     */
    private Boolean existFlag;

    /**
     * 扩展配置
     */
    private  List<ProExtendValueBO> extendList;

    /**
     * 创建时间
     */
    @JsonFormat(timezone="GMT+8", shape = JsonFormat.Shape.STRING, pattern= DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(timezone="GMT+8", shape = JsonFormat.Shape.STRING, pattern= DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;



}