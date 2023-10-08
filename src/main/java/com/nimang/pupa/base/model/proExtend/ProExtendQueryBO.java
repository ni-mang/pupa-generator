package com.nimang.pupa.base.model.proExtend;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.annotation.JSONField;
import com.nimang.pupa.base.entity.ProExtend;
import com.nimang.pupa.common.annotation.ValidEnum;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;
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
 * 扩展-查询BO
 * @author LinLaichun
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProExtendQueryBO extends PageQuery<ProExtend> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
 	 * 配置ID，列名：config_id
	 */
	@MPQuery
	@NotNull(message = "缺少“配置ID”")
	private Long configId;

	/**
 	 * 键，列名：key
	 */
	@MPQuery(rule = Compare.LIKE)
	private String key;

	/**
 	 * 键名，列名：name
	 */
	@MPQuery(rule = Compare.LIKE)
	private String name;

	/**
	 * 作用域，列名：scope，0:项目,1:项目成员,2:数据源,3:表单,4:字段,
	 * @see ProExtendScopeEnum
	 */
	@MPQuery
	@ValidEnum(enumClass = ProExtendScopeEnum.class, allowNull = true)
	private Integer scope;

	/**
	 * 状态，列名：status，0:禁用,1:启用
	 * @see StatusEnum
	 */
	@MPQuery
	@ValidEnum(enumClass = StatusEnum.class, allowNull = true)
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