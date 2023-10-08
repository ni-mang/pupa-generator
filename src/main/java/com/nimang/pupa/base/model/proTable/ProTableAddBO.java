package com.nimang.pupa.base.model.proTable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * 表单-新增BO
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProTableAddBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    @NotNull(message = "缺少“项目ID”")
    private Long projectId;

    /**
     * 数据源ID
     */
    @NotNull(message = "缺少“数据源ID”")
    private Long sourceId;

    /**
     * 库
     */
    @Size(max = 64,message="“库”不应超过64个字符")
    private String tableSchema;

    /**
     * 表名
     */
    @NotBlank(message = "缺少“表名”")
    @Size(max = 64,message="“表名”不应超过64个字符")
    private String tableName;

    /**
     * 无前缀名
     */
    @Size(max = 64,message="“无前缀名”不应超过64个字符")
    private String infixName;

    /**
     * 引擎
     */
    @Size(max = 64,message="“引擎”不应超过64个字符")
    private String engine;

    /**
     * 含有数据行数
     */
    private Long tableRows;

    /**
     * 模块
     */
    @Size(max = 50,message="“模块”不应超过50个字符")
    private String module;

    /**
     * 中文名
     */
    @Size(max = 50,message="“中文名”不应超过50个字符")
    private String cnName;

    /**
     * 扩展配置
     */
    @Valid
    private List<ProExtendValueBO> extendList;
}