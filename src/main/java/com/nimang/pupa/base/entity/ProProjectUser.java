package com.nimang.pupa.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 项目成员
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pro_project_user")
public class ProProjectUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "`id`", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 用户ID
	 */
	@TableField(value = "`user_id`")
	private Long userId;

	/**
	 * 项目ID
	 */
	@TableField(value = "`project_id`")
	private Long projectId;

	/**
	 * 角色
	 * 0:所有者,1:普通成员
	 */
	@TableField(value = "`role`")
	private Integer role;

	/**
	 * 作者署名
	 */
	@TableField(value = "`author`")
	private String author;

	/**
	 * 扩展配置
	 */
	@JSONField(serialize = false)
	@TableField(value = "`extend`")
	private String extend;

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

	/**
	 * 锁
	 */
	@TableField(value = "`opt_lock`", fill = FieldFill.INSERT)
	@Version
	private Long optLock;

}