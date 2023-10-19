package com.nimang.pupa.business.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProDatasource;
import com.nimang.pupa.base.model.proDatasource.*;
import com.nimang.pupa.business.service.BizProDatasourceService;
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
 * 项目/数据源
 * @module pupa
 * @author JustHuman
 * @date 2023-04-26
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/pro/datasource")
@RequiredArgsConstructor
public class ProDatasourceController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final BizProDatasourceService proDatasourceService;

	/**
	 * 新增
	 * @param bizModel ProDatasourceAddBO 新增-业务数据包
	 * @return R<Long> ID
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@PostMapping("/add")
	public R<Long> add(@Validated @RequestBody ProDatasourceAddBO bizModel) {
		log.info(" 数据源-新增-开始 参数：{}", JSON.toJSONString(bizModel));
		Long id = proDatasourceService.add(bizModel);
		log.info(" 数据源-新增-结束 结果：{}", id);
		return R.ok(id);
	}

	/**
	 * 修改
	 * @param bizModel ProDatasourceEditBO 修改-业务数据包
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@PutMapping("/edit")
	public R<Boolean> edit(@Validated @RequestBody ProDatasourceEditBO bizModel) {
		log.info(" 数据源-修改-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proDatasourceService.edit(bizModel);
		log.info(" 数据源-修改-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键删除
	 * @param id Long 数据源-ID
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@DeleteMapping("/remove")
	public R<Boolean> remove(@NotNull(message = "请输入ID") @RequestParam Long id) {
		log.info(" 数据源-根据主键删除-开始 参数：{}", id);
		boolean result = proDatasourceService.remove(id);
		log.info(" 数据源-根据主键删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键批量删除
	 * @param ids Long[] 数据源-ID数组
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@DeleteMapping("/removeBatch")
	public R<Boolean> removeBatch(@NotEmpty(message = "ID不能为空") @RequestParam Long[] ids) {
		log.info(" 数据源-根据主键批量删除-开始 参数：{}", JSON.toJSONString(ids));
		boolean result = proDatasourceService.removeBatch(Arrays.asList(ids));
		log.info(" 数据源-根据主键批量删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键获取
	 * @param id Long 数据源-ID
	 * @return R<ProDatasourceVO>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@GetMapping("/get")
	public R<ProDatasourceVO> get(@NotNull(message = "请输入ID") @RequestParam Long id) {
		log.info(" 数据源-根据主键获取-开始 参数：{}", id);
		ProDatasource proDatasource = proDatasourceService.get(id);
		ProDatasourceVO vo = proDatasourceService.assemble(proDatasource);
		log.info(" 数据源-根据主键获取-结束 结果：{}", JSON.toJSONString(vo));
		return R.ok(vo);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProDatasourceQueryBO 查询参数
	 * @return RPage<ProDatasourceVO>
	 * @author JustHuman
	 * @date 2023-04-26
	 * @status released
	 */
	@GetMapping("/query")
	public RPage<ProDatasourceVO> query(ProDatasourceQueryBO queryBO) {
		log.info(" 数据源-条件查询-开始 参数：{}", JSON.toJSONString(queryBO));
		// 默认排序：按主键倒序
		if(queryBO.isEmptyOrderItem()){
			queryBO.putOrderItem(ProDatasource::getId, false);
		}
		Page<ProDatasource> page = proDatasourceService.query(queryBO);
		Page<ProDatasourceVO> voPage = proDatasourceService.assemblePage(page);
		log.info(" 数据源-条件查询-结束 总量：{}，本次获得：{}", voPage.getTotal(), voPage.getRecords().size());
		return RPage.ok(voPage);
	}

	/**
	 * 同步
	 * @param bizModel IdBO>
	 * @return R<Integer>
	 * @author JustHuman
	 * @date 2023-04-28
	 * @status released
	 */
	@PostMapping("/pull")
	public R<Integer> pull(@Validated @RequestBody ProDatasourcePullBO bizModel) {
		log.info(" 数据源-同步-开始 参数：{}", JSON.toJSONString(bizModel));
		Integer count = proDatasourceService.doPull(bizModel);
		log.info(" 数据源-同步-结束 结果：{}", count);
		return R.ok(count);
	}

}
