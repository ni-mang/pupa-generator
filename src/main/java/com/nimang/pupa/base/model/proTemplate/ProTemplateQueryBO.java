package com.nimang.pupa.base.model.proTemplate;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.nimang.pupa.base.entity.ProTemplate;
import com.nimang.pupa.common.enums.proTemp.ProTempTypeEnum;
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
 * 模板-查询BO
 * @author JustHuman
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProTemplateQueryBO extends PageQuery<ProTemplate> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
 	 * 配置ID，列名：config_id
	 */
	@MPQuery
	@NotNull(message = "缺少“配置ID”")
	private Long configId;

	/**
 	 * 名称，列名：name
	 */
	@MPQuery(rule = Compare.LIKE)
	private String name;

	/**
	 * 模板引擎类型
	 * @see ProTempTypeEnum
	 */
	@MPQuery
	private Integer tempType;

	/**
	 * 状态，列名：status，0:禁用,1:启用
	 */
	@MPQuery
	private Integer status;

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