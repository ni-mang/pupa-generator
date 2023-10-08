package com.nimang.pupa.base.model.proDatasource;

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
 * 数据源-结果VO
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProDatasourceVO implements Serializable {
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
     * 连接名
     */
    @NotNull
    private String name;

    /**
     * 数据库品牌
     */
    @NotNull
    private Integer brand;

    private String brandDesc;

    /**
     * 主机地址
     */
    @NotBlank
    private String mainAddr;

    /**
     * 端口
     */
    @NotBlank
    private String port;

    /**
     * 库
     */
    @NotBlank
    private String schema;

    /**
     * 链接后缀
     * 如：serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=true
     */
    private String urlSuffix;

    /**
     * 账号
     */
    @NotBlank
    private String account;

    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 忽略前缀
     */
    private String prefix;

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
     * 创建人
     */
    private Long createBy;

    /**
     * 修改时间
     */
    @JsonFormat(timezone="GMT+8", shape = JsonFormat.Shape.STRING, pattern= DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;



}