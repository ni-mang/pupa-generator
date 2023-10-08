package com.nimang.pupa.base.model.proProjectUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 项目成员-识别BO
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProProjectUserIdentifyBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    @NotNull(message = "缺少“项目ID”")
    private Long projectId;

    /**
     * 用户ID
     */
    @NotNull(message = "缺少“用户ID”")
    private Long userId;

}