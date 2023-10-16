package com.nimang.pupa.dbExtends.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 
 * @author JustHuman
 * @date 2023-04-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("TABLES")
public class MysqlTables implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 库
	 */
	@TableField(value = "`table_schema`")
	private String tableSchema;

	/**
	 * 表名
	 */
	@TableField(value = "`table_name`")
	private String tableName;

	/**
	 * 引擎
	 */
	@TableField(value = "`engine`")
	private String engine;

	/**
	 * 含有数据行数
	 */
	@TableField(value = "`table_rows`")
	private Long tableRows;

	/**
	 * 创建时间
	 */
	@TableField(value = "`create_time`")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@TableField(value = "`update_time`")
	private LocalDateTime updateTime;

	/**
	 * 注释
	 */
	@TableField(value = "`table_comment`")
	private String tableComment;
}