package com.nimang.pupa.dbExtends.sqlServer.entity;

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
 * @date 2023-10-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("COLUMNS")
public class SqlServerColumns implements Serializable {
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
	 * 默认值
	 */
	@TableField(value = "`column_default`")
	private String columnDefault;

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
	 * 字符有效长度
	 */
	@TableField(value = "`character_octet_length`")
	private Long characterOctetLength;

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
	 * 时间精度
	 */
	@TableField(value = "`datetime_precision`")
	private Long datetimePrecision;

	/**
	 * 列类型
	 */
	@TableField(value = "`column_type`")
	private String columnType;

	/**
	 * 键
	 */
	@TableField(value = "`column_key`")
	private String columnKey;

	/**
	 * 附加
	 */
	@TableField(value = "`extra`")
	private String extra;

	/**
	 * 注释
	 */
	@TableField(value = "`column_comment`")
	private String columnComment;

}