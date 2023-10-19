package com.nimang.pupa.business.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.nimang.pupa.base.entity.ProDatasource;
import com.nimang.pupa.base.entity.ProTable;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import com.nimang.pupa.base.model.proTable.ProTableAddBO;
import com.nimang.pupa.base.model.proTable.ProTableEditBO;
import com.nimang.pupa.base.model.proTable.ProTableQueryBO;
import com.nimang.pupa.base.model.proTable.ProTableVO;
import com.nimang.pupa.base.service.IProDatasourceService;
import com.nimang.pupa.base.service.IProTableService;
import com.nimang.pupa.business.service.BizProTableService;
import com.nimang.pupa.common.constants.ExceptionConstants;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.tool.mp.query.MPQueryWrapper;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.SnowFlakeIdGen;
import com.nimang.pupa.dbExtends.DataTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



/**
 * 表单-业务接口实现
 * @author JustHuman
 * @date 2023-04-26
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class BizProTableServiceImpl implements BizProTableService {

	private final SnowFlakeIdGen snowFlakeIdGen;
	private final IProTableService proTableService;
	private final IProDatasourceService proDatasourceService;
	/**
	 * 新增
	 * @param addBO ProTableAddBO 新增数据
	 * @return Long ID
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Long add(ProTableAddBO addBO) {
		ProDatasource datasource = proDatasourceService.getById(addBO.getSourceId());
		if(ObjectUtil.isNull(datasource)){
			throw new ApiException(ExceptionConstants.NOT_FIND_POINT_DATA);
		}
		long count = proTableService.count(new LambdaQueryWrapper<ProTable>()
				.eq(ProTable::getSourceId, addBO.getSourceId())
				.eq(ProTable::getTableName, addBO.getTableName()));
		if(count>0){
			throw new ApiException(ExceptionConstants.EXISTED_TABLE);
		}
		ProTable proTable = ConvertUtil.convertOfEntity(addBO, ProTable.class);
		Long id = snowFlakeIdGen.nextId();
		proTable.setId(id);
		proTable.setTableSchema(datasource.getSchema());
		proTable.setExistFlag(false);
		proTable.makeInfixName(datasource.getPrefix());

		if(proTable.getModule().endsWith(DataTool.SP_STR)){
			proTable.setTableComment(proTable.getModule() + proTable.getCnName());
		}else {
			proTable.setTableComment(proTable.getModule() + DataTool.SP_STR + proTable.getCnName());
		}


		if(ObjectUtil.isNotEmpty(addBO.getExtendList())){
			proTable.setExtend(JSON.toJSONString(addBO.getExtendList()));
		}
		proTableService.save(proTable);
		return id;
	}

	/**
	 * 修改
	 * @param editBO ProTableEditBO 修改数据
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean edit(ProTableEditBO editBO) {
		ProDatasource datasource = proDatasourceService.getById(editBO.getSourceId());
		if(ObjectUtil.isNull(datasource)){
			throw new ApiException(ExceptionConstants.NOT_FIND_POINT_DATA);
		}
		long count = proTableService.count(new LambdaQueryWrapper<ProTable>()
				.eq(ProTable::getSourceId, editBO.getSourceId())
				.eq(ProTable::getTableName, editBO.getTableName())
				.ne(ProTable::getId, editBO.getId()));
		if(count>0){
			throw new ApiException(ExceptionConstants.EXISTED_TABLE);
		}
		ProTable proTable = proTableService.getById(editBO.getId());
		ConvertUtil.convertOfEntity(editBO, proTable);
		proTable.makeInfixName(datasource.getPrefix());

		if(proTable.getModule().endsWith(DataTool.SP_STR)){
			proTable.setTableComment(proTable.getModule() + proTable.getCnName());
		}else {
			proTable.setTableComment(proTable.getModule() + DataTool.SP_STR + proTable.getCnName());
		}

		if(ObjectUtil.isNotEmpty(editBO.getExtendList())){
			proTable.setExtend(JSON.toJSONString(editBO.getExtendList()));
		}
		return proTableService.updateById(proTable);
	}

	/**
	 * 根据主键删除
	 * @param id Long 表单-ID
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean remove(Long id) {
		return proTableService.removeById(id);
	}

	/**
	 * 根据主键批量删除
	 * @param ids List<Long> 表单-ID集合
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean removeBatch(List<Long> ids) {
		return proTableService.removeByIds(ids);
	}

	/**
	 * 根据主键获取
	 * @param id Long 表单-ID
	 * @return ProTable
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public ProTable get(Long id) {
		return proTableService.getById(id);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProTableQueryBO 查询参数
	 * @return Page<ProTable>
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Page<ProTable> query(ProTableQueryBO queryBO) {
		MPQueryWrapper<ProTable> wrapper = new MPQueryWrapper<>();
		wrapper.build(queryBO);
		return proTableService.page(queryBO.getPage(),wrapper);
	}

	/**
	 * 数据装配
	 * @param proTable 源对象
	 * @return ProTableVO
	 */
	@Override
	public ProTableVO assemble(ProTable proTable) {
		ProTableVO vo = JSON.parseObject(JSON.toJSONString(proTable), ProTableVO.class);
		vo.setExtendList(JSON.parseArray(proTable.getExtend(), ProExtendValueBO.class));
		return vo;
	}

	/**
	 * 数据装配-集合
	 * @param list 源对象集合
	 * @return List<ProTableVO>
	 */
	@Override
	public List<ProTableVO> assembleList(List<ProTable> list) {
		if(ObjectUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		List<ProTableVO> voList = new ArrayList<>();
		for (ProTable proTable:list){
			ProTableVO vo = JSON.parseObject(JSON.toJSONString(proTable), ProTableVO.class);
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 数据装配-分页
	 * @param page 源分页对象
	 * @return Page<ProTableVO>
	 */
	@Override
	public Page<ProTableVO> assemblePage(Page<ProTable> page) {
		Page<ProTableVO> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
		List<ProTableVO> t = assembleList(page.getRecords());
		tPage.setRecords(t);
		return tPage;
	}

}
