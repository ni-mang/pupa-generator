package com.nimang.pupa.base.model.proTemplate;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.nimang.pupa.common.enums.proTemp.ProTempTypeEnum;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



/**
 * 模板-结果VO
 * @author LinLaichun
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProTemplateVO implements Serializable {
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
     * 内容
     */
    @NotBlank
    private String content;

    /**
     * 内容语言
     */
    @NotBlank
    private String contentLang;

    /**
     * 名称
     */
    @NotBlank
    private String name;

    /**
     * 模板引擎类型
     * @see ProTempTypeEnum
     */
    @NotNull
    private Integer tempType;

    /**
     * 模板引擎类型描述
     */
    private String tempTypeDesc;

    /**
     * 文件生成路径
     */
    @NotBlank
    private String path;

    /**
     * 备注
     */
    private String notes;

    /**
     * 状态
     * 0:禁用,1:启用
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