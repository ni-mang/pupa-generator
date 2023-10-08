package com.nimang.pupa.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 项目
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pro_project")
public class ProProject implements Serializable {
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
	 * 项目名
	 */
	@TableField(value = "`name`")
	private String name;

	/**
	 * 项目说明
	 */
	@TableField(value = "`comments`")
	private String comments;

	/**
	 * 扩展配置
	 */
	@TableField(value = "`extend`")
	private String extend;

	/**
	 * 创建时间
	 */
	@TableField(value = "`create_time`", fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@TableField(value = "`create_by`")
	private Long createBy;

	/**
	 * 修改时间
	 */
	@TableField(value = "`update_time`", fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

}