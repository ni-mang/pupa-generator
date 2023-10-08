package com.nimang.pupa.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 配置
 * @author LinLaichun
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pro_config")
public class ProConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "`id`", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 类型
	 * 0:公共,1:私人
	 */
	@TableField(value = "`type`")
	private Integer type;

	/**
	 * 是否默认标志
	 * 0:否,1:是
	 */
	@TableField(value = "`default_flag`")
	private Boolean defaultFlag;

	/**
	 * 用户ID
	 */
	@TableField(value = "`user_id`")
	private Long userId;

	/**
	 * 配置名
	 */
	@TableField(value = "`name`")
	private String name;

	/**
	 * 说明
	 */
	@TableField(value = "`comments`")
	private String comments;

	/**
	 * 生成次数
	 */
	@TableField(value = "`gen_times`")
	private Integer genTimes;

	/**
	 * 状态
	 * 0:禁用,1:启用
	 */
	@TableField(value = "`status`")
	private Integer status;

	/**
	 * 创建时间
	 */
	@TableField(value = "`create_time`", fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@TableField(value = "`update_time`", fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

}