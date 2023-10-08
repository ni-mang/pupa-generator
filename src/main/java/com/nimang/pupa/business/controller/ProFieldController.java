package com.nimang.pupa.business.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProField;
import com.nimang.pupa.base.model.proField.*;
import com.nimang.pupa.business.service.BizProFieldService;
import com.nimang.pupa.common.tool.webTool.R;
import com.nimang.pupa.common.tool.webTool.RPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 项目/表字段
 * @module pupa
 * @author LinLaichun
 * @date 2023-04-26
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/pro/field")
@RequiredArgsConstructor
public class ProFieldController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final BizProFieldService proFieldService;

	/**
	 * 新增
	 * @param bizModel ProFieldAddBO 新增-业务数据包
	 * @return R<Long> ID
	 * @author LinLaichun
	 * @date 2023-04-26
	 * @status released
	 */
	@PostMapping("/add")
	public R<Long> add(@Validated @RequestBody ProFieldAddBO bizModel) {
		log.info(" 表字段-新增-开始 参数：{}", JSON.toJSONString(bizModel));
		Long id = proFieldService.add(bizModel);
		log.info(" 表字段-新增-结束 结果：{}", id);
		return R.ok(id);
	}

	/**
	 * 修改
	 * @param bizModel ProFieldEditBO 修改-业务数据包
	 * @return R<Boolean>
	 * @author LinLaichun
	 * @date 2023-04-26
	 * @status released
	 */
	@PutMapping("/edit")
	public R<Boolean> edit(@Validated @RequestBody ProFieldEditBO bizModel) {
		log.info(" 表字段-修改-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proFieldService.edit(bizModel);
		log.info(" 表字段-修改-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 变更标识
	 * @param bizModel
	 * @return
	 */
	@PutMapping("/change")
	public R<Boolean> change(@Validated @RequestBody ProFieldFlagChangeBO bizModel) {
		log.info(" 表字段-变更标识-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proFieldService.change(bizModel);
		log.info(" 表字段-修改-变更标识 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键删除
	 * @param id Long 表字段-ID
	 * @return R<Boolean>
	 * @author LinLaichun
	 * @date 2023-04-26
	 * @status released
	 */
	@DeleteMapping("/remove")
	public R<Boolean> remove(@NotNull(message = "请输入ID") @RequestParam Long id) {
		log.info(" 表字段-根据主键删除-开始 参数：{}", id);
		boolean result = proFieldService.remove(id);
		log.info(" 表字段-根据主键删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键批量删除
	 * @param ids Long[] 表字段-ID数组
	 * @return R<Boolean>
	 * @author LinLaichun
	 * @date 2023-04-26
	 * @status released
	 */
	@DeleteMapping("/removeBatch")
	public R<Boolean> removeBatch(@NotEmpty(message = "ID不能为空") @RequestParam Long[] ids) {
		log.info(" 表字段-根据主键批量删除-开始 参数：{}", JSON.toJSONString(ids));
		boolean result = proFieldService.removeBatch(Arrays.asList(ids));
		log.info(" 表字段-根据主键批量删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键获取
	 * @param id Long 表字段-ID
	 * @return R<ProFieldVO>
	 * @author LinLaichun
	 * @date 2023-04-26
	 * @status released
	 */
	@GetMapping("/get")
	public R<ProFieldVO> get(@NotNull(message = "请输入ID") @RequestParam Long id) {
		log.info(" 表字段-根据主键获取-开始 参数：{}", id);
		ProField proField = proFieldService.get(id);
		ProFieldVO vo = proFieldService.assemble(proField);
		log.info(" 表字段-根据主键获取-结束 结果：{}", JSON.toJSONString(vo));
		return R.ok(vo);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProFieldQueryBO 查询参数
	 * @return RPage<ProFieldVO>
	 * @author LinLaichun
	 * @date 2023-04-26
	 * @status released
	 */
	@GetMapping("/query")
	public RPage<ProFieldVO> query( ProFieldQueryBO queryBO) {
		log.info(" 表字段-条件查询-开始 参数：{}", JSON.toJSONString(queryBO));
		// 默认排序：按顺位正序
		if(queryBO.isEmptyOrderItem()){
			queryBO.putOrderItem(ProField::getOrdinalPosition, true);
		}
		Page<ProField> page = proFieldService.query(queryBO);
		Page<ProFieldVO> voPage = proFieldService.assemblePage(page);
		log.info(" 表字段-条件查询-结束 总量：{}，本次获得：{}", voPage.getTotal(), voPage.getRecords().size());
		return RPage.ok(voPage);
	}
}
