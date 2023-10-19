package com.nimang.pupa.business.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProTemplate;
import com.nimang.pupa.base.model.proTemplate.ProTemplateAddBO;
import com.nimang.pupa.base.model.proTemplate.ProTemplateEditBO;
import com.nimang.pupa.base.model.proTemplate.ProTemplateQueryBO;
import com.nimang.pupa.base.model.proTemplate.ProTemplateVO;
import com.nimang.pupa.business.service.BizProTemplateService;
import com.nimang.pupa.common.pojo.IdBO;
import com.nimang.pupa.common.pojo.IdsBO;
import com.nimang.pupa.common.pojo.StatusChangeBO;
import com.nimang.pupa.common.tool.webTool.R;
import com.nimang.pupa.common.tool.webTool.RPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 配置/模板
 * @module pupa
 * @author JustHuman
 * @date 2023-04-21
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/cfg/template")
@RequiredArgsConstructor
public class ProTemplateController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final BizProTemplateService proTemplateService;

	/**
	 * 新增
	 * @param bizModel ProTemplateAddBO 新增-业务数据包
	 * @return R<Long> ID
	 * @author JustHuman
	 * @date 2023-04-21
	 * @status released
	 */
	@PostMapping("/add")
	public R<Long> add(@Validated @RequestBody ProTemplateAddBO bizModel) {
		log.info(" 模板-新增-开始 参数：{}", JSON.toJSONString(bizModel));
		Long id = proTemplateService.add(bizModel);
		log.info(" 模板-新增-结束 结果：{}", id);
		return R.ok(id);
	}

	/**
	 * 修改
	 * @param bizModel ProTemplateEditBO 修改-业务数据包
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-21
	 * @status released
	 */
	@PutMapping("/edit")
	public R<Boolean> edit(@Validated @RequestBody ProTemplateEditBO bizModel) {
		log.info(" 模板-修改-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proTemplateService.edit(bizModel);
		log.info(" 模板-修改-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 状态变更
	 * @param bizModel StatusChangeBO 状态变更-业务数据包
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-06-30
	 * @status released
	 */
	@PutMapping("/change")
	public R<Boolean> change(@Validated @RequestBody StatusChangeBO bizModel) {
		log.info(" 模板-状态变更-开始 参数：{}", JSON.toJSONString(bizModel));
		boolean result = proTemplateService.change(bizModel);
		log.info(" 模板-状态变更-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键删除
	 * @param bo IdBO 模板-ID
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-21
	 * @status released
	 */
	@DeleteMapping("/remove")
	public R<Boolean> remove(@Validated IdBO bo) {
		log.info(" 模板-根据主键删除-开始 参数：{}", bo.getId());
		boolean result = proTemplateService.remove(bo.getId());
		log.info(" 模板-根据主键删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键批量删除
	 * @param bo IdsBO 模板-ID数组
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-04-21
	 * @status released
	 */
	@DeleteMapping("/removeBatch")
	public R<Boolean> removeBatch(@Validated IdsBO bo) {
		log.info(" 模板-根据主键批量删除-开始 参数：{}", JSON.toJSONString(bo));
		boolean result = proTemplateService.removeBatch(bo.getIds());
		log.info(" 模板-根据主键批量删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键获取
	 * @param bo IdBO 模板-ID
	 * @return R<ProTemplateVO>
	 * @author JustHuman
	 * @date 2023-04-21
	 * @status released
	 */
	@GetMapping("/get")
	public R<ProTemplateVO> get(@Validated IdBO bo) {
		log.info(" 模板-根据主键获取-开始 参数：{}", bo.getId());
		ProTemplate proTemplate = proTemplateService.get(bo.getId());
		ProTemplateVO vo = proTemplateService.assemble(proTemplate);
		log.info(" 模板-根据主键获取-结束 结果：{}", JSON.toJSONString(vo));
		return R.ok(vo);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProTemplateQueryBO 查询参数
	 * @return RPage<ProTemplateVO>
	 * @author JustHuman
	 * @date 2023-03-01
	 * @status released
	 */
	@GetMapping("/query")
	public RPage<ProTemplateVO> query(@Validated ProTemplateQueryBO queryBO) {
		log.info(" 模板-条件查询-开始 参数：{}", JSON.toJSONString(queryBO));
		// 默认排序：按主键倒序
		if(queryBO.isEmptyOrderItem()){
			queryBO.putOrderItem(ProTemplate::getId, false);
		}
		Page<ProTemplate> page = proTemplateService.query(queryBO);
		Page<ProTemplateVO> voPage = proTemplateService.assemblePage(page);
		log.info(" 模板-条件查询-结束 总量：{}，本次获得：{}", voPage.getTotal(), voPage.getRecords().size());
		return RPage.ok(voPage);
	}
}
