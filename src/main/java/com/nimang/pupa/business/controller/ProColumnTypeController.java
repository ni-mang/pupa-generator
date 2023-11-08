package com.nimang.pupa.business.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProColumnType;
import com.nimang.pupa.base.model.proColumnType.ProColumnTypeAddBO;
import com.nimang.pupa.base.model.proColumnType.ProColumnTypeEditBO;
import com.nimang.pupa.base.model.proColumnType.ProColumnTypeQueryBO;
import com.nimang.pupa.base.model.proColumnType.ProColumnTypeVO;
import com.nimang.pupa.base.model.proMapper.ColumnMapper;
import com.nimang.pupa.business.service.BizProColumnTypeService;
import com.nimang.pupa.common.tool.webTool.R;
import com.nimang.pupa.common.tool.webTool.RPage;
import com.nimang.pupa.common.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户/列类型
 * @module pupa
 * @author JustHuman
 * @date 2023-09-08
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/column-type")
@RequiredArgsConstructor
public class ProColumnTypeController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final BizProColumnTypeService proColumnTypeService;

	/**
	 * 新增
	 * @param addBO ProColumnTypeAddBO 新增-业务数据包
	 * @return R<Long> ID
	 * @author JustHuman
	 * @date 2023-09-08
	 * @status developing
	 */
	@PostMapping("/add")
	public R<Long> add(@Validated @RequestBody ProColumnTypeAddBO addBO) {
		log.info(" 列类型-新增-开始 参数：{}", JSON.toJSONString(addBO));
		Long id = proColumnTypeService.add(addBO);
		return R.ok(id);
	}

	/**
	 * 修改
	 * @param editBO ProColumnTypeEditBO 修改-业务数据包
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-09-08
	 * @status developing
	 */
	@PutMapping("/edit")
	public R<Boolean> edit(@Validated @RequestBody ProColumnTypeEditBO editBO) {
		log.info(" 列类型-修改-开始 参数：{}", JSON.toJSONString(editBO));
		boolean result = proColumnTypeService.edit(editBO);
		return R.ok(result);
	}

	/**
	 * 根据主键删除
	 * @param id Long 列类型-ID
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-09-08
	 * @status developing
	 */
	@DeleteMapping("/remove")
	public R<Boolean> remove(@NotNull(message = "请输入ID") @RequestParam Long id) {
		log.info(" 列类型-根据主键删除-开始 参数：{}", id);
		boolean result = proColumnTypeService.remove(id);
		return R.ok(result);
	}

	/**
	 * 根据主键批量删除
	 * @param ids Long[] 列类型-ID数组
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-09-08
	 * @status developing
	 * @ignore
	 */
	@DeleteMapping("/removeBatch")
	public R<Boolean> removeBatch(@NotEmpty(message = "ID不能为空") @RequestParam List<Long> ids) {
		log.info(" 列类型-根据主键批量删除-开始 参数：{}", JSON.toJSONString(ids));
		boolean result = proColumnTypeService.removeBatch(ids);
		return R.ok(result);
	}

	/**
	 * 根据主键获取
	 * @param id Long 列类型-ID
	 * @return R<ProColumnTypeVO>
	 * @author JustHuman
	 * @date 2023-09-08
	 * @status developing
	 */
	@GetMapping("/get")
	public R<ProColumnTypeVO> get(@NotNull(message = "请输入ID") @RequestParam Long id) {
		log.info(" 列类型-根据主键获取-开始 参数：{}", id);
		ProColumnType proColumnType = proColumnTypeService.get(id);
		ProColumnTypeVO vo = proColumnTypeService.assemble(proColumnType);
		return R.ok(vo);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProColumnTypeQueryBO 查询参数
	 * @return RPage<ProColumnTypeVO>
	 * @author JustHuman
	 * @date 2023-09-08
	 * @status developing
	 */
	@GetMapping("/query")
	public RPage<ProColumnTypeVO> query( ProColumnTypeQueryBO queryBO) {
		log.info(" 列类型-条件查询-开始 参数：{}", JSON.toJSONString(queryBO));
		// 默认排序：按主键倒序
		if(queryBO.isEmptyOrderItem()){
			queryBO.putOrderItem(ProColumnType::getBrand, true);
			queryBO.putOrderItem(ProColumnType::getColumnType, true);
		}
		Page<ProColumnType> page = proColumnTypeService.query(queryBO);
		Page<ProColumnTypeVO> voPage = proColumnTypeService.assemblePage(page);
		return RPage.ok(voPage);
	}

	/**
	 * 获取列数据
	 * @param brand
	 * @return
	 */
	@GetMapping("/columnMapper")
	public R<List<ColumnMapper>> columnMapper(@NotNull(message = "请选择数据库品牌") Integer brand) {
		ProColumnTypeQueryBO queryBO = new ProColumnTypeQueryBO();
		queryBO.setBrand(brand);
		queryBO.putOrderItem(ProColumnType::getColumnType, true);
		Page<ProColumnType> page = proColumnTypeService.query(queryBO);
		List<ProColumnType> list = page.getRecords();
		List<ColumnMapper> boList = ConvertUtil.convertOfAll(list, ColumnMapper.class);
		return R.ok(boList);
	}
}
