package com.nimang.pupa.business.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.nimang.pupa.base.entity.SysUser;
import com.nimang.pupa.base.model.sysUser.*;
import com.nimang.pupa.base.service.ISysUserService;
import com.nimang.pupa.business.service.BizUserService;
import com.nimang.pupa.common.constants.Constants;
import com.nimang.pupa.common.constants.ExceptionConstants;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.enums.user.UserTypeEnum;
import com.nimang.pupa.common.tool.mp.query.MPQueryWrapper;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.SnowFlakeIdGen;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;



/**
 * 用户信息-业务接口实现
 * @date 2023-04-12
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class BizUserServiceImpl implements BizUserService {

	private final SnowFlakeIdGen snowFlakeIdGen;
	private final ISysUserService userService;

	/**
	 * 新增
	 * @param baseBO UserBaseBO 新增数据
	 * @return Long ID
	 * @date 2023-04-12
	 */
	@Override
	public Long add(UserBaseBO baseBO) {
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
				.type(UserTypeEnum.ORDINARY_1.getCode())
				.loginName(baseBO.getLoginName())
				.nickName(baseBO.getLoginName())
				.password(password)
				.salt(salt)
				.status(StatusEnum.STATUS_1.getCode()).build();
		userService.save(user);
		return user.getId();
	}

	/**
	 * 修改
	 * @param editBO UserEditBO 修改数据
	 * @return Boolean
	 * @date 2023-04-12
	 */
	@Override
	public Boolean edit(UserOpEditBO editBO) {
		SysUser user = userService.getById(editBO.getId());
		ConvertUtil.convertOfEntity(editBO, user);
		return userService.updateById(user);
	}

	/**
	 * 状态变更
	 * @param bo ChangeStatusBO 状态数据
	 * @return Boolean
	 */
	@Override
	public Boolean changeStatus(ChangeStatusBO bo) {
		SysUser user = userService.getById(bo.getId());
		user.setStatus(bo.getStatus());
		userService.updateById(user);
		if(StatusEnum.STATUS_0.equals(bo.getStatus())){
			StpUtil.kickout(bo.getId());
		}
		return true;
	}

	/**
	 * 根据主键删除
	 * @param id Long 用户信息-ID
	 * @return Boolean
	 * @date 2023-04-12
	 */
	@Override
	public Boolean remove(Long id) {
		return userService.removeById(id);
	}

	/**
	 * 根据主键批量删除
	 * @param ids List<Long> 用户信息-ID集合
	 * @return Boolean
	 * @date 2023-04-12
	 */
	@Override
	public Boolean removeBatch(List<Long> ids) {
		return userService.removeByIds(ids);
	}

	/**
	 * 根据主键获取
	 * @param id Long 用户信息-ID
	 * @return SysUser
	 * @date 2023-04-12
	 */
	@Override
	public SysUser get(Long id) {
		return userService.getById(id);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO UserQueryBO 查询参数
	 * @return Page<SysUser>
	 * @date 2023-04-12
	 */
	@Override
	public Page<SysUser> query(UserQueryBO queryBO) {
		MPQueryWrapper<SysUser> wrapper = new MPQueryWrapper<>();
		wrapper.build(queryBO);
		if(!queryBO.getHasAdmin()){
			wrapper.ne(SysUser::getId, Constants.ADMIN_ID);
		}
		return userService.page(queryBO.getPage(),wrapper);
	}

	/**
	 * 数据装配
	 * @param user 源对象
	 * @return UserVO
	 */
	@Override
	public UserVO assemble(SysUser user) {
		UserVO vo = ConvertUtil.convertOfEntity(user, UserVO.class);
		vo.setTypeDesc(UserTypeEnum.getMsgByCode(vo.getType()));
		vo.setStatusDesc(StatusEnum.getMsgByCode(vo.getStatus()));
		return vo;
	}

	/**
	 * 数据装配-集合
	 * @param list 源对象集合
	 * @return List<UserVO>
	 */
	@Override
	public List<UserVO> assembleList(List<SysUser> list) {
		if(ObjectUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		List<UserVO> voList = new ArrayList<>();
		for (SysUser user:list){
			voList.add(assemble(user));
		}
		return voList;
	}

	/**
	 * 数据装配-分页
	 * @param page 源分页对象
	 * @return Page<UserVO>
	 */
	@Override
	public Page<UserVO> assemblePage(Page<SysUser> page) {
		Page<UserVO> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
		List<UserVO> t = assembleList(page.getRecords());
		tPage.setRecords(t);
		return tPage;
	}

}
