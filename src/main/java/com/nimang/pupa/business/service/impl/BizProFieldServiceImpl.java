package com.nimang.pupa.business.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.nimang.pupa.base.entity.ProField;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import com.nimang.pupa.base.model.proField.*;
import com.nimang.pupa.base.service.IProFieldService;
import com.nimang.pupa.business.service.BizProFieldService;
import com.nimang.pupa.common.constants.ExceptionConstants;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.tool.mp.query.MPQueryWrapper;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.SnowFlakeIdGen;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



/**
 * 表字段-业务接口实现
 * @author JustHuman
 * @date 2023-04-26
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class BizProFieldServiceImpl implements BizProFieldService {

	private final SnowFlakeIdGen snowFlakeIdGen;
	private final IProFieldService proFieldService;

	/**
	 * 新增
	 * @param addBO ProFieldAddBO 新增数据
	 * @return Long ID
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Long add(ProFieldAddBO addBO) {
		long count = proFieldService.count(new LambdaQueryWrapper<ProField>()
				.eq(ProField::getTableId, addBO.getTableId())
				.eq(ProField::getColumnName, addBO.getColumnName()));
		if(count>0){
			throw new ApiException(ExceptionConstants.EXISTED_FIELD);
		}
		ProField proField = ConvertUtil.convertOfEntity(addBO, ProField.class);
		Long id = snowFlakeIdGen.nextId();
		proField.setId(id);
		proField.setExistFlag(false);
		process(proField, addBO.getExtendList());
		proFieldService.save(proField);
		return id;
	}

	/**
	 * 修改
	 * @param editBO ProFieldEditBO 修改数据
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean edit(ProFieldEditBO editBO) {
		long count = proFieldService.count(new LambdaQueryWrapper<ProField>()
				.eq(ProField::getTableId, editBO.getTableId())
				.eq(ProField::getColumnName, editBO.getColumnName())
				.ne(ProField::getId, editBO.getId()));
		if(count>0){
			throw new ApiException(ExceptionConstants.EXISTED_FIELD);
		}
		ProField proField = proFieldService.getById(editBO.getId());
		ConvertUtil.convertOfEntity(editBO, proField);
		process(proField, editBO.getExtendList());
		return proFieldService.updateById(proField);
	}

	/**
	 * 变更标识
	 * @param flagChangeBO ProFieldFlagChangeBO 修改数据
	 * @return
	 */
	@Override
	public Boolean change(ProFieldFlagChangeBO flagChangeBO) {
		ProField proField = proFieldService.getById(flagChangeBO.getId());
		switch (flagChangeBO.getFiled()){
			case "insertFlag":
				proField.setInsertFlag(flagChangeBO.getValue());
				break;
			case "viewFlag":
				proField.setViewFlag(flagChangeBO.getValue());
				break;
			case "queryFlag":
				proField.setQueryFlag(flagChangeBO.getValue());
				break;
		}
		return proFieldService.updateById(proField);
	}

	public void process(ProField proField, List<ProExtendValueBO> extendList) {
		proField.setIsNullable(proField.getRequiredFlag()?"NO":"YES");
		proField.setColumnKey(proField.getPrimary()?"PRI":"");
		if(proField.getPrimary()){
			proField.setExtra(proField.getIdType().equals("AUTO")?"auto_increment":"");
		}
		if(ObjectUtil.isNotEmpty(extendList)){
			proField.setExtend(JSON.toJSONString(extendList));
		}
	}

	/**
	 * 根据主键删除
	 * @param id Long 表字段-ID
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean remove(Long id) {
		return proFieldService.removeById(id);
	}

	/**
	 * 根据主键批量删除
	 * @param ids List<Long> 表字段-ID集合
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean removeBatch(List<Long> ids) {
		return proFieldService.removeByIds(ids);
	}

	/**
	 * 根据主键获取
	 * @param id Long 表字段-ID
	 * @return ProField
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public ProField get(Long id) {
		return proFieldService.getById(id);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProFieldQueryBO 查询参数
	 * @return Page<ProField>
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Page<ProField> query(ProFieldQueryBO queryBO) {
		MPQueryWrapper<ProField> wrapper = new MPQueryWrapper<>();
		wrapper.build(queryBO);
		return proFieldService.page(queryBO.getPage(),wrapper);
	}

	/**
	 * 数据装配
	 * @param proField 源对象
	 * @return ProFieldVO
	 */
	@Override
	public ProFieldVO assemble(ProField proField) {
		ProFieldVO vo = JSON.parseObject(JSON.toJSONString(proField), ProFieldVO.class);
		vo.setExtendList(JSON.parseArray(proField.getExtend(), ProExtendValueBO.class));
		return vo;
	}

	/**
	 * 数据装配-集合
	 * @param list 源对象集合
	 * @return List<ProFieldVO>
	 */
	@Override
	public List<ProFieldVO> assembleList(List<ProField> list) {
		if(ObjectUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		List<ProFieldVO> voList = new ArrayList<>();
		for (ProField proField:list){
			ProFieldVO vo = JSON.parseObject(JSON.toJSONString(proField), ProFieldVO.class);
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 数据装配-分页
	 * @param page 源分页对象
	 * @return Page<ProFieldVO>
	 */
	@Override
	public Page<ProFieldVO> assemblePage(Page<ProField> page) {
		Page<ProFieldVO> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
		List<ProFieldVO> t = assembleList(page.getRecords());
		tPage.setRecords(t);
		return tPage;
	}

}
