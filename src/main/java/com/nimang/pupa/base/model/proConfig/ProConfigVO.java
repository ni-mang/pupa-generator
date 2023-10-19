package com.nimang.pupa.base.model.proConfig;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.enums.proConfig.ProConfigTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * 配置-结果VO
 * @author JustHuman
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProConfigVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull
    private Long id;

    /**
     * 类型
     * 0:公共,1:私人
     * @see ProConfigTypeEnum
     */
    @NotNull
    private Integer type;

    /**
     * 是否默认标志
     * 0:否,1:是
     */
    private Boolean defaultFlag;

    /**
     * 用户ID（创建人）
     */
    @NotNull
    private Long userId;

    /**
     * 用户名称（创建人）
     */
    private String userName;

    /**
     * 配置名
     */
    @NotBlank
    private String name;

    /**
     * 说明
     */
    private String comments;

    /**
     * 引用数
     */
    private Integer useNum;

    /**
     * 生成次数
     */
    private Integer genTimes;

    /**
     * 状态
     * 0:禁用,1:启用
     * @see StatusEnum
     */
    @NotNull
    private Integer status;

    /**
     * 是否只读
     */
    private Boolean readOnly;

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