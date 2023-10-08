package com.nimang.pupa.common.tool.enumsTool;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EnumVO {

    /**
     * 值
     */
    @NotBlank
    private String value;

    /**
     * 注释
     */
    @NotBlank
    private String desc;

}
