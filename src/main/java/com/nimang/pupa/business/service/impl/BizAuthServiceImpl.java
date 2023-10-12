package com.nimang.pupa.business.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nimang.pupa.base.entity.SysUser;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserAddBO;
import com.nimang.pupa.base.model.sysUser.*;
import com.nimang.pupa.base.service.ISysUserService;
import com.nimang.pupa.business.service.BizAuthService;
import com.nimang.pupa.business.service.BizProProjectUserService;
import com.nimang.pupa.common.constants.ExceptionConstants;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.SnowFlakeIdGen;
import com.nimang.pupa.common.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;


/**
 * 用户信息-业务接口实现
 * @date 2023-04-12
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class BizAuthServiceImpl implements BizAuthService {

	private final SnowFlakeIdGen snowFlakeIdGen;
	private final ISysUserService userService;
	private final BizProProjectUserService bizProProjectUserService;

	/**
	 * 注册
	 * @param baseBO UserBaseBO 注册数据
	 * @return Boolean 结果
	 */
	@Override
	public Boolean register(UserBaseBO baseBO) {
		// 检查用户名是否存在
		long count = userService.count(new LambdaQueryWrapper<SysUser>()
				.eq(SysUser::getLoginName, baseBO.getLoginName())
				.eq(SysUser::getStatus, StatusEnum.STATUS_1.getCode()));
		if(count > 0){
			throw new ApiException(ExceptionConstants.EXISTED_ACCOUNT);
		}
		// 密码加盐
		String salt =  RandomUtil.randomNumbers(4);
		String password = MD5.create().digestHex(MessageFormat.format("{0}{1}{2}", salt, baseBO.getPassword(), salt));
		// 保存用户信息
		SysUser user = SysUser.builder()
				.id(snowFlakeIdGen.nextId())
				.loginName(baseBO.getLoginName())
				.nickName(baseBO.getLoginName())
				.password(password)
				.salt(salt)
				.status(StatusEnum.STATUS_1.getCode()).build();
		Long testProId = 908350977811591168L;
		bizProProjectUserService.add(new ProProjectUserAddBO(testProId, user.getId()));
		return userService.save(user);
	}

	/**
	 * 变更信息
	 * @param editBO UserEditBO 修改数据
	 * @return Boolean
	 * @date 2023-04-14
	 */
	@Override
	public Boolean modify(UserEditBO editBO) {
		Long userId = UserUtil.getId();
		SysUser user = userService.getById(userId);
		ConvertUtil.convertOfEntity(editBO, user);
		userService.updateById(user);
		UserUtil.set(user);
		return true;
	}

	/**
	 * 登录
	 * @param baseBO UserBaseBO 登录数据
	 * @return UserVO 用户信息
	 */
	@Override
	public UserVO login(UserBaseBO baseBO) {
		// 根据登录名获取用户
		SysUser user = userService.getOne(new LambdaQueryWrapper<SysUser>()
				.eq(SysUser::getLoginName, baseBO.getLoginName())
				.or().eq(SysUser::getPhone,baseBO.getLoginName()));
		if(user == null ){
			throw new RuntimeException(ExceptionConstants.INVALID_ACC_PWD_ERROR);
		}
		if(StatusEnum.STATUS_0.equals(user.getStatus())){
			throw new RuntimeException(ExceptionConstants.INVALID_USER_STATUS_ERROR);
		}
		// 校验密码是否匹配
		String salt = user.getSalt();
		String password = MD5.create().digestHex(MessageFormat.format("{0}{1}{2}", salt, baseBO.getPassword(), salt));
		if(!password.equals(user.getPassword())){
			throw new RuntimeException(ExceptionConstants.INVALID_ACC_PWD_ERROR);
		}
		// Sa_Token登录
		StpUtil.login(user.getId());
		UserUtil.set(user);
		return assemble(user);
	}

	/**
	 * 获取当前登录用户
	 * @return UserVO 用户信息
	 */
	@Override
	public UserVO getCurrentUser() {
		// 获取当前用户
		SysUser user = UserUtil.get();
		return assemble(user);
	}

	/**
	 * 修改密码
	 * @param modifyPwdBO 密码数据
	 * @return Boolean 结果
	 */
	@Override
	public Boolean modifyPwd(ModifyPwdBO modifyPwdBO) {
		if(!modifyPwdBO.getNewPassword().equals(modifyPwdBO.getConfirmPassword())){
			throw new RuntimeException(ExceptionConstants.INVALID_PWD_CONFIRM);
		}
		// 获取当前用户
		SysUser user = UserUtil.get();
		// 校验密码是否匹配
		String salt = user.getSalt();
		String password = MD5.create().digestHex(MessageFormat.format("{0}{1}{2}", salt, modifyPwdBO.getPassword(), salt));
		if(!password.equals(user.getPassword())){
			throw new RuntimeException(ExceptionConstants.INVALID_PWD_ERROR);
		}
		// 新密码
		salt = RandomUtil.randomString(4);
		String newPassword = MD5.create().digestHex(MessageFormat.format("{0}{1}{2}", salt, modifyPwdBO.getNewPassword(), salt));
		//保存新密码
		user.setSalt(salt);
		user.setPassword(newPassword);
		userService.updateById(user);
		UserUtil.set(user);
		return true;
	}

	/**
	 * 重置密码-管理员操作
	 * @param resetPwdBO 重置数据
	 * @return Boolean 结果
	 */
	@Override
	public Boolean resetPwd(ResetPwdBO resetPwdBO) {
		// 校验密码是否一致
		if(!resetPwdBO.getNewPassword().equals(resetPwdBO.getConfirmPassword())){
			throw new RuntimeException(ExceptionConstants.INVALID_PWD_CONFIRM);
		}
		// 获取用户
		SysUser user = userService.getById(resetPwdBO.getUserId());
		if(user == null ){
			throw new RuntimeException(ExceptionConstants.INVALID_ACC_ERROR);
		}
		if(StatusEnum.STATUS_0.equals(user.getStatus())){
			throw new RuntimeException(ExceptionConstants.INVALID_USER_STATUS_ERROR);
		}
		// 修改密码
		user.setSalt(RandomUtil.randomString(4));
		String newPassword = MD5.create().digestHex(MessageFormat.format("{0}{1}{2}", user.getSalt(), resetPwdBO.getNewPassword(), user.getSalt()));
		user.setPassword(newPassword);
		userService.updateById(user);
		return true;
	}

	/**
	 * 数据装配
	 * @param user 源对象
	 * @return UserVO
	 */
	public UserVO assemble(SysUser user) {
		UserVO vo = ConvertUtil.convertOfEntity(user, UserVO.class);
		vo.setStatusDesc(StatusEnum.getMsgByCode(vo.getStatus()));
		return vo;
	}
}
