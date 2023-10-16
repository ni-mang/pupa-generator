package com.nimang.pupa.base.model.proProject;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 项目-结果VO
 * @author JustHuman
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProProjectVO implements Serializable {
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

    private String configName;

    /**
     * 项目名
     */
    @NotBlank
    private String name;

    /**
     * 所有者
     */
    private Long belongTo;

    /**
     * 所有者ID
     */
    private Long ownerId;

    /**
     * 所有者名称
     */
    private String owner;


    /**
     * 项目说明
     */
    private String comments;

    /**
     * 扩展配置
     */
    private List<ProExtendValueBO> extendList;

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
     * 创建人
     */
    private Long createBy;

    /**
     * 创建人名称
     */
    private String creator;

    /**
     * 修改时间
     */
    @JsonFormat(timezone="GMT+8", shape = JsonFormat.Shape.STRING, pattern= DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;



}