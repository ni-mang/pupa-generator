package com.nimang.pupa.base.model.proProjectUser;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 项目成员-结果VO
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProProjectUserVO implements Serializable {
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
     * 用户ID
     */
    @NotNull
    private Long userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 作者署名
     */
    private String author;

    /**
     * 角色
     * 0:所有者,1:普通成员
     */
    @NotNull
    private Integer role;

    /**
     * 角色描述
     */
    @NotNull
    private String roleDesc;

    /**
     * 扩展配置
     */
    private List<ProExtendValueBO> extendList;

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

    /**
     * 锁
     */
    private Long optLock;



}