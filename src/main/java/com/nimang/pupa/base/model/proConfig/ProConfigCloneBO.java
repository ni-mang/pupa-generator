package com.nimang.pupa.base.model.proConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 配置-克隆BO
 * @author LinLaichun
 * @date 2023-04-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProConfigCloneBO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 目标ID
	 */
	@NotNull(message = "缺少“目标ID”")
	private Long id;

	/**
	 * 新配置名
	 */
	@NotBlank(message = "缺少“新配置名”")
	@Size(max = 30,message="“新配置名”不应超过30个字符")
	private String name;

}