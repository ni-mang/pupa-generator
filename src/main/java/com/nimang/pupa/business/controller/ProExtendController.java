package com.nimang.pupa.business.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProExtend;
import com.nimang.pupa.base.model.proExtend.*;
import com.nimang.pupa.business.service.BizProExtendService;
import com.nimang.pupa.common.pojo.IdBO;
import com.nimang.pupa.common.pojo.IdsBO;
import com.nimang.pupa.common.pojo.StatusChangeBO;
import com.nimang.pupa.common.tool.webTool.R;
import com.nimang.pupa.common.tool.webTool.RPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 配置/扩展
 * @module pupa
 * @author LinLaichun
 * @date 2023-04-21
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/cfg/extend")
@RequiredArgsConstructor
public class ProExtendController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final BizProExtendService proExtendService;

	/**
	 * 新增
	 * @param bizModel ProExtendAddBO 新增-业务数据包
	 * @return R<Long> ID
	 * @author LinLaichun
	 * @date 2023-04-21
	 * @status released
	 */
	@PostMapping("/add")
	public R<Long> add(@Validated @RequestBody ProExtendAddBO bizModel) {
		log.info(" 扩展-新增-开始 参数：{}", JSON.toJSONString(bizModel));
		Long id = proExtendService.add(bizModel);
		log.info(" 扩展-新增-结束 结果：{}", id);
		return R.ok(id);
	}

	/**
	 * 修改
	 * @param bizModel ProExtendEditBO 修改-业务数据包
	 * @return R<Boolean>
	 * @author LinLaichun
	 * @date 2023-04-21
	 * @status released
	 */
	@PutMapping("/edit")
	public R<Boolean> edit(@Validated @RequestBody ProExtendEditBO bizModel) {
		log.info(" 扩展-修改-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proExtendService.edit(bizModel);
		log.info(" 扩展-修改-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 状态变更
	 * @param bizModel StatusChangeBO 状态变更-业务数据包
	 * @return R<Boolean>
	 * @author LinLaichun
	 * @date 2023-06-30
	 * @status released
	 */
	@PutMapping("/change")
	public R<Boolean> change(@Validated @RequestBody StatusChangeBO bizModel) {
		log.info(" 扩展-状态变更-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proExtendService.change(bizModel);
		log.info(" 扩展-状态变更-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键删除
	 * @param bo IdBO 扩展-ID
	 * @return R<Boolean>
	 * @author LinLaichun
	 * @date 2023-04-21
	 * @status released
	 */
	@DeleteMapping("/remove")
	public R<Boolean> remove(@Validated IdBO bo) {
		log.info(" 扩展-根据主键删除-开始 参数：{}", bo.getId());
		boolean result = proExtendService.remove(bo.getId());
		log.info(" 扩展-根据主键删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键批量删除
	 * @param bo IdsBO 扩展-ID数组
	 * @return R<Boolean>
	 * @author LinLaichun
	 * @date 2023-04-21
	 * @status released
	 */
	@DeleteMapping("/removeBatch")
	public R<Boolean> removeBatch(@Validated IdsBO bo) {
		log.info(" 扩展-根据主键批量删除-开始 参数：{}", JSON.toJSONString(bo));
		boolean result = proExtendService.removeBatch(bo.getIds());
		log.info(" 扩展-根据主键批量删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键获取
	 * @param bo IdBO 扩展-ID
	 * @return R<ProExtendVO>
	 * @author LinLaichun
	 * @date 2023-04-21
	 * @status released
	 */
	@GetMapping("/get")
	public R<ProExtendVO> get(@Validated IdBO bo) {
		log.info(" 扩展-根据主键获取-开始 参数：{}", bo.getId());
		ProExtend proExtend = proExtendService.get(bo.getId());
		ProExtendVO vo = proExtendService.assemble(proExtend);
		log.info(" 扩展-根据主键获取-结束 结果：{}", JSON.toJSONString(vo));
		return R.ok(vo);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProExtendQueryBO 查询参数
	 * @return RPage<ProExtendVO>
	 * @author LinLaichun
	 * @date 2023-04-21
	 * @status released
	 */
	@GetMapping("/query")
	public RPage<ProExtendVO> query(@Validated ProExtendQueryBO queryBO) {
		log.info(" 扩展-条件查询-开始 参数：{}", JSON.toJSONString(queryBO));
		// 默认排序：按作用域正序，按主键倒序
		if(queryBO.isEmptyOrderItem()){
			queryBO.putOrderItem(ProExtend::getScope, true);
			queryBO.putOrderItem(ProExtend::getId, true);
		}
		Page<ProExtend> page = proExtendService.query(queryBO);
		Page<ProExtendVO> voPage = proExtendService.assemblePage(page);
		log.info(" 扩展-条件查询-结束 总量：{}，本次获得：{}", voPage.getTotal(), voPage.getRecords().size());
		return RPage.ok(voPage);
	}

	/**
	 * 展示所有扩展信息
	 * @param bo
	 * @return R<List<ProExtendShowVO>>
	 * @author LinLaichun
	 * @date 2023-04-28
	 * @status released
	 */
	@GetMapping("/showAllExtend")
	public R<List<ProExtendShowVO>> showAllExtend(@Validated IdBO bo) {
		log.info(" 扩展-展示所有扩展信息-开始 参数：{}", bo.getId());
		List<ProExtendShowVO> voList = proExtendService.showAllExtend(bo.getId());
		log.info(" 扩展-展示所有扩展信息-结束 结果：{}", JSON.toJSONString(voList));
		return R.ok(voList);
	}
}
