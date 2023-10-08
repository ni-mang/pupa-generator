package com.nimang.pupa.base.model.proTable;

import com.nimang.pupa.base.entity.ProTable;
import com.nimang.pupa.common.tool.mp.annotation.MPQuery;
import com.nimang.pupa.common.tool.mp.enums.Compare;
import com.nimang.pupa.common.tool.webTool.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * 表单-查询BO
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProTableQueryBO extends PageQuery<ProTable> implements Serializable {
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
 	 * 库，列名：table_schema
	 */
	@MPQuery(rule = Compare.LIKE)
	private String tableSchema;

	/**
	 * 模块
	 */
	@MPQuery(rule = Compare.LIKE)
	private String module;

	/**
	 * 中文名
	 */
	@MPQuery(rule = Compare.LIKE)
	private String cnName;

	/**
 	 * 表名，列名：table_name
	 */
	@MPQuery(rule = Compare.LIKE)
	private String tableName;

	/**
	 * 存在标识
	 * 0:否,1:是
	 */
	@MPQuery
	private Boolean existFlag;
}