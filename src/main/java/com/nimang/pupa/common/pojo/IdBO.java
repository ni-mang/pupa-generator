package com.nimang.pupa.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * IDBO
 * @date 2023-04-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "缺少“ID”")
    private Long id;



}