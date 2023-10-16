package com.nimang.pupa.base.model.proMapper;

import com.nimang.pupa.common.annotation.ValidEnum;
import com.nimang.pupa.common.enums.proTemp.ProTempLangEnum;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * 数据映射-新增BO
 * @author JustHuman
 * @date 2023-07-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProMapperAddBO implements Serializable{
	private static final long serialVersionUID = 1L;

    private Boolean a;

    /**
     * 配置ID
     */
    @NotNull(message = "缺少“配置ID”")
    private Long configId;

    /**
     * 名称
     */
    @NotBlank(message = "缺少“名称”")
    @Size(max = 30,message="“名称”不应超过30个字符")
    private String name;

    /**
     * 数据库品牌
     * @see DatasourceBrandEnum
     */
    @NotNull(message = "缺少“数据库品牌”")
    @ValidEnum(enumClass = DatasourceBrandEnum.class)
    private Integer brand;

    /**
     * 语言
     * @see ProTempLangEnum
     */
    @NotNull(message = "缺少“数据库品牌”")
    @ValidEnum(enumClass = ProTempLangEnum.class)
    private String lang;

    /**
     * 映射规则
     */
    @Valid
    private List<ColumnMapper> mapperList;

    /**
     * 释义
     */
    @Size(max = 200,message="“释义”不应超过200个字符")
    private String comments;


}