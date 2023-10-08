package com.nimang.pupa.base.model.proDatasource;

import com.nimang.pupa.common.annotation.ValidEnum;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据源-品牌BO
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProDatasourceBrandBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 数据库品牌
     * @see DatasourceBrandEnum
     */
    @NotNull(message = "缺少“数据库品牌”")
    @ValidEnum(enumClass = DatasourceBrandEnum.class)
    private Integer brand;

}