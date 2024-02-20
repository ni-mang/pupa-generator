package com.nimang.pupa.base.model.proProjectUser;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 项目成员-新增BO
 * @author JustHuman
 * @date 2023-04-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProProjectUserAddBO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 项目ID
	 */
	@NotNull(message = "缺少“项目ID”")
	private Long projectId;

	/**
	 * 用户ID
	 */
	@NotEmpty(message = "缺少“用户ID”")
	private List<Long> userIds;
}