package com.nimang.pupa.base.model.sysUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户信息-编辑BO
 * @date 2023-04-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOpEditBO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@NotNull(message = "缺少“ID”")
	private Long id;

	/**
	 * 头像地址
	 */
	@Size(max = 100,message="“头像地址”不应超过100个字符")
	private String avatar;

	/**
	 * 昵称
	 */
	@Size(max = 20,message="“昵称”不应超过20个字符")
	private String nickName;

	/**
	 * 锁
	 */
	@NotNull(message = "缺少“锁”")
	private Long optLock;
}