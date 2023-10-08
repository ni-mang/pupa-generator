package com.nimang.pupa.common.pojo;

import com.nimang.pupa.common.enums.StatusEnum;
import lombok.*;

import java.io.Serializable;

/**
 * 状态变更BO
 * @author LinLaichun
 * @date 2023-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class StatusChangeBO extends IdBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 状态
     * 0:禁用,1:启用
     * @see StatusEnum
     */
    private Integer status;

}