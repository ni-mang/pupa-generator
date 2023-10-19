package com.nimang.pupa.base.model.proColumnType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 列类型-编辑BO
 * @author JustHuman
 * @date 2023-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProColumnTypeEditBO extends ProColumnTypeAddBO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@NotNull(message = "缺少“ID”")
	private Long id;

}