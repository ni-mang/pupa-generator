package com.nimang.pupa.business.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProTable;
import com.nimang.pupa.base.model.proTable.ProTableAddBO;
import com.nimang.pupa.base.model.proTable.ProTableEditBO;
import com.nimang.pupa.base.model.proTable.ProTableQueryBO;
import com.nimang.pupa.base.model.proTable.ProTableVO;
import com.nimang.pupa.business.service.BizProTableService;
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
 * 项目/表
 * @module pupa
 * @author JustHuman
 * @date 2023-04-26
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/pro/table")
@RequiredArgsConstructor
public class ProTableController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final BizProTableService proTableService;

	/**
	 * 新增
	 * @param bizModel ProTableAddBO 新增-业务数据包
	 * @return R<Long> ID
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@PostMapping("/add")
	public R<Long> add(@Validated @RequestBody ProTableAddBO bizModel) {
		log.info(" 表单-新增-开始 参数：{}", JSON.toJSONString(bizModel));
		Long id = proTableService.add(bizModel);
		log.info(" 表单-新增-结束 结果：{}", id);
		return R.ok(id);
	}

	/**
	 * 修改
	 * @param bizModel ProTableEditBO 修改-业务数据包
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@PutMapping("/edit")
	public R<Boolean> edit(@Validated @RequestBody ProTableEditBO bizModel) {
		log.info(" 表单-修改-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proTableService.edit(bizModel);
		log.info(" 表单-修改-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键删除
	 * @param id Long 表单-ID
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@DeleteMapping("/remove")
	public R<Boolean> remove(@NotNull(message = "请输入ID") @RequestParam Long id) {
		log.info(" 表单-根据主键删除-开始 参数：{}", id);
		boolean result = proTableService.remove(id);
		log.info(" 表单-根据主键删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键批量删除
	 * @param ids Long[] 表单-ID数组
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@DeleteMapping("/removeBatch")
	public R<Boolean> removeBatch(@NotEmpty(message = "ID不能为空") @RequestParam Long[] ids) {
		log.info(" 表单-根据主键批量删除-开始 参数：{}", JSON.toJSONString(ids));
		boolean result = proTableService.removeBatch(Arrays.asList(ids));
		log.info(" 表单-根据主键批量删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键获取
	 * @param id Long 表单-ID
	 * @return R<ProTableVO>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@GetMapping("/get")
	public R<ProTableVO> get(@NotNull(message = "请输入ID") @RequestParam Long id) {
		log.info(" 表单-根据主键获取-开始 参数：{}", id);
		ProTable proTable = proTableService.get(id);
		ProTableVO vo = proTableService.assemble(proTable);
		log.info(" 表单-根据主键获取-结束 结果：{}", JSON.toJSONString(vo));
		return R.ok(vo);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProTableQueryBO 查询参数
	 * @return RPage<ProTableVO>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@GetMapping("/query")
	public RPage<ProTableVO> query( ProTableQueryBO queryBO) {
		log.info(" 表单-条件查询-开始 参数：{}", JSON.toJSONString(queryBO));
		if(queryBO.isEmptyOrderItem()){
			queryBO.putOrderItem(ProTable::getTableName, true);
		}
		Page<ProTable> page = proTableService.query(queryBO);
		Page<ProTableVO> voPage = proTableService.assemblePage(page);
		log.info(" 表单-条件查询-结束 总量：{}，本次获得：{}", voPage.getTotal(), voPage.getRecords().size());
		return RPage.ok(voPage);
	}
}
