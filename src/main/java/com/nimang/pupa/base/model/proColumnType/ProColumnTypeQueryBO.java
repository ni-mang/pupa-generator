package com.nimang.pupa.base.model.proColumnType;

import com.nimang.pupa.base.entity.ProColumnType;
import com.nimang.pupa.common.tool.mp.annotation.MPQuery;
import com.nimang.pupa.common.tool.webTool.PageQuery;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 列类型-查询BO
 * @author LinLaichun
 * @date 2023-09-7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProColumnTypeQueryBO extends PageQuery<ProColumnType> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID，列名：id
	 * 
	 */
	@MPQuery
	private Long id;

	/**
	 * 数据库品牌，列名：brand
	 * @see DatasourceBrandEnum
	 */
	@MPQuery
	private Integer brand;

	/**
	 * 列类型
	 */
	@MPQuery
	private String columnType;
}