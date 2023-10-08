package com.nimang.pupa.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 列类型
 * @author LinLaichun
 * @date 2023-09-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pro_column_type")
public class ProColumnType implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "`id`", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 数据库品牌
	 */
	@TableField(value = "`brand`")
	private Integer brand;

	/**
	 * 列类型
	 */
	@TableField(value = "`column_type`")
	private String columnType;
}