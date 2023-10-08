package com.nimang.pupa.business.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProMapper;
import com.nimang.pupa.base.model.proMapper.*;
import com.nimang.pupa.base.service.IProMapperService;
import com.nimang.pupa.business.service.BizProMapperService;
import com.nimang.pupa.common.constants.ExceptionConstants;
import com.nimang.pupa.common.enums.proTemp.ProTempLangEnum;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.tool.mp.query.MPQueryWrapper;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.SnowFlakeIdGen;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 数据映射-业务接口实现
 * @author LinLaichun
 * @date 2023-08-09
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class BizProMapperServiceImpl implements BizProMapperService {

	private final SnowFlakeIdGen snowFlakeIdGen;
	private final IProMapperService proMapperService;

	/**
	 * 新增
	 * @param addBO ProMapperAddBO 新增数据
	 * @return Long ID
	 * @author LinLaichun
	 * @date 2023-08-09
	 */
	@Override
	public Long add(ProMapperAddBO addBO) {
		long count = proMapperService.count(new LambdaQueryWrapper<ProMapper>()
				.eq(ProMapper::getConfigId, addBO.getConfigId()).eq(ProMapper::getBrand, addBO.getBrand()).eq(ProMapper::getLang, addBO.getLang()));
		if(count > 0){
			throw new ApiException(ExceptionConstants.EXISTED_MAPPER);
		}
		ProMapper proMapper = ConvertUtil.convertOfEntity(addBO, ProMapper.class);
		Long id = snowFlakeIdGen.nextId();
		proMapper.setId(id);
		proMapper.setMapper(JSON.toJSONString(addBO.getMapperList()));
		proMapperService.save(proMapper);
		return id;
	}

	/**
	 * 修改
	 * @param editBO ProMapperEditBO 修改数据
	 * @return Boolean
	 * @author LinLaichun
	 * @date 2023-08-09
	 */
	@Override
	public Boolean edit(ProMapperEditBO editBO) {
		long count = proMapperService.count(new LambdaQueryWrapper<ProMapper>()
				.ne(ProMapper::getId, editBO.getId()).eq(ProMapper::getConfigId, editBO.getConfigId()).eq(ProMapper::getBrand, editBO.getBrand()).eq(ProMapper::getLang, editBO.getLang()));
		if(count > 0){
			throw new ApiException(ExceptionConstants.EXISTED_MAPPER);
		}
		ProMapper proMapper = proMapperService.getById(editBO.getId());
		ConvertUtil.convertOfEntity(editBO, proMapper);
		proMapper.setMapper(JSON.toJSONString(editBO.getMapperList()));
		return proMapperService.updateById(proMapper);
	}

	/**
	 * 根据主键删除
	 * @param id Long 数据映射配置-ID
	 * @return Boolean
	 * @author LinLaichun
	 * @date 2023-08-09
	 */
	@Override
	public Boolean remove(Long id) {
		return proMapperService.removeById(id);
	}

	/**
	 * 根据主键批量删除
	 * @param ids List<Long> 数据映射配置-ID集合
	 * @return Boolean
	 * @author LinLaichun
	 * @date 2023-08-09
	 */
	@Override
	public Boolean removeBatch(List<Long> ids) {
		return proMapperService.removeByIds(ids);
	}

	/**
	 * 根据主键获取
	 * @param id Long 数据映射配置-ID
	 * @return ProMapper
	 * @author LinLaichun
	 * @date 2023-08-09
	 */
	@Override
	public ProMapper get(Long id) {
		return proMapperService.getById(id);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProMapperQueryBO 查询参数
	 * @return Page<ProMapper>
	 * @author LinLaichun
	 * @date 2023-08-09
	 */
	@Override
	public Page<ProMapper> query(ProMapperQueryBO queryBO) {
		MPQueryWrapper<ProMapper> wrapper = new MPQueryWrapper<>();
		wrapper.build(queryBO);
		return proMapperService.page(queryBO.getPage(),wrapper);
	}

	/**
	 * 数据装配
	 * @param proMapper 源对象
	 * @return ProMapperVO
	 */
	@Override
	public ProMapperVO assemble(ProMapper proMapper) {
		ProMapperVO vo = ConvertUtil.convertOfEntity(proMapper, ProMapperVO.class);
		vo.setMapperList(JSON.parseArray(proMapper.getMapper(), ColumnMapper.class));
		return vo;
	}

	/**
	 * 数据装配-集合
	 * @param list 源对象集合
	 * @return List<ProMapperVO>
	 */
	@Override
	public List<ProMapperVO> assembleList(List<ProMapper> list) {
		if(ObjectUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		List<ProMapperVO> voList = new ArrayList<>();
		for (ProMapper proMapper:list){
			ProMapperVO vo = ConvertUtil.convertOfEntity(proMapper, ProMapperVO.class);
			vo.setBrandDesc(DatasourceBrandEnum.getMsgByCode(vo.getBrand()));
			vo.setLangDesc(ProTempLangEnum.getMsgByCode(vo.getLang()));
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 数据装配-分页
	 * @param page 源分页对象
	 * @return Page<ProMapperVO>
	 */
	@Override
	public Page<ProMapperVO> assemblePage(Page<ProMapper> page) {
		Page<ProMapperVO> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
		List<ProMapperVO> t = assembleList(page.getRecords());
		tPage.setRecords(t);
		return tPage;
	}

}
