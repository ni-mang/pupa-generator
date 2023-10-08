package com.nimang.pupa.business.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.SysUser;
import com.nimang.pupa.base.model.sysUser.*;
import com.nimang.pupa.business.service.BizUserService;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.pojo.IdBO;
import com.nimang.pupa.common.pojo.IdsBO;
import com.nimang.pupa.common.tool.webTool.R;
import com.nimang.pupa.common.tool.webTool.RPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户/用户信息
 * @module pupa
 * @date 2023-04-12
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/user")
@RequiredArgsConstructor
public class UserController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final BizUserService userService;

	/**
	 * 新增
	 * @param userBaseBO UserAddBO 新增-业务数据包
	 * @return R<Long> ID
	 * @date 2023-04-12
	 * @status released
	 */
	@PostMapping("/add")
	public R<Long> add(@Validated @RequestBody UserBaseBO userBaseBO) {
		log.info(" 用户信息-新增-开始 参数：{}",  JSON.toJSONString(userBaseBO));
		Long id = userService.add(userBaseBO);
		return R.ok(id);
	}

	/**
	 * 修改
	 * @param userOpEditBO UserOpEditBO 修改-业务数据包
	 * @return R<Boolean>
	 * @date 2023-04-12
	 * @status released
	 */
	@PutMapping("/edit")
	public R<Boolean> edit(@Validated @RequestBody UserOpEditBO userOpEditBO) {
		log.info(" 用户信息-修改-开始 参数：{}",  JSON.toJSONString(userOpEditBO));
		boolean result = userService.edit(userOpEditBO);
		return R.ok(result);
	}

	/**
	 * 状态变更
	 * @param changeStatusBO ChangeStatusBO 状态参数
	 * @return R<Boolean>
	 * @date 2023-04-12
	 * @status released
	 */
	@PutMapping("/changeStatus")
	public R<Boolean> changeStatus(@Validated @RequestBody ChangeStatusBO changeStatusBO) {
		log.info(" 用户信息-状态变更-开始 参数：{}",  JSON.toJSONString(changeStatusBO));
		boolean result = userService.changeStatus(changeStatusBO);
		return R.ok(result);
	}

	/**
	 * 根据主键删除
	 * @param bo IdBO 用户信息-ID
	 * @return R<Boolean>
	 * @date 2023-04-12
	 * @status released
	 */
	@DeleteMapping("/remove")
	public R<Boolean> remove(@Validated IdBO bo) {
		log.info(" 用户信息-根据主键删除-开始 参数：{}", bo.getId());
		boolean result = userService.remove(bo.getId());
		return R.ok(result);
	}

	/**
	 * 根据主键批量删除
	 * @param bo IdsBO 用户信息-ID数组
	 * @return R<Boolean>
	 * @date 2023-04-12
	 * @status released
	 */
	@DeleteMapping("/removeBatch")
	public R<Boolean> removeBatch(@Validated IdsBO bo) {
		log.info(" 用户信息-根据主键批量删除-开始 参数：{}", JSON.toJSONString(bo));
		boolean result = userService.removeBatch(bo.getIds());
		return R.ok(result);
	}

	/**
	 * 根据主键获取
	 * @param bo IdBO 用户信息-ID
	 * @return R<UserVO>
	 * @date 2023-04-12
	 * @status released
	 */
	@GetMapping("/get")
	public R<UserVO> get(@Validated IdBO bo) {
		log.info(" 用户信息-根据主键获取-开始 参数：{}", bo.getId());
		SysUser user = userService.get(bo.getId());
		UserVO vo = userService.assemble(user);
		return R.ok(vo);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO UserQueryBO 查询参数
	 * @return RPage<UserVO>
	 * @date 2023-03-01
	 * @status released
	 */
	@GetMapping("/query")
	public RPage<UserVO> query(UserQueryBO queryBO) {
		log.info(" 用户信息-条件查询-开始 参数：{}", JSON.toJSONString(queryBO));
		// 默认排序：按主键倒序
		if(queryBO.isEmptyOrderItem()){
			queryBO.putOrderItem(SysUser::getStatus, false);
			queryBO.putOrderItem(SysUser::getId, false);
		}
		Page<SysUser> page = userService.query(queryBO);
		Page<UserVO> voPage = userService.assemblePage(page);
		return RPage.ok(voPage);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO UserQueryBO 查询参数
	 * @return R<List<UserVO>>
	 * @date 2023-07-25
	 * @status released
	 */
	@GetMapping("/userForSelect")
	public R<List<UserVO>> userForSelect(UserQueryBO queryBO) {
		log.info(" 用户信息-条件查询-开始 参数：{}", JSON.toJSONString(queryBO));
		// 默认排序：按主键倒序
		if(queryBO.isEmptyOrderItem()){
			queryBO.putOrderItem(SysUser::getId, false);
		}
		queryBO.setStatus(StatusEnum.STATUS_1.getCode());
		Page<SysUser> page = userService.query(queryBO);
		List<UserVO> voPage = userService.assembleList(page.getRecords());
		return R.ok(voPage);
	}
}
