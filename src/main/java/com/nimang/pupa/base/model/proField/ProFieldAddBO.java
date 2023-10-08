package com.nimang.pupa.base.model.proField;

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
 * 表字段-新增BO
 * @author LinLaichun
 * @date 2023-04-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProFieldAddBO implements Serializable{
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
     * 表单ID
     */
    @NotNull(message = "缺少“表单ID”")
    private Long tableId;

    /**
     * 列名
     */
    @NotBlank(message = "缺少“列名”")
    @Size(max = 64,message="“列名”不应超过64个字符")
    private String columnName;

    /**
     * 顺位
     */
    @NotNull(message = "缺少“顺位”")
    private Long ordinalPosition;

    /**
     * 默认值
     */
    private String columnDefault;

    /**
     * 物理类型
     */
    @NotBlank(message = "缺少“物理类型”")
    @Size(max = 64,message="“物理类型”不应超过64个字符")
    private String dataType;

    /**
     * 属性名
     */
    @NotBlank(message = "缺少“属性名”")
    @Size(max = 64,message="“属性名”不应超过64个字符")
    private String attrName;

    /**
     * 中文名
     */
    @Size(max = 20,message="“中文名”不应超过20个字符")
    private String columnCn;

    /**
     * 注释
     */
    @Size(max = 200,message="“注释”不应超过200个字符")
    private String columnNotes;

    /**
     * 是否主键
     * 0:否,1:是
     */
    @NotNull(message = "缺少“是否主键”")
    private Boolean primary;

    /**
     * 是否必填
     * 0:否,1:是
     */
    @NotNull(message = "缺少“是否必填”")
    private Boolean requiredFlag;

    /**
     * 主键类型
     * 常用区分主键值是否自增
     */
    private String idType;

    /**
     * 下限
     */
    @Size(max = 20,message="“下限”不应超过20个字符")
    private String boundMin;

    /**
     * 上限
     */
    @Size(max = 20,message="“上限”不应超过20个字符")
    private String boundMax;

    /**
     * 扩展配置
     */
    @Valid
    private List<ProExtendValueBO> extendList;

    /**
     * 插入标识
     * 0:否,1:是
     */
    private Boolean insertFlag;

    /**
     * 展示标识
     * 0:否,1:是
     */
    private Boolean viewFlag;

    /**
     * 查询标识
     * 0:否,1:是
     */
    private Boolean queryFlag;

    /**
     * 枚举
     */
    private String enumName;

}