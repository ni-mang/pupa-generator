package com.nimang.pupa.base.model.proConfig;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 配置-编辑BO
 * @author LinLaichun
 * @date 2023-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProConfigEditBO extends ProConfigAddBO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@NotNull(message = "缺少“ID”")
	private Long id;

	/**
	 * 用户ID
	 */
	@NotNull(message = "缺少“用户”")
	private Long userId;

}