package com.nimang.pupa.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;


/**
 * ID组BO
 * @date 2023-04-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdsBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotEmpty(message = "缺少“ID”")
    private List<Long> ids;


}