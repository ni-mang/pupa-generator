package com.nimang.pupa.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 扩展
 * @author LinLaichun
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pro_extend")
public class ProExtend implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "`id`", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 配置ID
	 */
	@TableField(value = "`config_id`")
	private Long configId;

	/**
	 * 键
	 */
	@TableField(value = "`key`")
	private String key;

	/**
	 * 值
	 */
	@TableField(value = "`value`")
	private String value;

	/**
	 * 键名
	 */
	@TableField(value = "`name`")
	private String name;

	/**
	 * 释义
	 */
	@TableField(value = "`comments`")
	private String comments;

	/**
	 * 作用域
	 * 0:项目,1:项目成员,2:数据源,3:表单,4:字段,
	 */
	@TableField(value = "`scope`")
	private Integer scope;

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