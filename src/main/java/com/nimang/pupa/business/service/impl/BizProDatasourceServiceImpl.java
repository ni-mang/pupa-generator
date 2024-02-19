package com.nimang.pupa.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.nimang.pupa.base.entity.ProDatasource;
import com.nimang.pupa.base.entity.ProTable;
import com.nimang.pupa.base.service.IProDatasourceService;
import com.nimang.pupa.base.service.IProFieldService;
import com.nimang.pupa.base.service.IProTableService;
import com.nimang.pupa.dbExtends.IMetadataService;
import com.nimang.pupa.base.model.proDatasource.*;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import com.nimang.pupa.business.service.BizProDatasourceService;
import com.nimang.pupa.common.constants.ExceptionConstants;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import com.nimang.pupa.common.tool.mp.query.MPQueryWrapper;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.SnowFlakeIdGen;
import com.nimang.pupa.common.util.UserUtil;
import com.nimang.pupa.base.entity.ProField;
import com.nimang.pupa.base.entity.SourceInfo;
import com.nimang.pupa.base.service.IDatasourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 数据源-业务接口实现
 * @author JustHuman
 * @date 2023-04-26
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class BizProDatasourceServiceImpl implements BizProDatasourceService {

	private final SnowFlakeIdGen snowFlakeIdGen;
	private final TransactionTemplate transactionTemplate;
	private final IProDatasourceService proDatasourceService;
	private final IProTableService proTableService;
	private final IProFieldService proFieldService;

	private final List<String> ignoreFields = Arrays.asList("id","insertFlag","viewFlag","queryFlag","extend");
	/**
	 * 新增
	 * @param addBO ProDatasourceAddBO 新增数据
	 * @return Long ID
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Long add(ProDatasourceAddBO addBO) {
		long userId = UserUtil.getId();
		ProDatasource proDatasource = ConvertUtil.convertOfEntity(addBO, ProDatasource.class);
		Long id = snowFlakeIdGen.nextId();
		proDatasource.setId(id);
		proDatasource.setCreateBy(userId);
		if(ObjectUtil.isNotEmpty(addBO.getExtendList())){
			proDatasource.setExtend(JSON.toJSONString(addBO.getExtendList()));
		}
		proDatasourceService.save(proDatasource);
		return id;
	}

	/**
	 * 修改
	 * @param editBO ProDatasourceEditBO 修改数据
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean edit(ProDatasourceEditBO editBO) {
		ProDatasource proDatasource = proDatasourceService.getById(editBO.getId());
		ConvertUtil.convertOfEntity(editBO, proDatasource);
		if(ObjectUtil.isNotEmpty(editBO.getExtendList())){
			proDatasource.setExtend(JSON.toJSONString(editBO.getExtendList()));
		}
		return proDatasourceService.updateById(proDatasource);
	}

	/**
	 * 根据主键删除
	 * @param id Long 数据源-ID
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean remove(Long id) {
		transactionTemplate.execute(status -> {
			proDatasourceService.removeById(id);
			proTableService.remove(new LambdaQueryWrapper<ProTable>().eq(ProTable::getSourceId, id));
			proFieldService.remove(new LambdaQueryWrapper<ProField>().eq(ProField::getSourceId, id));
			return null;
		});
		return true;
	}

	/**
	 * 根据主键批量删除
	 * @param ids List<Long> 数据源-ID集合
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean removeBatch(List<Long> ids) {
		return proDatasourceService.removeByIds(ids);
	}

	/**
	 * 根据主键获取
	 * @param id Long 数据源-ID
	 * @return ProDatasource
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public ProDatasource get(Long id) {
		return proDatasourceService.getById(id);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProDatasourceQueryBO 查询参数
	 * @return Page<ProDatasource>
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Page<ProDatasource> query(ProDatasourceQueryBO queryBO) {
		MPQueryWrapper<ProDatasource> wrapper = new MPQueryWrapper<>();
		wrapper.build(queryBO);
		return proDatasourceService.page(queryBO.getPage(),wrapper);
	}

	/**
	 * 数据装配
	 * @param proDatasource 源对象
	 * @return ProDatasourceVO
	 */
	@Override
	public ProDatasourceVO assemble(ProDatasource proDatasource) {
		ProDatasourceVO vo = JSON.parseObject(JSON.toJSONString(proDatasource), ProDatasourceVO.class);
		vo.setExtendList(JSON.parseArray(proDatasource.getExtend(), ProExtendValueBO.class));
		vo.setBrandDesc(DatasourceBrandEnum.getMsgByCode(vo.getBrand()));
		return vo;
	}

	/**
	 * 数据装配-集合
	 * @param list 源对象集合
	 * @return List<ProDatasourceVO>
	 */
	@Override
	public List<ProDatasourceVO> assembleList(List<ProDatasource> list) {
		if(ObjectUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		List<ProDatasourceVO> voList = new ArrayList<>();
		for (ProDatasource proDatasource:list){
			ProDatasourceVO vo = JSON.parseObject(JSON.toJSONString(proDatasource), ProDatasourceVO.class);
			vo.setBrandDesc(DatasourceBrandEnum.getMsgByCode(vo.getBrand()));
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 数据装配-分页
	 * @param page 源分页对象
	 * @return Page<ProDatasourceVO>
	 */
	@Override
	public Page<ProDatasourceVO> assemblePage(Page<ProDatasource> page) {
		Page<ProDatasourceVO> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
		List<ProDatasourceVO> t = assembleList(page.getRecords());
		tPage.setRecords(t);
		return tPage;
	}

	/**
	 * 执行同步
	 * @param pullBO ProDatasourcePullBO 同步操作参数
	 * @return Integer 同步表数量
	 */
	@Override
	public Integer doPull(ProDatasourcePullBO pullBO) {
		List<String> tableNames = null;
		if(ObjectUtil.isNotEmpty(pullBO.getTableIds())){
			List<ProTable> tableList = proTableService.listByIds(pullBO.getTableIds());
			if(ObjectUtil.isEmpty(tableList)){
				throw new ApiException(ExceptionConstants.NOT_FIND_POINT_DATA);
			}
			tableNames = tableList.stream().map(ProTable::getTableName).collect(Collectors.toList());
		}

		ProDatasource datasource = proDatasourceService.getById(pullBO.getSourceId());
		if(ObjectUtil.isNull(datasource)){
			throw new ApiException(ExceptionConstants.NOT_FIND_POINT_DATA);
		}

		IMetadataService metadataService = getMetadataService(datasource.getBrand());

		// 获取新增、修改的表
		List<ProTable> addTableList = new ArrayList<>();
		List<ProTable> upTableList = new ArrayList<>();
		List<ProTable> findTableList = findTables(metadataService, datasource, tableNames, addTableList, upTableList);
		List<ProTable> allTableList = new ArrayList<>();
		allTableList.addAll(addTableList);
		allTableList.addAll(upTableList);

		// 获取新增、修改的列
		List<ProField> addFieldList = new ArrayList<>();
		List<ProField> upFieldList = new ArrayList<>();
		findFields(metadataService, datasource, allTableList, addFieldList, upFieldList);

		transactionTemplate.execute(status -> {
			if(ObjectUtil.isNotEmpty(addTableList)){
				proTableService.saveBatch(addTableList);
			}
			if(ObjectUtil.isNotEmpty(upTableList)){
				proTableService.updateBatchById(upTableList);
			}
			if(ObjectUtil.isNotEmpty(addFieldList)){
				proFieldService.saveBatch(addFieldList);
			}
			if(ObjectUtil.isNotEmpty(upFieldList)){
				proFieldService.updateBatchById(upFieldList);
			}
			return null;
		});
		return findTableList.size();

	}


	/**
	 * 获取新增、修改的表
	 * @param metadataService
	 * @param datasource
	 * @param tableNames
	 * @param addTableList
	 * @param upTableList
	 * @return
	 */
	public List<ProTable> findTables(IMetadataService metadataService, ProDatasource datasource, List<String> tableNames, List<ProTable> addTableList, List<ProTable> upTableList) {

		List<ProTable> findTableList = null;
		try {
			findTableList = metadataService.findTables(datasource, tableNames);
		} catch (PersistenceException e) {

			throw new ApiException(MessageFormat.format(ExceptionConstants.INVALID_DB_INFO, e.getMessage()));
		}
		LambdaQueryWrapper<ProTable> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(ProTable::getSourceId, datasource.getId());
		if(ObjectUtil.isNotEmpty(tableNames)){
			wrapper.in(ProTable::getTableName, tableNames);
		}
		List<ProTable> existTableList = proTableService.list(wrapper);
		if(ObjectUtil.isEmpty(existTableList)){
			addTableList.addAll(findTableList);
		}else {
			Map<String, ProTable> existTableMap = existTableList.stream().collect(Collectors.toMap(ProTable::getTableName, Function.identity()));
			for (ProTable table:findTableList){
				ProTable existTable = existTableMap.get(table.getTableName());
				if(ObjectUtil.isNull(existTable)){
					addTableList.add(table);
				}else {
					ConvertUtil.convertOfEntity(table, existTable, ignoreFields);
					upTableList.add(existTable);
					existTableList.remove(existTable);
				}
			}
			existTableList.forEach(t -> t.setExistFlag(false));
			upTableList.addAll(existTableList);
		}
		return findTableList;
	}

	/**
	 * 获取新增、修改的列
	 * @param metadataService
	 * @param datasource
	 * @param proTableList
	 * @param addFieldList
	 * @param upFieldList
	 */
	public void findFields(IMetadataService metadataService, ProDatasource datasource, List<ProTable> proTableList, List<ProField> addFieldList, List<ProField> upFieldList) {

		List<ProField> findFieldList = metadataService.findColumns(datasource, proTableList);

		List<Long> tableIds = proTableList.stream().map(ProTable::getId).collect(Collectors.toList());
		List<ProField> existFieldList = proFieldService.list(new LambdaQueryWrapper<ProField>().eq(ProField::getSourceId, datasource.getId()).in(ProField::getTableId, tableIds));
		if(ObjectUtil.isEmpty(existFieldList)){
			addFieldList.addAll(findFieldList);
		}else {
			Map<String, ProField> existFieldMap = existFieldList.stream().collect(Collectors.toMap(p -> p.getTableId() + p.getColumnName(), Function.identity()));
			for (ProField field:findFieldList){
				ProField existField = existFieldMap.get(field.getTableId() + field.getColumnName());
				if(ObjectUtil.isNull(existField)){
					addFieldList.add(field);
				}else {
					ConvertUtil.convertOfEntity(field, existField, ignoreFields);
					upFieldList.add(existField);
					existFieldList.remove(existField);
				}
			}
			existFieldList.forEach(t -> t.setExistFlag(false));
			upFieldList.addAll(existFieldList);
		}
	}

	/**
	 * 获取服务
	 * @param brand 数据库品牌
	 * @return IMetadataService
	 */
	private IMetadataService getMetadataService(Integer brand){
		Map<String, IMetadataService> beanMap =  SpringUtil.getBeansOfType(IMetadataService.class);
		List<IMetadataService> serviceList = beanMap.values()
				.stream()
				.filter(e-> e.isMatch(brand))
				.collect(Collectors.toList());
		if(CollectionUtil.isNotEmpty(serviceList)){
			if(serviceList.size() != 1){
				throw new IllegalStateException("获取数据库服务异常，存在多个服务:" + brand);
			}
			return serviceList.get(0);
		}
		throw new ApiException("无法获取数据库服务: " + brand);
	}
}
