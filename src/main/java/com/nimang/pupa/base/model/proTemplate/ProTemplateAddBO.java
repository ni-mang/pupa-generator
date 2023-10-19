package com.nimang.pupa.base.model.proTemplate;

import com.nimang.pupa.common.annotation.ValidEnum;
import com.nimang.pupa.common.enums.proTemp.ProTempTypeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * 模板-新增BO
 * @author JustHuman
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProTemplateAddBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    @NotNull(message = "缺少“配置ID”")
    private Long configId;

    /**
     * 内容
     */
    @NotBlank(message = "缺少“内容”")
    private String content;

    /**
     * 内容语言
     */
    @NotBlank(message = "缺少“语言”")
    private String contentLang;

    /**
     * 名称
     */
    @NotBlank(message = "缺少“名称”")
    @Size(max = 50,message="“名称”不应超过50个字符")
    private String name;

    /**
     * 模板引擎类型
     * @see ProTempTypeEnum
     */
    @NotNull(message = "缺少“模板引擎类型”")
    @ValidEnum(enumClass = ProTempTypeEnum.class)
    private Integer tempType;

    /**
     * 文件生成路径
     */
    @NotBlank(message = "缺少“文件生成路径”")
    @Size(max = 200,message="“文件生成路径”不应超过200个字符")
    private String path;

    /**
     * 备注
     */
    @Size(max = 200,message="“备注”不应超过200个字符")
    private String notes;
}