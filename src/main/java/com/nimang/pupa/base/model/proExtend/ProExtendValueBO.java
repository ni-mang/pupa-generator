package com.nimang.pupa.base.model.proExtend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 扩展-键值BO
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProExtendValueBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 键
     */
    @NotBlank(message = "缺少“键”")
    @Size(max = 30,message="“键”不应超过30个字符")
    private String key;

    /**
     * 值
     */
    @Size(max = 50,message="“值”不应超过50个字符")
    private String value;
}