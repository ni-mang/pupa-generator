package com.nimang.pupa.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据映射
 * @author JustHuman
 * @date 2023-08-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pro_mapper")
public class ProMapper implements Serializable {
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
	 * 名称
	 */
	@TableField(value = "`name`")
	private String name;

	/**
	 * 数据库品牌
	 */
	@TableField(value = "`brand`")
	private Integer brand;

	/**
	 * 语言
	 */
	@TableField(value = "`lang`")
	private String lang;

	/**
	 * 映射规则
	 * json
	 */
	@TableField(value = "`mapper`")
	private String mapper;

	/**
	 * 释义
	 */
	@TableField(value = "`comments`")
	private String comments;
}