package com.nimang.pupa.base.model.sysUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 重设密码BO
 * @date 2023-04-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPwdBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotNull(message = "缺少“用户ID”")
    private Long userId;

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