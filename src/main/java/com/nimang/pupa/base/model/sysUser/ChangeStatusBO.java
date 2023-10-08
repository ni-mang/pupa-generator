package com.nimang.pupa.base.model.sysUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户信息-状态变更BO
 * @date 2023-04-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusBO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@NotNull(message = "缺少“ID”")
	private Long id;

	/**
	 * 状态
	 */
	@NotNull(message = "缺少“状态”")
	private Integer status;
}