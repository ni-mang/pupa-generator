package com.nimang.pupa.base.model.proConfig;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.nimang.pupa.base.entity.ProConfig;
import com.nimang.pupa.common.annotation.ValidEnum;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.tool.mp.annotation.MPQuery;
import com.nimang.pupa.common.tool.mp.enums.Compare;
import com.nimang.pupa.common.tool.webTool.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 配置-查询BO
 * @author LinLaichun
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProConfigQueryBO extends PageQuery<ProConfig> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
 	 * 用户ID，列名：user_id
	 */
	@MPQuery
	private Long userId;

	/**
 	 * 配置名，列名：name
	 */
	@MPQuery(rule = Compare.LIKE)
	private String name;

	/**
	 * 状态，列名：status，0:禁用,1:启用
	 * @see StatusEnum
	 */
	@MPQuery
	@ValidEnum(enumClass = StatusEnum.class)
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