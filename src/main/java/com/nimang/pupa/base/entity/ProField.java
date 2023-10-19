package com.nimang.pupa.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 表字段
 * @author JustHuman
 * @date 2023-04-27
 */
@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pro_field")
public class ProField implements Serializable {
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
	 * 数据源ID
	 */
	@TableField(value = "`source_id`")
	private Long sourceId;

	/**
	 * 表单ID
	 */
	@TableField(value = "`table_id`")
	private Long tableId;

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
	 * 物理类型
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
	 * 主键类型
	 * 常用区分主键值是否自增
	 */
	@TableField(value = "`id_type`")
	private String idType;

	/**
	 * 附加
	 * 常用区分主键值来源
	 */
	@TableField(value = "`extra`")
	private String extra;

	/**
	 * 属性名
	 */
	@TableField(value = "`attr_name`")
	private String attrName;

	/**
	 * 中文名
	 */
	@TableField(value = "`column_cn`")
	private String columnCn;

	/**
	 * 注释
	 */
	@TableField(value = "`column_notes`")
	private String columnNotes;

	/**
	 * 是否主键
	 * 0:否,1:是
	 */
	@TableField(value = "`primary`")
	private Boolean primary;

	/**
	 * 是否必填
	 * 0:否,1:是
	 */
	@TableField(value = "`required_flag`")
	private Boolean requiredFlag;

	/**
	 * 无符号
	 * 0:否,1:是
	 */
	@TableField(value = "`unsigned`")
	private Boolean unsigned;

	/**
	 * 下限
	 */
	@TableField(value = "`bound_min`")
	private String boundMin;

	/**
	 * 上限
	 */
	@TableField(value = "`bound_max`")
	private String boundMax;

	/**
	 * 扩展配置
	 */
	@TableField(value = "`extend`")
	private String extend;

	/**
	 * 存在标识
	 * 0:否,1:是
	 */
	@TableField(value = "`exist_flag`")
	private Boolean existFlag;

	/**
	 * 插入标识
	 * 0:否,1:是
	 */
	@TableField(value = "`insert_flag`")
	private Boolean insertFlag;

	/**
	 * 展示标识
	 * 0:否,1:是
	 */
	@TableField(value = "`view_flag`")
	private Boolean viewFlag;

	/**
	 * 查询标识
	 * 0:否,1:是
	 */
	@TableField(value = "`query_flag`")
	private Boolean queryFlag;

	/**
	 * 枚举
	 */
	@TableField(value = "`enum_name`")
	private String enumName;

}