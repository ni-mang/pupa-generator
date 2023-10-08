package com.nimang.pupa.business.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProProject;
import com.nimang.pupa.base.model.proProject.ProProjectAddBO;
import com.nimang.pupa.base.model.proProject.ProProjectEditBO;
import com.nimang.pupa.base.model.proProject.ProProjectQueryBO;
import com.nimang.pupa.base.model.proProject.ProProjectVO;
import com.nimang.pupa.business.service.BizProProjectService;
import com.nimang.pupa.common.constants.ExceptionConstants;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.pojo.IdBO;
import com.nimang.pupa.common.pojo.IdsBO;
import com.nimang.pupa.common.tool.webTool.R;
import com.nimang.pupa.common.tool.webTool.RPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 项目/项目
 * @module pupa
 * @author LinLaichun
 * @date 2023-04-26
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/pro/project")
@RequiredArgsConstructor
public class ProProjectController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final BizProProjectService proProjectService;

	/**
	 * 新增
	 * @param bizModel ProProjectAddBO 新增-业务数据包
	 * @return R<Long> ID
	 * @author LinLaichun
	 * @date 2023-04-26
	 * @status released
	 */
	@PostMapping("/add")
	public R<Long> add(@Validated @RequestBody ProProjectAddBO bizModel) {
		log.info(" 项目-新增-开始 参数：{}", JSON.toJSONString(bizModel));
		Long id = proProjectService.add(bizModel);
		log.info(" 项目-新增-结束 结果：{}", id);
		return R.ok(id);
	}

	/**
	 * 修改
	 * @param bizModel ProProjectEditBO 修改-业务数据包
	 * @return R<Boolean>
	 * @author LinLaichun
	 * @date 2023-04-26
	 * @status released
	 */
	@PutMapping("/edit")
	public R<Boolean> edit(@Validated @RequestBody ProProjectEditBO bizModel) {
		log.info(" 项目-修改-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proProjectService.edit(bizModel);
		log.info(" 项目-修改-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 克隆
	 * @param bizModel IdBO> 目标ID
	 * @return R<Long> 新配置ID
	 * @author LinLaichun
	 * @date 2023-04-21
	 * @status released
	 */
	@PostMapping("/clone")
	public R<Long> clone(@Validated @RequestBody IdBO bizModel) {
		log.info(" 项目-克隆-开始 参数：{}", JSON.toJSONString(bizModel));
		Long id = proProjectService.clone(bizModel.getId());
		return R.ok(id);
	}

	/**
	 * 根据主键删除
	 * @param bo IdBO 项目-ID
	 * @return R<Boolean>
	 * @author LinLaichun
	 * @date 2023-04-26
	 * @status released
	 */
	@DeleteMapping("/remove")
	public R<Boolean> remove(@Validated IdBO bo) {
		log.info(" 项目-根据主键删除-开始 参数：{}", bo.getId());
		boolean result = proProjectService.remove(bo.getId());
		log.info(" 项目-根据主键删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键批量删除
	 * @param bo IdsBO 项目-ID数组
	 * @return R<Boolean>
	 * @author LinLaichun
	 * @date 2023-04-26
	 * @status released
	 */
	@DeleteMapping("/removeBatch")
	public R<Boolean> removeBatch(@Validated IdsBO bo) {
		log.info(" 项目-根据主键批量删除-开始 参数：{}", JSON.toJSONString(bo));
		boolean result = proProjectService.removeBatch(bo.getIds());
		log.info(" 项目-根据主键批量删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键获取
	 * @param bo IdBO 项目-ID
	 * @return R<ProProjectVO>
	 * @author LinLaichun
	 * @date 2023-04-26
	 * @status released
	 */
	@GetMapping("/get")
	public R<ProProjectVO> get(@Validated IdBO bo) {
		log.info(" 项目-根据主键获取-开始 参数：{}", bo.getId());
		ProProject proProject = proProjectService.get(bo.getId());
		ProProjectVO vo = proProjectService.assemble(proProject);
		log.info(" 项目-根据主键获取-结束 结果：{}", JSON.toJSONString(vo));
		return R.ok(vo);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProProjectQueryBO 查询参数
	 * @return RPage<ProProjectVO>
	 * @author LinLaichun
	 * @date 2023-04-26
	 * @status released
	 */
	@GetMapping("/query")
	public RPage<ProProjectVO> query( ProProjectQueryBO queryBO) {
		log.info(" 项目-条件查询-开始 参数：{}", JSON.toJSONString(queryBO));
		// 默认排序：按主键倒序
		if(queryBO.isEmptyOrderItem()){
			queryBO.putOrderItem(ProProject::getId, false);
		}
		Page<ProProject> page = proProjectService.query(queryBO);
		Page<ProProjectVO> voPage = proProjectService.assemblePage(page);
		log.info(" 项目-条件查询-结束 总量：{}，本次获得：{}", voPage.getTotal(), voPage.getRecords().size());
		return RPage.ok(voPage);
	}

	/**
	 * 导出选择项目
	 * @param bo
	 * @author LinLaichun
	 * @date 2023-07-18
	 * @status released
	 */
	@GetMapping("/exportSelect")
	public void exportSelect(@Validated IdsBO bo) {
		proProjectService.export(bo.getIds());
	}

	/**
	 * 导出全部项目
	 * @param queryBO
	 * @author LinLaichun
	 * @date 2023-07-18
	 * @status released
	 */
	@GetMapping("/exportAll")
	public void exportAll(ProProjectQueryBO queryBO) {
		proProjectService.export(queryBO);
	}

	/**
	 * 导入项目文件
	 * @param file
	 * @return
	 * @author LinLaichun
	 * @date 2023-07-18
	 * @status released
	 */
	@PostMapping("/importAll")
	public R<Integer> importAll(MultipartFile file) {
		if(!file.getOriginalFilename().endsWith(".pupa")){
			throw new ApiException(ExceptionConstants.INVALID_FILE);
		}
		Integer index = proProjectService.importAll(file);
		return R.ok(index);
	}
}
