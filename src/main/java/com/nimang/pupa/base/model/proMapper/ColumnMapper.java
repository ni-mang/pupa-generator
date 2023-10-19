package com.nimang.pupa.base.model.proMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 列字段类型映射BO
 * @author JustHuman
 * @date 2023-09-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnMapper implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 列类型
     * 如:varchar
     */
    @NotBlank(message = "缺少“列类型”")
    @Size(max = 30,message="“列类型”不应超过30个字符")
    private String columnType;

    /**
     * 属性类型
     * 如:String
     */
    @Size(max = 50,message="“属性类型”不应超过50个字符")
    private String attrType = "";

    /**
     * 引用路径
     */
    @Size(max = 100,message="“引用路径”不应超过100个字符")
    private String importPath = "";

}