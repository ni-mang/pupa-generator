package com.nimang.pupa.base.model.proMapper;

import com.nimang.pupa.base.entity.ProMapper;
import com.nimang.pupa.common.enums.proTemp.ProTempLangEnum;
import com.nimang.pupa.common.tool.mp.annotation.MPQuery;
import com.nimang.pupa.common.tool.mp.enums.Compare;
import com.nimang.pupa.common.tool.webTool.PageQuery;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据映射-查询BO
 * @author LinLaichun
 * @date 2023-07-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProMapperQueryBO extends PageQuery<ProMapper> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID，列名：id
	 * 
	 */
	@MPQuery
	private Long id;

	/**
	 * 配置ID
	 */
	@MPQuery
	private Long configId;

	/**
	 * 名称，列名：name
	 */
	@MPQuery(rule = Compare.LIKE)
	private String name;

	/**
	 * 数据库品牌，列名：brand
	 * @see DatasourceBrandEnum
	 */
	@MPQuery
	private Integer brand;

	/**
	 * 语言，列名：lang
	 * @see ProTempLangEnum
	 */
	@MPQuery
	private String lang;
}