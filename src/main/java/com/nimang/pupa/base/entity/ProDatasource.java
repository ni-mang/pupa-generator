package com.nimang.pupa.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 数据源
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pro_datasource")
public class ProDatasource implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "`id`", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 项目ID
	 */
	@TableField(value = "`project_id`")
	private Long projectId;

	/**
	 * 连接名
	 */
	@TableField(value = "`name`")
	private String name;

	/**
	 * 数据库品牌
	 */
	@TableField(value = "`brand`")
	private Integer brand;

	/**
	 * 地址
	 * 如：127.0.0.1:3306
	 */
	@TableField(value = "`main_addr`")
	private String mainAddr;

	/**
	 * 库
	 */
	@TableField(value = "`schema`")
	private String schema;

	/**
	 * 链接后缀
	 * 如：serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=true
	 */
	@TableField(value = "`url_suffix`")
	private String urlSuffix;

	/**
	 * 账号
	 */
	@TableField(value = "`account`")
	private String account;

	/**
	 * 密码
	 */
	@TableField(value = "`password`")
	private String password;

	/**
	 * 忽略前缀
	 */
	@TableField(value = "`prefix`")
	private String prefix;

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