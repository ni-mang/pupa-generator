package com.nimang.pupa.base.model.sysUser;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * 用户信息-编辑BO
 * @date 2023-04-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditBO implements Serializable {
	private static final long serialVersionUID = 1L;

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
}