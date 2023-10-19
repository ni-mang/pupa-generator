package com.nimang.pupa.base.model.proDatasource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 数据源-列字段类型映射BO
 * @author JustHuman
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProDatasourceMapperBO implements Serializable{
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
    @NotBlank(message = "缺少“属性类型”")
    @Size(max = 50,message="“属性类型”不应超过50个字符")
    private String attrType;

}