package com.nimang.pupa.base.model.proConfig;

import com.nimang.pupa.common.enums.proConfig.ProConfigTypeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * 配置-新增BO
 * @author LinLaichun
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProConfigAddBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 配置名
     */
    @NotBlank(message = "缺少“配置名”")
    @Size(max = 30,message="“配置名”不应超过30个字符")
    private String name;

    /**
     * 类型
     * 0:公共,1:私人
     * @see ProConfigTypeEnum
     */
    @NotNull(message = "缺少“类型”")
    private Integer type;

    /**
     * 说明
     */
    @Size(max = 200,message="“说明”不应超过200个字符")
    private String comments;

}