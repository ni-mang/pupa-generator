package com.nimang.pupa.business.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProConfig;
import com.nimang.pupa.base.model.gen.GenDataInfo;
import com.nimang.pupa.business.service.BizProConfigService;
import com.nimang.pupa.common.constants.ExceptionConstants;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.pojo.IdBO;
import com.nimang.pupa.common.pojo.IdsBO;
import com.nimang.pupa.common.pojo.StatusChangeBO;
import com.nimang.pupa.common.tool.webTool.R;
import com.nimang.pupa.common.tool.webTool.RPage;
import com.nimang.pupa.base.model.proConfig.ProConfigAddBO;
import com.nimang.pupa.base.model.proConfig.ProConfigEditBO;
import com.nimang.pupa.base.model.proConfig.ProConfigQueryBO;
import com.nimang.pupa.base.model.proConfig.ProConfigVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 配置/配置
 * @module pupa
 * @author JustHuman
 * @date 2023-04-21
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/cfg/config")
@RequiredArgsConstructor
public class ProConfigController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final BizProConfigService proConfigService;

	/**
	 * 新增
	 * @param addBO ProConfigAddBO 新增-业务数据包
	 * @return R<Long> ID
	 * @author JustHuman
	 * @date 2023-04-21
	 * @status released
	 */
	@PostMapping("/add")
	public R<Long> add(@Validated @RequestBody ProConfigAddBO addBO) {
		log.info(" 配置-新增-开始 参数：{}", JSON.toJSONString(addBO));
		Long id = proConfigService.add(addBO);
		return R.ok(id);
	}

	/**
	 * 修改
	 * @param bizModel ProConfigEditBO 修改-业务数据包
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-21
	 * @status released
	 */
	@PutMapping("/edit")
	public R<Boolean> edit(@Validated @RequestBody ProConfigEditBO bizModel) {
		log.info(" 配置-修改-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proConfigService.edit(bizModel);
		return R.ok(result);
	}

	/**
	 * 状态变更
	 * @param bizModel StatusChangeBO> 状态变更-业务数据包
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-06-27
	 * @status released
	 */
	@PutMapping("/change")
	public R<Boolean> change(@Validated @RequestBody StatusChangeBO bizModel) {
		log.info(" 配置-状态变更-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proConfigService.change(bizModel);
		return R.ok(result);
	}

	/**
	 * 克隆
	 * @param bizModel IdBO> 目标对象ID
	 * @return R<Long> 新配置ID
	 * @author JustHuman
	 * @date 2023-04-21
	 * @status released
	 */
	@PostMapping("/clone")
	public R<Long> clone(@Validated @RequestBody IdBO bizModel) {
		log.info(" 配置-克隆-开始 参数：{}", JSON.toJSONString(bizModel));
		Long id = proConfigService.clone(bizModel.getId());
		return R.ok(id);
	}

	/**
	 * 根据主键删除
	 * @param bo IdBO 配置-ID
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-21
	 * @status released
	 */
	@DeleteMapping("/remove")
	public R<Boolean> remove(@Validated IdBO bo) {
		log.info(" 配置-根据主键删除-开始 参数：{}", bo.getId());
		boolean result = proConfigService.remove(bo.getId());
		return R.ok(result);
	}

	/**
	 * 根据主键获取
	 * @param bo IdBO 配置-ID
	 * @return R<ProConfigVO>
	 * @author JustHuman
	 * @date 2023-04-21
	 * @status released
	 */
	@GetMapping("/get")
	public R<ProConfigVO> get(@Validated IdBO bo) {
		log.info(" 配置-根据主键获取-开始 参数：{}", bo.getId());
		ProConfig proConfig = proConfigService.get(bo.getId());
		ProConfigVO vo = proConfigService.assemble(proConfig);
		return R.ok(vo);
	}

	/**
	 * 获取参数说明
	 * @param bo IdBO 配置-ID
	 * @return R<List<GenDataVO>>
	 * @author JustHuman
	 * @date 2023-07-12
	 */
	@GetMapping("/showConfigInfo")
	public R<List<GenDataInfo>> showConfigInfo(@Validated IdBO bo) {
		log.info(" 配置-获取参数说明-开始 参数：{}", bo.getId());
		List<GenDataInfo> infoList = proConfigService.showConfigInfo(bo.getId());
		return R.ok(infoList);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProConfigQueryBO 查询参数
	 * @return RPage<ProConfigVO>
	 * @author JustHuman
	 * @date 2023-04-21
	 * @status released
	 */
	@GetMapping("/query")
	public RPage<ProConfigVO> query(ProConfigQueryBO queryBO) {
		log.info(" 配置-条件查询-开始 参数：{}", JSON.toJSONString(queryBO));
		// 默认排序：按主键倒序
		if(queryBO.isEmptyOrderItem()){
			queryBO.putOrderItem(ProConfig::getId, false);
		}
		Page<ProConfig> page = proConfigService.query(queryBO);
		Page<ProConfigVO> voPage = proConfigService.assemblePage(page);
		return RPage.ok(voPage);
	}

	/**
	 * 获取下拉选择列表
	 * @return
	 * @author JustHuman
	 * @date 2023-04-24
	 * @status released
	 */
	@GetMapping("/listForSelect")
	public R<List<ProConfigVO>> listForSelect() {
		log.info(" 配置-获取下拉选择列表-开始");
		List<ProConfig> list = proConfigService.listForSelect();
		List<ProConfigVO> voList = proConfigService.assembleList(list);
		return R.ok(voList);
	}

	/**
	 * 导出选择配置
	 * @param bo
	 * @author JustHuman
	 * @date 2023-07-17
	 * @status released
	 */
	@GetMapping("/exportSelect")
	public void exportSelect(@Validated IdsBO bo) {
		proConfigService.export(bo.getIds());
	}

	/**
	 * 导出全部配置
	 * @param queryBO
	 * @author JustHuman
	 * @date 2023-07-17
	 * @status released
	 */
	@GetMapping("/exportAll")
	public void exportAll(ProConfigQueryBO queryBO) {
		proConfigService.export(queryBO);
	}

	/**
	 * 导入配置文件
	 * @param file
	 * @return
	 * @author JustHuman
	 * @date 2023-07-17
	 * @status released
	 */
	@PostMapping("/importAll")
	public R<Integer> importAll(MultipartFile file) {
		if(!file.getOriginalFilename().endsWith(".pupa")){
			throw new ApiException(ExceptionConstants.INVALID_FILE);
		}
		Integer index = proConfigService.importAll(file);
		return R.ok(index);
	}
}
