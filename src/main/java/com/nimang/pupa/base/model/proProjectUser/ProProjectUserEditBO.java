package com.nimang.pupa.base.model.proProjectUser;

import java.io.Serializable;
import java.util.List;

import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 项目成员-编辑BO
 * @author JustHuman
 * @date 2023-04-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProProjectUserEditBO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@NotNull(message = "缺少“ID”")
	private Long id;

	/**
	 * 作者署名
	 */
	private String author;

	/**
	 * 角色
	 * 0:所有者,1:普通成员
	 */
	private Integer role;

	/**
	 * 扩展配置
	 */
	@Valid
	private List<ProExtendValueBO> extendList;

	/**
	 * 锁
	 */
	private Long optLock;

}