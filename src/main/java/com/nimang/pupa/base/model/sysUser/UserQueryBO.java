package com.nimang.pupa.base.model.sysUser;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.nimang.pupa.base.entity.SysUser;
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
 * 用户信息-查询BO
 * @date 2023-04-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryBO extends PageQuery<SysUser> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 非此ID
	 */
	@MPQuery(rule = Compare.NE)
	private Long noId;

	/**
	 * 登录名，列名：login_name
	 */
	@MPQuery(rule = Compare.LIKE)
	private String loginName;

	/**
	 * 昵称，列名：nick_name
	 */
	@MPQuery(rule = Compare.LIKE)
	private String nickName;

	/**
	 * 状态，列名：status
	 * 0:禁用,1:启用
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

	/**
	 * 是否管理员
	 */
	private Boolean hasAdmin = true;
}