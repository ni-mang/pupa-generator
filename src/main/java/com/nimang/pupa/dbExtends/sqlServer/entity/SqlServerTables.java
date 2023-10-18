package com.nimang.pupa.dbExtends.sqlServer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * 
 * @author JustHuman
 * @date 2023-10-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("TABLES")
public class SqlServerTables implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 表名
	 */
	@TableField(value = "`table_name`")
	private String tableName;

	/**
	 * 创建时间
	 */
	@TableField(value = "`create_time`")
	private Date createTime;

	/**
	 * 注释
	 */
	@TableField(value = "`table_comment`")
	private String tableComment;
}