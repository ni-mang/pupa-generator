package com.nimang.pupa.dbExtends.postgreSql.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 
 * @author JustHuman
 * @date 2023-11-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostgreColumns implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 表名
	 */
	@TableField(value = "`table_name`")
	private String tableName;

	/**
	 * 列名
	 */
	@TableField(value = "`column_name`")
	private String columnName;

	/**
	 * 顺位
	 */
	@TableField(value = "`ordinal_position`")
	private Long ordinalPosition;

	/**
	 * 是否允许为空
	 * NO/YES
	 */
	@TableField(value = "`is_nullable`")
	private String isNullable;

	/**
	 * 数据类型
	 */
	@TableField(value = "`data_type`")
	private String dataType;

	/**
	 * 字符最大长度
	 */
	@TableField(value = "`character_maximum_length`")
	private Long characterMaximumLength;

	/**
	 * 数字精度
	 */
	@TableField(value = "`numeric_precision`")
	private Long numericPrecision;

	/**
	 * 数字刻度
	 */
	@TableField(value = "`numeric_scale`")
	private Long numericScale;

	/**
	 * 键
	 */
	@TableField(value = "`column_key`")
	private String columnKey;

	/**
	 * 注释
	 */
	@TableField(value = "`column_comment`")
	private String columnComment;

}