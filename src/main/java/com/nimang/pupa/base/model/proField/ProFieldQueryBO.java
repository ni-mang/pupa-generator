package com.nimang.pupa.base.model.proField;

import com.nimang.pupa.base.entity.ProField;
import com.nimang.pupa.common.tool.mp.annotation.MPQuery;
import com.nimang.pupa.common.tool.mp.enums.Compare;
import com.nimang.pupa.common.tool.webTool.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 表字段-查询BO
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProFieldQueryBO extends PageQuery<ProField> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
 	 * 项目ID，列名：project_id
	 */
	@MPQuery
	private Long projectId;

	/**
	 * 数据源ID
	 */
	@MPQuery
	private Long sourceId;

	/**
 	 * 表单ID，列名：table_id
	 */
	@MPQuery
	private Long tableId;

	/**
 	 * 列名，列名：column_name
	 */
	@MPQuery(rule = Compare.LIKE)
	private String columnName;

	/**
 	 * 中文名，列名：column_cn
	 */
	@MPQuery(rule = Compare.LIKE)
	private String columnCn;

	/**
	 * 存在标识
	 * 0:否,1:是
	 */
	@MPQuery
	private Boolean existFlag;
}