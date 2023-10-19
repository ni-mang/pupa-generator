package com.nimang.pupa.base.model.proMapper;

import com.nimang.pupa.common.enums.proTemp.ProTempLangEnum;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * 数据映射-结果VO
 * @author JustHuman
 * @date 2023-07-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProMapperVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull
    private Long id;

    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 名称
     */
    @NotBlank
    private String name;

    /**
     * 数据库品牌
     * @see DatasourceBrandEnum
     */
    @NotNull
    private Integer brand;

    private String brandDesc;

    /**
     * 语言
     * @see ProTempLangEnum
     */
    @NotNull
    private String lang;

    private String langDesc;

    /**
     * 映射规则
     */
    private List<ColumnMapper> mapperList;

    /**
     * 释义
     */
    private String comments;

}