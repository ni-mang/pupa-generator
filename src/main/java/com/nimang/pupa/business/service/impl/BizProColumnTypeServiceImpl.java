package com.nimang.pupa.business.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nimang.pupa.base.entity.ProColumnType;
import com.nimang.pupa.base.model.proColumnType.ProColumnTypeAddBO;
import com.nimang.pupa.base.model.proColumnType.ProColumnTypeEditBO;
import com.nimang.pupa.base.model.proColumnType.ProColumnTypeQueryBO;
import com.nimang.pupa.base.model.proColumnType.ProColumnTypeVO;
import com.nimang.pupa.base.service.IProColumnTypeService;
import com.nimang.pupa.business.service.BizProColumnTypeService;
import com.nimang.pupa.common.constants.ExceptionConstants;
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
 * 列类型-业务接口实现
 * @author LinLaichun
 * @date 2023-09-08
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class BizProColumnTypeServiceImpl implements BizProColumnTypeService {

	private final SnowFlakeIdGen snowFlakeIdGen;
	private final IProColumnTypeService proColumnTypeService;

	/**
	 * 新增
	 * @param addBO ProColumnTypeAddBO 新增数据
	 * @return Long ID
	 * @author LinLaichun
	 * @date 2023-09-08
	 */
	@Override
	public Long add(ProColumnTypeAddBO addBO) {
		long count = proColumnTypeService.count(new LambdaQueryWrapper<ProColumnType>()
				.eq(ProColumnType::getBrand, addBO.getBrand()).eq(ProColumnType::getColumnType, addBO.getColumnType()));
		if(count > 0){
			throw new ApiException(ExceptionConstants.EXISTED_COLUMN);
		}

		ProColumnType proColumnType = ConvertUtil.convertOfEntity(addBO, ProColumnType.class);
		Long id = snowFlakeIdGen.nextId();
		proColumnType.setId(id);
		proColumnTypeService.save(proColumnType);
		return id;
	}

	/**
	 * 修改
	 * @param editBO ProColumnTypeEditBO 修改数据
	 * @return Boolean
	 * @author LinLaichun
	 * @date 2023-09-08
	 */
	@Override
	public Boolean edit(ProColumnTypeEditBO editBO) {
		long count = proColumnTypeService.count(new LambdaQueryWrapper<ProColumnType>()
				.ne(ProColumnType::getId, editBO.getId()).eq(ProColumnType::getBrand, editBO.getBrand()).eq(ProColumnType::getColumnType, editBO.getColumnType()));
		if(count > 0){
			throw new ApiException(ExceptionConstants.EXISTED_COLUMN);
		}

		ProColumnType proColumnType = proColumnTypeService.getById(editBO.getId());
		ConvertUtil.convertOfEntity(editBO, proColumnType);
		return proColumnTypeService.updateById(proColumnType);
	}

	/**
	 * 根据主键删除
	 * @param id Long 列类型-ID
	 * @return Boolean
	 * @author LinLaichun
	 * @date 2023-09-08
	 */
	@Override
	public Boolean remove(Long id) {
		return proColumnTypeService.removeById(id);
	}

	/**
	 * 根据主键批量删除
	 * @param ids List<Long> 列类型-ID集合
	 * @return Boolean
	 * @author LinLaichun
	 * @date 2023-09-08
	 */
	@Override
	public Boolean removeBatch(List<Long> ids) {
		return proColumnTypeService.removeByIds(ids);
	}

	/**
	 * 根据主键获取
	 * @param id Long 列类型-ID
	 * @return ProColumnType
	 * @author LinLaichun
	 * @date 2023-09-08
	 */
	@Override
	public ProColumnType get(Long id) {
		return proColumnTypeService.getById(id);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProColumnTypeQueryBO 查询参数
	 * @return Page<ProColumnType>
	 * @author LinLaichun
	 * @date 2023-09-08
	 */
	@Override
	public Page<ProColumnType> query(ProColumnTypeQueryBO queryBO) {
		MPQueryWrapper<ProColumnType> wrapper = new MPQueryWrapper<>();
		wrapper.build(queryBO);
		return proColumnTypeService.page(queryBO.getPage(),wrapper);
	}

	/**
	 * 数据装配
	 * @param proColumnType 源对象
	 * @return ProColumnTypeVO
	 */
	@Override
	public ProColumnTypeVO assemble(ProColumnType proColumnType) {
		ProColumnTypeVO vo = ConvertUtil.convertOfEntity(proColumnType, ProColumnTypeVO.class);
		return vo;
	}

	/**
	 * 数据装配-集合
	 * @param list 源对象集合
	 * @return List<ProColumnTypeVO>
	 */
	@Override
	public List<ProColumnTypeVO> assembleList(List<ProColumnType> list) {
		if(ObjectUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		List<ProColumnTypeVO> voList = new ArrayList<>();
		for (ProColumnType proColumnType:list){
			ProColumnTypeVO vo = ConvertUtil.convertOfEntity(proColumnType, ProColumnTypeVO.class);
			vo.setBrandDesc(DatasourceBrandEnum.getMsgByCode(vo.getBrand()));
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 数据装配-分页
	 * @param page 源分页对象
	 * @return Page<ProColumnTypeVO>
	 */
	@Override
	public Page<ProColumnTypeVO> assemblePage(Page<ProColumnType> page) {
		Page<ProColumnTypeVO> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
		List<ProColumnTypeVO> t = assembleList(page.getRecords());
		tPage.setRecords(t);
		return tPage;
	}

}
