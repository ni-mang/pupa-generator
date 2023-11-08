package com.nimang.pupa.dbExtends.postgreSql.entity;

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
 * @date 2023-11-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostgreTables implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 表名
	 */
	@TableField(value = "`table_name`")
	private String tableName;

	/**
	 * 注释
	 */
	@TableField(value = "`table_comment`")
	private String tableComment;
}