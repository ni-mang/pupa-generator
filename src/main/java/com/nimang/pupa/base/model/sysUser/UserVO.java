package com.nimang.pupa.base.model.sysUser;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * 用户信息-结果VO
 * @date 2023-04-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull
    private Long id;

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 用户类型描述
     */
    private String typeDesc;

    /**
     * 登录名
     */
    @NotBlank
    private String loginName;

    /**
     * 手机号
     */
    @NotBlank
    private String phone;

    /**
     * 邮箱
     */
    @NotBlank
    private String email;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 状态
     * 0:禁用,1:启用
     */
    @NotNull
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 创建时间
     */
    @JsonFormat(timezone="GMT+8", shape = JsonFormat.Shape.STRING, pattern= DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 乐观锁
     */
    private Long optLock;



}