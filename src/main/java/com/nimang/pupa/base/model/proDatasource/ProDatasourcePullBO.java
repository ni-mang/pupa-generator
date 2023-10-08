package com.nimang.pupa.base.model.proDatasource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 数据源-同步
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProDatasourcePullBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 数据源ID
     */
    @NotNull(message = "缺少“数据源ID”")
    private Long sourceId;

    /**
     * 表ID
     */
    private List<Long> tableIds;

}