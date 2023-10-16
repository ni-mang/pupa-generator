package com.nimang.pupa.business.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.nimang.pupa.base.entity.ProProject;
import com.nimang.pupa.base.entity.ProProjectUser;
import com.nimang.pupa.base.entity.SysUser;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserAddBO;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserEditBO;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserVO;
import com.nimang.pupa.base.service.IProExtendService;
import com.nimang.pupa.base.service.IProProjectService;
import com.nimang.pupa.base.service.IProProjectUserService;
import com.nimang.pupa.base.service.ISysUserService;
import com.nimang.pupa.business.service.BizProProjectUserService;
import com.nimang.pupa.common.constants.Constants;
import com.nimang.pupa.common.enums.pro.ProUserRoleEnum;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;
import com.nimang.pupa.common.tool.mp.query.MPQueryWrapper;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.SnowFlakeIdGen;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserQueryBO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 项目成员-业务接口实现
 * @author JustHuman
 * @date 2023-04-26
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class BizProProjectUserServiceImpl implements BizProProjectUserService {

	private final SnowFlakeIdGen snowFlakeIdGen;
	private final TransactionTemplate transactionTemplate;
	private final IProProjectUserService proProjectUserService;
	private final IProProjectService proProjectService;
	private final IProExtendService proExtendService;
	private final ISysUserService userService;

	/**
	 * 新增
	 * @param addBO ProProjectUserAddBO 新增数据
	 * @return Long 项目ID
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Long add(ProProjectUserAddBO addBO) {
		ProProjectUser proProjectUser = ConvertUtil.convertOfEntity(addBO, ProProjectUser.class);
		Long id = snowFlakeIdGen.nextId();
		proProjectUser.setId(id);
		proProjectUser.setRole(ProUserRoleEnum.PUR_1.getCode());
		ProProject proProject = proProjectService.getById(addBO.getProjectId());
		proProjectUser.setExtend(proExtendService.getJsonForScope(proProject.getConfigId(), ProExtendScopeEnum.PES_1));
		SysUser user = userService.getById(addBO.getUserId());
		proProjectUser.setAuthor(user.getNickName());
		proProjectUserService.save(proProjectUser);
		return id;
	}

	/**
	 * 修改
	 * @param editBO ProProjectUserEditBO 修改数据
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean edit(ProProjectUserEditBO editBO) {
		ProProjectUser proProjectUser = proProjectUserService.getById(editBO.getId());
		ConvertUtil.convertOfEntity(editBO, proProjectUser);
		if(ObjectUtil.isNotEmpty(editBO.getExtendList())){
			proProjectUser.setExtend(JSON.toJSONString(editBO.getExtendList()));
		}
		return proProjectUserService.updateById(proProjectUser);
	}

	/**
	 * 授权转让
	 * @param editBO
	 * @return
	 */
	@Override
	public Boolean transfer(ProProjectUserEditBO editBO) {
		ProProjectUser proProjectUser = proProjectUserService.getById(editBO.getId());
		if(ProUserRoleEnum.PUR_1.equals(proProjectUser.getRole())){
			transactionTemplate.execute(status -> {
				proProjectUserService.update(new LambdaUpdateWrapper<ProProjectUser>()
						.set(ProProjectUser::getRole, ProUserRoleEnum.PUR_1.getCode())
						.eq(ProProjectUser::getProjectId, proProjectUser.getProjectId())
				);
				proProjectUser.setRole(ProUserRoleEnum.PUR_0.getCode());
				proProjectUserService.updateById(proProjectUser);
				return null;
			});
		}
		return true;
	}

	/**
	 * 根据主键删除
	 * @param id Long 项目成员-ID
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean remove(Long id) {
		return proProjectUserService.removeById(id);
	}


	/**
	 * 根据主键获取
	 * @param id Long 项目成员-ID
	 * @return ProProjectUser
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public ProProjectUser get(Long id) {
		return proProjectUserService.getById(id);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProProjectUserQueryBO 查询参数
	 * @return Page<ProProjectUser>
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Page<ProProjectUser> query(ProProjectUserQueryBO queryBO) {
		MPQueryWrapper<ProProjectUser> wrapper = new MPQueryWrapper<>();
		wrapper.build(queryBO);
		return proProjectUserService.page(queryBO.getPage(),wrapper);
	}

	/**
	 * 获取项目成员选择下拉数据
	 * @param queryBO
	 * @return
	 */
	@Override
	public List<SysUser> userForSelect(ProProjectUserQueryBO queryBO) {
		List<Object> puId = proProjectUserService.listObjs(new LambdaQueryWrapper<ProProjectUser>().select(ProProjectUser::getUserId).eq(ProProjectUser::getProjectId, queryBO.getProjectId()));
		List<SysUser> sysUsers;
		if(queryBO.getIsMember()){
			sysUsers = userService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getId, puId).ne(SysUser::getId, Constants.ADMIN_ID));
		}else {
			sysUsers = userService.list(new LambdaQueryWrapper<SysUser>().notIn(SysUser::getId, puId).ne(SysUser::getId, Constants.ADMIN_ID));
		}
		return sysUsers;
	}

	/**
	 * 数据装配
	 * @param proProjectUser 源对象
	 * @return ProProjectUserVO
	 */
	@Override
	public ProProjectUserVO assemble(ProProjectUser proProjectUser) {
		ProProjectUserVO vo = JSON.parseObject(JSON.toJSONString(proProjectUser), ProProjectUserVO.class);
		SysUser user = userService.getById(vo.getUserId());
		vo.setNickName(user.getNickName());
		vo.setRoleDesc(ProUserRoleEnum.getMsgByCode(vo.getRole()));
		vo.setExtendList(JSON.parseArray(proProjectUser.getExtend(), ProExtendValueBO.class));
		return vo;
	}

	/**
	 * 数据装配-集合
	 * @param list 源对象集合
	 * @return List<ProProjectUserVO>
	 */
	@Override
	public List<ProProjectUserVO> assembleList(List<ProProjectUser> list) {
		if(ObjectUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		Set<Long> userIds = list.stream().map(ProProjectUser::getUserId).collect(Collectors.toSet());
		Map<Long, String> userMap = userService.listByIds(userIds).stream().collect(Collectors.toMap(SysUser::getId, SysUser::getNickName));
		List<ProProjectUserVO> voList = new ArrayList<>();
		for (ProProjectUser proProjectUser:list){
			ProProjectUserVO vo = JSON.parseObject(JSON.toJSONString(proProjectUser), ProProjectUserVO.class);
			vo.setNickName(userMap.get(vo.getUserId()));
			vo.setRoleDesc(ProUserRoleEnum.getMsgByCode(vo.getRole()));
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 数据装配-分页
	 * @param page 源分页对象
	 * @return Page<ProProjectUserVO>
	 */
	@Override
	public Page<ProProjectUserVO> assemblePage(Page<ProProjectUser> page) {
		Page<ProProjectUserVO> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
		List<ProProjectUserVO> t = assembleList(page.getRecords());
		tPage.setRecords(t);
		return tPage;
	}

}
