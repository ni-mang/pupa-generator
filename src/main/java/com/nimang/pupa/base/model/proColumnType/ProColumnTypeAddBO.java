package com.nimang.pupa.base.model.proColumnType;

import com.nimang.pupa.common.annotation.ValidEnum;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 列类型-新增BO
 * @author JustHuman
 * @date 2023-09-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProColumnTypeAddBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 数据库品牌
     * @see DatasourceBrandEnum
     */
    @NotNull(message = "缺少“数据库品牌”")
    @ValidEnum(enumClass = DatasourceBrandEnum.class)
    private Integer brand;

    /**
     * 列类型
     */
    @NotBlank(message = "缺少“列类型”")
    @Size(max = 64,message="“列类型”不应超过64个字符")
    private String columnType;

}