package com.nimang.pupa.base.model.proDatasource;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.nimang.pupa.base.entity.ProDatasource;
import com.nimang.pupa.common.tool.mp.annotation.MPQuery;
import com.nimang.pupa.common.tool.mp.enums.Compare;
import com.nimang.pupa.common.tool.webTool.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 数据源-查询BO
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProDatasourceQueryBO extends PageQuery<ProDatasource> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
 	 * 项目ID，列名：project_id
	 */
	@NotNull(message = "缺少“项目ID”")
	@MPQuery
	private Long projectId;

	/**
	 * 连接名
	 */
	@MPQuery(rule = Compare.LIKE)
	private String name;

	/**
 	 * 数据库品牌，列名：brand
	 */
	@MPQuery
	private Integer brand;

	/**
	 * 库
	 */
	@MPQuery(rule = Compare.LIKE)
	private String schema;

	/**
	 * 创建时间起始，列名：create_time
	 */
	@DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
	@MPQuery(column = "create_time", rule = Compare.GE)
	private LocalDateTime createTimeBegin;

	/**
	 * 创建时间截止
	 */
	@DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
	@MPQuery(column = "create_time", rule = Compare.LE)
	private LocalDateTime createTimeEnd;

}