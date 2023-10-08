package com.nimang.pupa.base.model.proField;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 表字段-编辑BO
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProFieldEditBO extends ProFieldAddBO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@NotNull(message = "缺少“ID”")
	private Long id;

}