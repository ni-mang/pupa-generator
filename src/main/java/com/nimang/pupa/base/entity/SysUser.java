package com.nimang.pupa.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 用户信息
 * @date 2023-04-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "`id`", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 用户类型
	 */
	@TableField(value = "`type`")
	private Integer type;

	/**
	 * 登录名
	 */
	@TableField(value = "`login_name`")
	private String loginName;

	/**
	 * 手机号
	 */
	@TableField(value = "`phone`")
	private String phone;

	/**
	 * 邮箱
	 */
	@TableField(value = "`email`")
	private String email;

	/**
	 * 头像地址
	 */
	@TableField(value = "`avatar`")
	private String avatar;

	/**
	 * 昵称
	 */
	@TableField(value = "`nick_name`")
	private String nickName;

	/**
	 * 密码
	 */
	@TableField(value = "`password`")
	private String password;

	/**
	 * 密码加盐
	 */
	@TableField(value = "`salt`")
	private String salt;

	/**
	 * 状态
	 * 0:禁用,1:启用
	 */
	@TableField(value = "`status`")
	private Integer status;

	/**
	 * 逻辑删除
	 * 0:正常,1:删除
	 */
	@TableField(value = "`deleted`")
	@TableLogic
	private Boolean deleted;

	/**
	 * 创建时间
	 */
	@TableField(value = "`create_time`", fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@TableField(value = "`update_time`", fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 锁
	 */
	@TableField(value = "`opt_lock`", fill = FieldFill.INSERT)
	@Version
	private Long optLock;

}