package com.nimang.pupa.base.model.proField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 表字段-变更标识BO
 * @author LinLaichun
 * @date 2023-09-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProFieldFlagChangeBO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@NotNull(message = "缺少“ID”")
	private Long id;

	/**
	 * 字段名
	 */
	@NotNull(message = "缺少“字段名”")
	private String filed;

	/**
	 * 值
	 */
	@NotNull(message = "缺少“值”")
	private Boolean value;

}