package com.nimang.pupa.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 模板
 * @author LinLaichun
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pro_template")
public class ProTemplate implements Serializable {
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
	 * 内容
	 */
	@TableField(value = "`content`")
	private String content;

	/**
	 * 内容语言
	 */
	@TableField(value = "`content_lang`")
	private String contentLang;

	/**
	 * 名称
	 */
	@TableField(value = "`name`")
	private String name;

	/**
	 * 模板引擎类型
	 */
	@TableField(value = "`temp_type`")
	private Integer tempType;

	/**
	 * 文件生成路径
	 */
	@TableField(value = "`path`")
	private String path;

	/**
	 * 备注
	 */
	@TableField(value = "`notes`")
	private String notes;

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