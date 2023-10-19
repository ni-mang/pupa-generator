package com.nimang.pupa.base.model.proExtend;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 扩展-编辑BO
 * @author JustHuman
 * @date 2023-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProExtendEditBO extends ProExtendAddBO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@NotNull(message = "缺少“ID”")
	private Long id;

}