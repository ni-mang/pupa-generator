package com.nimang.pupa.base.model.proColumnType;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



/**
 * 列类型-结果VO
 * @author LinLaichun
 * @date 2023-09-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProColumnTypeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull
    private Long id;

    /**
     * 数据库品牌
     * @see DatasourceBrandEnum
     */
    @NotNull
    private Integer brand;

    private String brandDesc;

    /**
     * 列类型
     */
    @NotBlank
    private String columnType;



}