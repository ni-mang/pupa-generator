package com.nimang.pupa.business.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProMapper;
import com.nimang.pupa.base.model.proMapper.ProMapperAddBO;
import com.nimang.pupa.base.model.proMapper.ProMapperEditBO;
import com.nimang.pupa.base.model.proMapper.ProMapperQueryBO;
import com.nimang.pupa.base.model.proMapper.ProMapperVO;
import com.nimang.pupa.business.service.BizProMapperService;
import com.nimang.pupa.common.tool.webTool.R;
import com.nimang.pupa.common.tool.webTool.RPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 配置/数据映射
 * @module pupa
 * @author JustHuman
 * @date 2023-09-08
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/cfg/mapper")
@RequiredArgsConstructor
public class ProMapperController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final BizProMapperService proMapperService;

	/**
	 * 新增
	 * @param addBO ProMapperAddBO 新增-业务数据包
	 * @return R<Long> ID
	 * @author JustHuman
	 * @date 2023-09-08
	 * @status developing
	 */
	@PostMapping("/add")
	public R<Long> add(@Validated @RequestBody ProMapperAddBO addBO) {
		log.info(" 数据映射-新增-开始 参数：{}", JSON.toJSONString(addBO));
		Long id = proMapperService.add(addBO);
		log.info(" 数据映射-新增-结束 结果：{}",  id);
		return R.ok(id);
	}

	/**
	 * 修改
	 * @param editBO ProMapperEditBO 修改-业务数据包
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-09-08
	 * @status developing
	 */
	@PutMapping("/edit")
	public R<Boolean> edit(@Validated @RequestBody ProMapperEditBO editBO) {
		log.info(" 数据映射-修改-开始 参数：{}",  JSON.toJSONString(editBO));
		boolean result = proMapperService.edit(editBO);
		log.info(" 数据映射-修改-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键删除
	 * @param id Long 数据映射-ID
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-09-08
	 * @status developing
	 */
	@DeleteMapping("/remove")
	public R<Boolean> remove(@NotNull(message = "请输入ID") @RequestParam Long id) {
		log.info(" 数据映射-根据主键删除-开始 参数：{}", id);
		boolean result = proMapperService.remove(id);
		log.info(" 数据映射-根据主键删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键批量删除
	 * @param ids Long[] 数据映射-ID数组
	 * @return R<Boolean>
	 * @author JustHuman
	 * @date 2023-09-08
	 * @status developing
	 * @ignore
	 */
	@DeleteMapping("/removeBatch")
	public R<Boolean> removeBatch(@NotEmpty(message = "ID不能为空") @RequestParam List<Long> ids) {
		log.info(" 数据映射-根据主键批量删除-开始 参数：{}", JSON.toJSONString(ids));
		boolean result = proMapperService.removeBatch(ids);
		log.info(" 数据映射-根据主键批量删除-结束 操作完成");
		return R.ok(result);
	}

	/**
	 * 根据主键获取
	 * @param id Long 数据映射-ID
	 * @return R<ProMapperVO>
	 * @author JustHuman
	 * @date 2023-09-08
	 * @status developing
	 */
	@GetMapping("/get")
	public R<ProMapperVO> get(@NotNull(message = "请输入ID") @RequestParam Long id) {
		log.info(" 数据映射-根据主键获取-开始 参数：{}", id);
		ProMapper proMapper = proMapperService.get(id);
		ProMapperVO vo = proMapperService.assemble(proMapper);
		log.info(" 数据映射-根据主键获取-结束 结果：{}", JSON.toJSONString(vo));
		return R.ok(vo);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProMapperQueryBO 查询参数
	 * @return RPage<ProMapperVO>
	 * @author JustHuman
	 * @date 2023-09-08
	 * @status developing
	 */
	@GetMapping("/query")
	public RPage<ProMapperVO> query( ProMapperQueryBO queryBO) {
		log.info(" 数据映射-条件查询-开始 参数：{}", JSON.toJSONString(queryBO));
		// 默认排序：按主键倒序
		if(queryBO.isEmptyOrderItem()){
			queryBO.putOrderItem(ProMapper::getId, false);
		}
		Page<ProMapper> page = proMapperService.query(queryBO);
		Page<ProMapperVO> voPage = proMapperService.assemblePage(page);
		log.info(" 数据映射-条件查询-结束 总量：{}，本次获得：{}", voPage.getTotal(), voPage.getRecords().size());
		return RPage.ok(voPage);
	}
}
