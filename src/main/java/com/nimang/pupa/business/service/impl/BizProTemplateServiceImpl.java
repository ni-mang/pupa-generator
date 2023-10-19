package com.nimang.pupa.business.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.nimang.pupa.base.entity.ProTemplate;
import com.nimang.pupa.base.model.proTemplate.ProTemplateAddBO;
import com.nimang.pupa.base.model.proTemplate.ProTemplateEditBO;
import com.nimang.pupa.base.model.proTemplate.ProTemplateQueryBO;
import com.nimang.pupa.base.model.proTemplate.ProTemplateVO;
import com.nimang.pupa.base.service.IProTemplateService;
import com.nimang.pupa.business.service.BizProTemplateService;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.enums.proTemp.ProTempTypeEnum;
import com.nimang.pupa.common.pojo.StatusChangeBO;
import com.nimang.pupa.common.tool.mp.query.MPQueryWrapper;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.SnowFlakeIdGen;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



/**
 * 模板-业务接口实现
 * @author JustHuman
 * @date 2023-04-21
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class BizProTemplateServiceImpl implements BizProTemplateService {

	private final SnowFlakeIdGen snowFlakeIdGen;
	private final IProTemplateService proTemplateService;

	/**
	 * 新增
	 * @param addBO ProTemplateAddBO 新增数据
	 * @return Long ID
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Long add(ProTemplateAddBO addBO) {
		long exist = proTemplateService.count(new LambdaQueryWrapper<ProTemplate>().eq(ProTemplate::getConfigId, addBO.getConfigId()).eq(ProTemplate::getName, addBO.getName()));
		if(exist > 0){
			throw new ApiException("已存在名为“" + addBO.getName() + "”的扩展项");
		}
		ProTemplate proTemplate = ConvertUtil.convertOfEntity(addBO, ProTemplate.class);
		Long id = snowFlakeIdGen.nextId();
		proTemplate.setId(id);
		proTemplate.setStatus(StatusEnum.STATUS_1.getCode());
		proTemplateService.save(proTemplate);
		return id;
	}

	/**
	 * 修改
	 * @param editBO ProTemplateEditBO 修改数据
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Boolean edit(ProTemplateEditBO editBO) {
		ProTemplate proTemplate = proTemplateService.getById(editBO.getId());
		ConvertUtil.convertOfEntity(editBO, proTemplate);
		return proTemplateService.updateById(proTemplate);
	}

	/**
	 * 状态变更
	 * @param changeBO StatusChangeBO 修改数据
	 * @return
	 */
	@Override
	public Boolean change(StatusChangeBO changeBO) {
		ProTemplate proTemplate = ConvertUtil.convertOfEntity(changeBO, ProTemplate.class);
		return proTemplateService.updateById(proTemplate);
	}

	/**
	 * 根据主键删除
	 * @param id Long 模板-ID
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Boolean remove(Long id) {
		return proTemplateService.removeById(id);
	}

	/**
	 * 根据主键批量删除
	 * @param ids List<Long> 模板-ID集合
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Boolean removeBatch(List<Long> ids) {
		return proTemplateService.removeByIds(ids);
	}

	/**
	 * 根据主键获取
	 * @param id Long 模板-ID
	 * @return ProTemplate
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public ProTemplate get(Long id) {
		return proTemplateService.getById(id);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProTemplateQueryBO 查询参数
	 * @return Page<ProTemplate>
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Page<ProTemplate> query(ProTemplateQueryBO queryBO) {
		MPQueryWrapper<ProTemplate> wrapper = new MPQueryWrapper<>();
		wrapper.build(queryBO);
		return proTemplateService.page(queryBO.getPage(),wrapper);
	}

	/**
	 * 数据装配
	 * @param proTemplate 源对象
	 * @return ProTemplateVO
	 */
	@Override
	public ProTemplateVO assemble(ProTemplate proTemplate) {
		ProTemplateVO vo = ConvertUtil.convertOfEntity(proTemplate, ProTemplateVO.class);
		return vo;
	}

	/**
	 * 数据装配-集合
	 * @param list 源对象集合
	 * @return List<ProTemplateVO>
	 */
	@Override
	public List<ProTemplateVO> assembleList(List<ProTemplate> list) {
		if(ObjectUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		List<ProTemplateVO> voList = new ArrayList<>();
		for (ProTemplate proTemplate:list){
			ProTemplateVO vo = ConvertUtil.convertOfEntity(proTemplate, ProTemplateVO.class);
			vo.setTempTypeDesc(ProTempTypeEnum.getMsgByCode(vo.getTempType()));
			vo.setContent("");
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 数据装配-分页
	 * @param page 源分页对象
	 * @return Page<ProTemplateVO>
	 */
	@Override
	public Page<ProTemplateVO> assemblePage(Page<ProTemplate> page) {
		Page<ProTemplateVO> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
		List<ProTemplateVO> t = assembleList(page.getRecords());
		tPage.setRecords(t);
		return tPage;
	}

}
