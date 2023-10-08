package com.nimang.pupa.base.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 表单
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pro_table")
public class ProTable implements Serializable {
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
	 * 无前缀名
	 */
	@TableField(value = "`infix_name`")
	private String infixName;

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
	 * 模块
	 */
	@TableField(value = "`module`")
	private String module;

	/**
	 * 中文名
	 */
	@TableField(value = "`cn_name`")
	private String cnName;

	/**
	 * 注释
	 */
	@TableField(value = "`table_comment`")
	private String tableComment;

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
	 * 去除前缀
	 * @param prefixStr
	 */
	public void makeInfixName(String prefixStr){
		if(StrUtil.isNotBlank(prefixStr)){
			String[] prefixes = prefixStr.split(",");
			for(String prefix:prefixes){
				int index = this.getTableName().indexOf(prefix);
				if(index == 0){
					this.infixName = this.getTableName().substring(prefix.length());
				}
			}
		}
	}
}