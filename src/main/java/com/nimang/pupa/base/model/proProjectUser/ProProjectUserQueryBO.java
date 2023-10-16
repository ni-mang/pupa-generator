package com.nimang.pupa.base.model.proProjectUser;

import com.nimang.pupa.base.entity.ProProjectUser;
import com.nimang.pupa.common.tool.mp.annotation.MPQuery;
import com.nimang.pupa.common.tool.mp.enums.Compare;
import com.nimang.pupa.common.tool.webTool.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 项目成员-查询BO
 * @author JustHuman
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProProjectUserQueryBO extends PageQuery<ProProjectUser> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
 	 * 项目ID，列名：project_id
	 */
	@MPQuery
	@NotNull(message = "缺少“项目ID”")
	private Long projectId;

	/**
	 * 昵称
	 */
	@MPQuery(rule = Compare.LIKE)
	private String nickName;

	/**
	 * 是否项目成员
	 */
	private Boolean isMember;
}