package com.nimang.pupa.base.model.proProject;

import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * 项目-新增BO
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProProjectAddBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    @NotNull(message = "缺少“配置ID”")
    private Long configId;

    /**
     * 项目名
     */
    @NotBlank(message = "缺少“项目名”")
    @Size(max = 30,message="“项目名”不应超过30个字符")
    private String name;

    /**
     * 项目说明
     */
    @Size(max = 200,message="“项目说明”不应超过200个字符")
    private String comments;

    /**
     * 扩展配置
     */
    @Valid
    private List<ProExtendValueBO> extendList;
}