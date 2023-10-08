package com.nimang.pupa.business.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.nimang.pupa.base.entity.SysUser;
import com.nimang.pupa.base.model.sysUser.*;
import com.nimang.pupa.business.service.BizAuthService;
import com.nimang.pupa.common.annotation.IgnoreAuth;
import com.nimang.pupa.common.tool.webTool.R;
import com.nimang.pupa.common.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户/用户认证
 * @module pupa
 * @date 2023-04-12
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/auth")
@RequiredArgsConstructor
public class AuthController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final BizAuthService authService;

	/**
	 * 注册
	 * @param userBaseBO UserAddBO 注册-业务数据包
	 * @return R<Boolean> 操作结果
	 * @date 2023-04-14
	 * @status released
	 */
	@IgnoreAuth
	@PostMapping("/register")
	public R<Boolean> register(@Validated @RequestBody UserBaseBO userBaseBO) {
		log.info(" 用户认证-注册-开始 参数：{}", JSON.toJSONString(userBaseBO));
		Boolean result = authService.register(userBaseBO);
		return R.ok(result);
	}

	/**
	 * 变更信息
	 * @param userEditBO UserEditBO 编辑-业务数据包
	 * @return R<Boolean> 操作结果
	 * @date 2023-04-14
	 * @status released
	 */
	@PostMapping("/modify")
	public R<Boolean> modify(@Validated @RequestBody UserEditBO userEditBO) {
		log.info(" 用户认证-变更信息-开始 参数：{}",JSON.toJSONString(userEditBO));
		Boolean result = authService.modify(userEditBO);
		return R.ok(result);
	}

	/**
	 * 登录
	 * @param userBaseBO UserBaseBO 登录参数
	 * @return R<UserVO> 用户信息
	 * @date 2023-04-14
	 * @status released
	 */
	@IgnoreAuth
	@PutMapping("/login")
	public R<UserVO> login(@Validated @RequestBody UserBaseBO userBaseBO) {
		log.info(" 用户认证-登录-开始 参数：{}", JSON.toJSONString(userBaseBO));
		UserVO vo = authService.login(userBaseBO);
		return R.ok(vo);
	}

	/**
	 * 登出
	 * @return Boolean 操作结果
	 * @date 2023-04-14
	 * @status released
	 */
	@PutMapping("/logout")
	public R<Boolean> logout() {
		log.info(" 用户认证-登出-开始");
		SysUser user = UserUtil.get();
		StpUtil.logout(user.getId());
		return R.ok(true);
	}

	/**
	 * 获取当前用户
	 * @return R<UserVO> 用户信息
	 * @date 2023-04-14
	 * @status released
	 */
	@GetMapping("/getCurrentUser")
	public R<UserVO> getCurrentUser() {
		log.info(" 用户认证-获取当前用户-开始");
		UserVO vo = authService.getCurrentUser();
		return R.ok(vo);
	}

	/**
	 * 修改密码
	 * @param modifyPwdBO ModifyPwdBO 密码参数
	 * @return R<Boolean> 操作结果
	 * @date 2023-04-14
	 * @status released
	 */
	@PutMapping("/modifyPwd")
	public R<Boolean> modifyPwd(@Validated @RequestBody ModifyPwdBO modifyPwdBO) {
		log.info(" 用户认证-修改密码-开始 参数：{}", JSON.toJSONString(modifyPwdBO));
		authService.modifyPwd(modifyPwdBO);
		return R.ok(true);
	}

	/**
	 * 重置密码
	 * @param resetPwdBO ModifyPwdBO 密码参数
	 * @return R<Boolean> 操作结果
	 * @date 2023-04-14
	 * @status released
	 */
	@PutMapping("/resetPwd")
	public R<Boolean> resetPwd(@Validated @RequestBody ResetPwdBO resetPwdBO) {
		log.info(" 用户认证-重置密码-开始 参数：{}", JSON.toJSONString(resetPwdBO));
		authService.resetPwd(resetPwdBO);
		return R.ok(true);
	}
}
