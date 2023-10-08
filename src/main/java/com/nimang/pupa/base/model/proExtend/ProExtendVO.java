package com.nimang.pupa.base.model.proExtend;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * 扩展-结果VO
 * @author LinLaichun
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProExtendVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull
    private Long id;

    /**
     * 配置ID
     */
    @NotNull
    private Long configId;

    /**
     * 键
     */
    @NotBlank
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 键名
     */
    @NotBlank
    private String name;

    /**
     * 释义
     */
    private String comments;

    /**
     * 作用域
     * 0:项目,1:项目成员,2:数据源,3:表单,4:字段,
     * @see ProExtendScopeEnum
     */
    @NotNull
    private Integer scope;
    private String scopeDesc;
    /**
     * 状态
     * 0:禁用,1:启用
     * @see StatusEnum
     */
    @NotNull
    private Integer status;

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