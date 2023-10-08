package com.nimang.pupa.base.model.sysUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 修改密码BO
 * @date 2023-04-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPwdBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 原密码
     */
    @NotBlank(message = "缺少“原密码”")
    private String password;

    /**
     * 新密码
     */
    @NotBlank(message = "缺少“新密码”")
    private String newPassword;

    /**
     * 确认密码
     */
    @NotBlank(message = "缺少“确认密码”")
    private String confirmPassword;
}