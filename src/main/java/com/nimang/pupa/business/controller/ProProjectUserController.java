package com.nimang.pupa.business.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProProjectUser;
import com.nimang.pupa.base.entity.SysUser;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserAddBO;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserEditBO;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserQueryBO;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserVO;
import com.nimang.pupa.base.model.sysUser.UserVO;
import com.nimang.pupa.business.service.BizProProjectUserService;
import com.nimang.pupa.common.pojo.IdBO;
import com.nimang.pupa.common.tool.webTool.R;
import com.nimang.pupa.common.tool.webTool.RPage;
import com.nimang.pupa.common.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目/项目成员
 * @module pupa
 * @author JustHuman
 * @date 2023-04-26
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/pro/project-user")
@RequiredArgsConstructor
public class ProProjectUserController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final BizProProjectUserService proProjectUserService;

	/**
	 * 新增
	 * @param bizModel ProProjectUserAddBO 新增-业务数据包
	 * @return R<Long> 项目ID
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@PostMapping("/add")
	public R<Long> add(@Validated @RequestBody ProProjectUserAddBO bizModel) {
		log.info(" 项目成员-新增-开始 参数：{}", JSON.toJSONString(bizModel));
		Long projectId = proProjectUserService.add(bizModel);
		log.info(" 项目成员-新增-结束 结果：{}", projectId);
		return R.ok(projectId);
	}

	/**
	 * 修改
	 * @param bizModel ProProjectUserEditBO 修改-业务数据包
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@PutMapping("/edit")
	public R<Boolean> edit(@Validated @RequestBody ProProjectUserEditBO bizModel) {
		log.info(" 项目成员-修改-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proProjectUserService.edit(bizModel);
		log.info(" 项目成员-修改-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 授权转让
	 * @param bizModel ProProjectUserEditBO 转让-业务数据包
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-07-26
	 * @status released
	 */
	@PutMapping("/transfer")
	public R<Boolean> transfer(@Validated @RequestBody ProProjectUserEditBO bizModel) {
		log.info(" 项目成员-转让-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proProjectUserService.transfer(bizModel);
		log.info(" 项目成员-转让-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键删除
	 * @param bo Long IdBO-ID
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@DeleteMapping("/remove")
	public R<Boolean> remove(@Validated IdBO bo) {
		log.info(" 项目成员-根据主键删除-开始 参数：{}", bo.getId());
		boolean result = proProjectUserService.remove(bo.getId());
		log.info(" 项目成员-根据主键删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键获取
	 * @param bo Long IdBO-ID
	 * @return R<ProProjectUserVO>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@GetMapping("/get")
	public R<ProProjectUserVO> get(@Validated IdBO bo) {
		log.info(" 项目成员-根据主键获取-开始 参数：{}", bo);
		ProProjectUser proProjectUser = proProjectUserService.get(bo.getId());
		ProProjectUserVO vo = proProjectUserService.assemble(proProjectUser);
		log.info(" 项目成员-根据主键获取-结束 结果：{}", JSON.toJSONString(vo));
		return R.ok(vo);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProProjectUserQueryBO 查询参数
	 * @return RPage<ProProjectUserVO>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@GetMapping("/query")
	public RPage<ProProjectUserVO> query(@Validated ProProjectUserQueryBO queryBO) {
		log.info(" 项目成员-条件查询-开始 参数：{}", JSON.toJSONString(queryBO));
		// 默认排序：按主键倒序
		if(queryBO.isEmptyOrderItem()){
			queryBO.putOrderItem(ProProjectUser::getProjectId, false);
		}
		Page<ProProjectUser> page = proProjectUserService.query(queryBO);
		Page<ProProjectUserVO> voPage = proProjectUserService.assemblePage(page);
		log.info(" 项目成员-条件查询-结束 总量：{}，本次获得：{}", voPage.getTotal(), voPage.getRecords().size());
		return RPage.ok(voPage);
	}

	/**
	 * 获取项目成员选择下拉数据
	 * @param queryBO ProProjectUserQueryBO 查询参数
	 * @return R<List<UserVO>>
	 * @author JustHuman
	 * @date 2023-06-10
	 * @status released
	 */
	@GetMapping("/userForSelect")
	public R<List<UserVO>> userForSelect(@Validated ProProjectUserQueryBO queryBO) {
		List<SysUser> userList = proProjectUserService.userForSelect(queryBO);
		List<UserVO> voList = ConvertUtil.convertOfAll(userList,UserVO.class);
		return R.ok(voList);
	}
}
