package com.nimang.pupa.base.model.proExtend;

import com.nimang.pupa.common.annotation.ValidEnum;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * 扩展-新增BO
 * @author JustHuman
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProExtendAddBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    @NotNull(message = "缺少“配置ID”")
    private Long configId;

    /**
     * 键
     */
    @NotBlank(message = "缺少“键”")
    @Size(max = 30,message="“键”不应超过30个字符")
    private String key;

    /**
     * 值
     */
    @Size(max = 50,message="“值”不应超过50个字符")
    private String value;

    /**
     * 键名
     */
    @NotBlank(message = "缺少“键名”")
    @Size(max = 30,message="“键名”不应超过30个字符")
    private String name;

    /**
     * 释义
     */
    @Size(max = 200,message="“释义”不应超过200个字符")
    private String comments;

    /**
     * 作用域
     * 0:项目,1:项目成员,2:数据源,3:表单,4:字段,
     * @see ProExtendScopeEnum
     */
    @NotNull(message = "缺少“作用域”")
    @ValidEnum(enumClass = ProExtendScopeEnum.class)
    private Integer scope;

    /**
     * 状态
     * 0:禁用,1:启用
     * @see StatusEnum
     */
    private Integer status;

}