package com.nimang.pupa.base.model.sysUser;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * 用户信息-新增BO
 * @date 2023-04-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBaseBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 登录名
     */
    @NotBlank(message = "缺少“登录名”")
    @Size(max = 20,message="“登录名”不应超过20个字符")
    private String loginName;

    /**
     * 昵称
     */
    @Size(max = 20,message="“昵称”不应超过20个字符")
    private String nickName;

    /**
     * 密码
     */
    @NotBlank(message = "缺少“密码”")
    private String password;
}