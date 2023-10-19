package com.nimang.pupa.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nimang.pupa.base.entity.*;
import com.nimang.pupa.base.model.exportAndImport.*;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import com.nimang.pupa.base.model.proProject.ProProjectAddBO;
import com.nimang.pupa.base.model.proProject.ProProjectEditBO;
import com.nimang.pupa.base.model.proProject.ProProjectQueryBO;
import com.nimang.pupa.base.model.proProject.ProProjectVO;
import com.nimang.pupa.base.model.proProjectUser.ProProjectUserVO;
import com.nimang.pupa.base.service.*;
import com.nimang.pupa.business.service.BizProConfigService;
import com.nimang.pupa.business.service.BizProProjectService;
import com.nimang.pupa.common.constants.Constants;
import com.nimang.pupa.common.constants.ExceptionConstants;
import com.nimang.pupa.common.enums.pro.ProUserRoleEnum;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;
import com.nimang.pupa.common.enums.user.UserTypeEnum;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.tool.mp.query.MPQueryWrapper;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.FileUtil;
import com.nimang.pupa.common.util.SnowFlakeIdGen;
import com.nimang.pupa.common.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 项目-业务接口实现
 * @author JustHuman
 * @date 2023-04-26
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class BizProProjectServiceImpl implements BizProProjectService {

	private final SnowFlakeIdGen snowFlakeIdGen;
	private final IProProjectService proProjectService;
	private final IProProjectUserService proProjectUserService;
	private final IProDatasourceService proDatasourceService;
	private final IProTableService proTableService;
	private final IProFieldService proFieldService;
	private final IProConfigService proConfigService;
	private final ISysUserService sysUserService;
	private final IProMapperService proMapperService;
	private final IProExtendService proExtendService;
	private final IProTemplateService proTemplateService;
	private final TransactionTemplate transactionTemplate;
	private final BizProConfigService bizProConfigService;

	/**
	 * 新增
	 * @param addBO ProProjectAddBO 新增数据
	 * @return Long ID
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Long add(ProProjectAddBO addBO) {
		long userId = UserUtil.getId();
		if(UserTypeEnum.VISITOR_2.equals(UserUtil.get().getType())){
			long max = 2;
			long count = proProjectService.count(new LambdaQueryWrapper<ProProject>().eq(ProProject::getCreateBy, userId));
			if(count == max){
				throw new ApiException(MessageFormat.format(ExceptionConstants.CANT_ADD_PROJECT_OF_DEMO, max));
			}
		}
		ProProject proProject = ConvertUtil.convertOfEntity(addBO, ProProject.class);
		Long id = snowFlakeIdGen.nextId();
		proProject.setId(id);
		proProject.setCreateBy(userId);
		if(ObjectUtil.isNotEmpty(addBO.getExtendList())){
			proProject.setExtend(JSON.toJSONString(addBO.getExtendList()));
		}else {
			proProject.setExtend(proExtendService.getJsonForScope(addBO.getConfigId(), ProExtendScopeEnum.PES_0));
		}
		ProProjectUser projectUser = ProProjectUser.builder()
				.id(snowFlakeIdGen.nextId())
				.userId(userId)
				.projectId(id)
				.role(ProUserRoleEnum.PUR_0.getCode())
				.extend(proExtendService.getJsonForScope(addBO.getConfigId(), ProExtendScopeEnum.PES_1)).build();

		transactionTemplate.execute(status -> {
			proProjectService.save(proProject);
			proProjectUserService.save(projectUser);
			return null;
		});
		return id;
	}

	/**
	 * 修改
	 * @param editBO ProProjectEditBO 修改数据
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean edit(ProProjectEditBO editBO) {
		ProProject proProject = proProjectService.getById(editBO.getId());
		ConvertUtil.convertOfEntity(editBO, proProject);
		if(ObjectUtil.isEmpty(editBO.getExtendList())){
			proProject.setExtend("");
		}else {
			proProject.setExtend(JSON.toJSONString(editBO.getExtendList()));
		}
		return proProjectService.updateById(proProject);
	}

	/**
	 * 克隆
	 * @param id Long 项目-ID
	 * @return
	 * @author LinLaichun
	 * @date 2023-04-26
	 */
	@Override
	public Long clone(Long id) {
		long userId = UserUtil.getId();
		SysUser user = UserUtil.get();
		if(UserTypeEnum.VISITOR_2.equals(user.getType())){
			throw new ApiException(ExceptionConstants.CANT_OPERATE_OF_DEMO);
		}
		ProProject targetProject = proProjectService.getById(id);
		if(ObjectUtil.isNull(targetProject)){
			throw new ApiException(ExceptionConstants.NOT_FIND_POINT_DATA);
		}
		String newName = targetProject.getName() + "_copy";

		long count = proProjectService.count(new MPJLambdaWrapper<ProProject>()
				.leftJoin(ProProjectUser.class, ProProjectUser::getProjectId, ProProject::getId)
				.eq(ProProjectUser::getUserId, userId)
				.eq(ProProjectUser::getRole, ProUserRoleEnum.PUR_0)
				.eq(ProProject::getName, newName));


		if(count > 0){
			throw new ApiException(MessageFormat.format(ExceptionConstants.EXISTED_FOR_COPY, newName));
		}
		List<ProDatasource>  targetDatasourceList = proDatasourceService.list(new LambdaQueryWrapper<ProDatasource>().eq(ProDatasource::getProjectId, id));
		List<ProTable>  targetTableList = proTableService.list(new LambdaQueryWrapper<ProTable>().eq(ProTable::getProjectId, id));
		List<ProField>  targetFieldList = proFieldService.list(new LambdaQueryWrapper<ProField>().eq(ProField::getProjectId, id));

		Long newProId = snowFlakeIdGen.nextId();

		ProProject newProject = ConvertUtil.convertOfEntity(targetProject, ProProject.class);
		newProject.setId(newProId);
		newProject.setName(newName);
		newProject.setCreateTime(null);
		newProject.setUpdateTime(null);
		newProject.setCreateBy(userId);

		ProProjectUser projectUser = ProProjectUser.builder()
				.id(snowFlakeIdGen.nextId())
				.userId(userId)
				.projectId(newProId)
				.role(ProUserRoleEnum.PUR_0.getCode())
				.extend(proExtendService.getJsonForScope(targetProject.getConfigId(), ProExtendScopeEnum.PES_1)).build();

		List<ProDatasource>  newDatasourceList = new ArrayList<>();
		List<ProTable>  newTableList = new ArrayList<>();
		List<ProField>  newFieldList = new ArrayList<>();

		for (ProDatasource td:targetDatasourceList){
			ProDatasource nd = ConvertUtil.convertOfEntity(td, ProDatasource.class);
			Long newDatasourceId = snowFlakeIdGen.nextId();
			nd.setId(newDatasourceId);
			nd.setProjectId(newProId);
			nd.setCreateBy(userId);
			nd.setCreateTime(null);
			nd.setUpdateTime(null);
			newDatasourceList.add(nd);

			List<ProTable> filterTableList = targetTableList.stream().filter(t -> t.getSourceId().equals(td.getId())).collect(Collectors.toList());
			for (ProTable tt:filterTableList){
				ProTable nt = ConvertUtil.convertOfEntity(tt, ProTable.class);
				Long newTableId = snowFlakeIdGen.nextId();
				nt.setId(newTableId);
				nt.setProjectId(newProId);
				nt.setSourceId(newDatasourceId);
				nt.setCreateTime(null);
				nt.setUpdateTime(null);
				newTableList.add(nt);

				List<ProField> filterFieldList = targetFieldList.stream().filter(f -> f.getTableId().equals(tt.getId())).collect(Collectors.toList());
				for (ProField tf:filterFieldList){
					ProField nf = ConvertUtil.convertOfEntity(tf, ProField.class);
					nf.setId(snowFlakeIdGen.nextId());
					nf.setProjectId(newProId);
					nf.setSourceId(newDatasourceId);
					nf.setTableId(newTableId);
					newFieldList.add(nf);
				}
				targetFieldList.removeAll(filterFieldList);
			}
			targetTableList.removeAll(filterTableList);
		}

		transactionTemplate.execute(status -> {
			proProjectService.save(newProject);
			proProjectUserService.save(projectUser);
			if(ObjectUtil.isNotEmpty(newDatasourceList)){
				proDatasourceService.saveBatch(newDatasourceList);
			}
			if(ObjectUtil.isNotEmpty(newTableList)){
				proTableService.saveBatch(newTableList);
			}
			if(ObjectUtil.isNotEmpty(newFieldList)){
				proFieldService.saveBatch(newFieldList);
			}
			return null;
		});

		return newProId;
	}

	/**
	 * 根据主键删除
	 * @param id Long 项目-ID
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean remove(Long id) {
		transactionTemplate.execute(status -> {
			proProjectService.removeById(id);
			proProjectUserService.remove(new LambdaQueryWrapper<ProProjectUser>().eq(ProProjectUser::getProjectId, id));
			proDatasourceService.remove(new LambdaQueryWrapper<ProDatasource>().eq(ProDatasource::getProjectId, id));
			proTableService.remove(new LambdaQueryWrapper<ProTable>().eq(ProTable::getProjectId, id));
			proFieldService.remove(new LambdaQueryWrapper<ProField>().eq(ProField::getProjectId, id));
			return null;
		});
		return true;
	}

	/**
	 * 根据主键批量删除
	 * @param ids List<Long> 项目-ID集合
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Boolean removeBatch(List<Long> ids) {
		return proProjectService.removeByIds(ids);
	}

	/**
	 * 根据主键获取
	 * @param id Long 项目-ID
	 * @return ProProject
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public ProProject get(Long id) {
		return proProjectService.getById(id);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProProjectQueryBO 查询参数
	 * @return Page<ProProject>
	 * @author JustHuman
	 * @date 2023-04-26
	 */
	@Override
	public Page<ProProject> query(ProProjectQueryBO queryBO) {
		MPQueryWrapper<ProProject> wrapper = new MPQueryWrapper<>();
		wrapper.build(queryBO);
		long userId = UserUtil.getId();
		if(!Constants.ADMIN_ID.equals(userId)){
			List<Long> projectIds = proProjectUserService.list(new LambdaQueryWrapper<ProProjectUser>().eq(ProProjectUser::getUserId, userId))
					.stream().map(ProProjectUser::getProjectId).collect(Collectors.toList());
			if(ObjectUtil.isEmpty(projectIds)){
				return new Page<>();
			}
			wrapper.in(ProProject::getId, projectIds);
		}
		return proProjectService.page(queryBO.getPage(),wrapper);
	}

	/**
	 * 数据装配
	 * @param proProject 源对象
	 * @return ProProjectVO
	 */
	@Override
	public ProProjectVO assemble(ProProject proProject) {
		Long userId = UserUtil.getId();
		ProProjectVO vo = JSON.parseObject(JSON.toJSONString(proProject), ProProjectVO.class);

		// 获取所有者
		SysUser owner = sysUserService.getOne(new MPJLambdaWrapper<SysUser>()
				.selectAll(SysUser.class)
				.leftJoin(ProProjectUser.class, ProProjectUser::getUserId, SysUser::getId)
				.eq(ProProjectUser::getProjectId, proProject.getId())
				.eq(ProProjectUser::getRole, ProUserRoleEnum.PUR_0));


		vo.setOwnerId(owner.getId());
		vo.setOwner(owner.getNickName());
		ProConfig config = proConfigService.getById(vo.getConfigId());
		if(ObjectUtil.isNotNull(config)){
			vo.setConfigName(config.getName());
		}
		vo.setExtendList(JSON.parseArray(proProject.getExtend(), ProExtendValueBO.class));
		vo.setReadOnly(!userId.equals(vo.getOwnerId()));
		return vo;
	}

	/**
	 * 数据装配-集合
	 * @param list 源对象集合
	 * @return List<ProProjectVO>
	 */
	@Override
	public List<ProProjectVO> assembleList(List<ProProject> list) {
		if(ObjectUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		List<Long> projectIds = list.stream().map(ProProject::getId).collect(Collectors.toList());
		// 获取所有者
		List<Map<String, Object>> ownerList = proProjectUserService.listMaps(new MPJLambdaWrapper<ProProjectUser>()
				.selectAs(ProProjectUser::getProjectId,"projectId")
				.selectAs(ProProjectUser::getUserId, "userId")
				.selectAs(SysUser::getNickName, "nickName")
				.leftJoin(SysUser.class, SysUser::getId, ProProjectUser::getUserId)
				.in(ProProjectUser::getProjectId, projectIds)
				.eq(ProProjectUser::getRole, ProUserRoleEnum.PUR_0));

		Map<Long, ProProjectUserVO> ownerMap = new HashMap<>();
		for(Map<String, Object> obj:ownerList){
			ProProjectUserVO vo = BeanUtil.fillBeanWithMap(obj, new ProProjectUserVO(), false);
			ownerMap.put(vo.getProjectId(), vo);
		}
		// 获取配置信息
		Set<Long> configIds = list.stream().map(ProProject::getConfigId).collect(Collectors.toSet());
		Map<Long, String> configMap = proConfigService.listByIds(configIds).stream().collect(Collectors.toMap(ProConfig::getId, ProConfig::getName));
		// 组装
		List<ProProjectVO> voList = new ArrayList<>();
		Long userId = UserUtil.getId();
		for (ProProject proProject:list){
			ProProjectVO vo = JSON.parseObject(JSON.toJSONString(proProject), ProProjectVO.class);
			ProProjectUserVO owner = ownerMap.get(vo.getId());
			if(ObjectUtil.isNotNull(owner)){
				vo.setOwnerId(owner.getUserId());
				vo.setOwner(owner.getNickName());
			}
			vo.setConfigName(configMap.get(vo.getConfigId()));
			vo.setReadOnly(!userId.equals(vo.getOwnerId()));
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 数据装配-分页
	 * @param page 源分页对象
	 * @return Page<ProProjectVO>
	 */
	@Override
	public Page<ProProjectVO> assemblePage(Page<ProProject> page) {
		Page<ProProjectVO> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
		List<ProProjectVO> t = assembleList(page.getRecords());
		tPage.setRecords(t);
		return tPage;
	}

	/**
	 * 按配置ID导出项目
	 * @param projectIds
	 */
	@Override
	public void export(List<Long> projectIds){
		List<ProProject> projectList = proProjectService.listByIds(projectIds);
		String text = doExport(projectList);
		FileUtil.download(text.getBytes(), "project_export.pupa");
	}

	/**
	 * 按搜索条件导出项目
	 * @param queryBO
	 */
	@Override
	public void export(ProProjectQueryBO queryBO){
		queryBO.setPageNum(null);
		queryBO.setPageSize(null);
		Page<ProProject> projectPage = query(queryBO);
		String text = doExport(projectPage.getRecords());
		FileUtil.download(text.getBytes(), "project_export.pupa");
	}

	/**
	 * 生成导出数据
	 * @param projectList
	 */
	public String doExport(List<ProProject> projectList){
		if(ObjectUtil.isEmpty(projectList)){
			throw new ApiException(ExceptionConstants.NOT_FIND_POINT_DATA);
		}
		List<Long> projectIds = projectList.stream().map(ProProject::getId).collect(Collectors.toList());
		List<Long> configIds = projectList.stream().map(ProProject::getConfigId).collect(Collectors.toList());
		List<ProDatasource> datasourceList = proDatasourceService.list(new LambdaQueryWrapper<ProDatasource>().in(ProDatasource::getProjectId, projectIds));
		List<ProTable> tableList = proTableService.list(new LambdaQueryWrapper<ProTable>().in(ProTable::getProjectId, projectIds));
		List<ProField> fieldList = proFieldService.list(new LambdaQueryWrapper<ProField>().in(ProField::getProjectId, projectIds));
		List<ProConfig> configList = proConfigService.list(new LambdaQueryWrapper<ProConfig>().in(ProConfig::getId, configIds));

		List<ProTable> filterTableList;
		List<ProField> filterFieldList;
		// 项目
		List<EAIProject> eaiProjectList = new ArrayList<>();
		for(ProProject project:projectList){
			EAIProject eaiProject = ConvertUtil.convertOfEntity(project, EAIProject.class);
			// 数据源
			List<EAIDatasource> eaiDatasourceList = new ArrayList<>();
			for(ProDatasource datasource:datasourceList.stream().filter(d -> d.getProjectId().equals(project.getId())).collect(Collectors.toList())){
				EAIDatasource eaiDatasource = ConvertUtil.convertOfEntity(datasource, EAIDatasource.class);
				// 表
				List<EAITable> eaiTableList = new ArrayList<>();
				filterTableList = tableList.stream().filter(t -> t.getSourceId().equals(datasource.getId())).collect(Collectors.toList());
				for(ProTable table:filterTableList){
					EAITable eaiTable = ConvertUtil.convertOfEntity(table, EAITable.class);
					// 字段
					List<EAIField> eaiFieldList = new ArrayList<>();
					filterFieldList = fieldList.stream().filter(f -> f.getTableId().equals(table.getId())).collect(Collectors.toList());
					for(ProField field:filterFieldList){
						EAIField eaiField = ConvertUtil.convertOfEntity(field, EAIField.class);
						eaiFieldList.add(eaiField);
					}
					fieldList.removeAll(filterFieldList);
					eaiTable.setFieldList(eaiFieldList);
					eaiTableList.add(eaiTable);

				}
				tableList.removeAll(filterTableList);
				eaiDatasource.setTableList(eaiTableList);
				eaiDatasourceList.add(eaiDatasource);
			}
			List<ProConfig> filterConfigList = configList.stream().filter(f -> f.getId().equals(project.getConfigId())).collect(Collectors.toList());
			List<EAIConfig> eaiConfigList = bizProConfigService.doExport(filterConfigList);
			eaiProject.setEaiConfig(eaiConfigList.get(0));
			eaiProject.setDatasourceList(eaiDatasourceList);
			eaiProjectList.add(eaiProject);
		}
		return Base64.encode(JSON.toJSONString(eaiProjectList));
	}

	/**
	 * 导入配置
	 * @param file
	 * @return
	 */
	@Override
	public Integer importAll(MultipartFile file) {
		Long userId = UserUtil.getId();
		SysUser user = UserUtil.get();
		if(UserTypeEnum.VISITOR_2.equals(user.getType())){
			throw new ApiException(ExceptionConstants.CANT_OPERATE_OF_DEMO);
		}
		String importText = FileUtil.read(file);
		List<EAIProject> eaiProjectList = JSON.parseArray(Base64.decodeStr(importText),EAIProject.class);
		if(ObjectUtil.isEmpty(eaiProjectList)){
			throw new ApiException("文件数据不匹配");
		}
		// 组装数据
		List<ProConfig> configList = new ArrayList<>();
		List<ProMapper> mapperList = new ArrayList<>();
		List<ProExtend> extendList = new ArrayList<>();
		List<ProTemplate> templateList = new ArrayList<>();
		List<ProProject> projectList = new ArrayList<>();
		List<ProProjectUser> projectUserList = new ArrayList<>();
		List<ProDatasource> datasourceList = new ArrayList<>();
		List<ProTable> tableList = new ArrayList<>();
		List<ProField> fieldList = new ArrayList<>();

		Long projectId, sourceId, tableId;
		Map<Long, Long> configIdMap = new HashMap<>();
		// 项目
		for(EAIProject eaiProject: eaiProjectList) {
			ProProject proProject = ConvertUtil.convertOfEntity(eaiProject, ProProject.class);
			projectId = snowFlakeIdGen.nextId();
			proProject.setId(projectId);
			proProject.setCreateBy(userId);
			projectList.add(proProject);
			// 配置
			List<ProMapper> subMapperList = new ArrayList<>();
			List<ProExtend> subExtendList = new ArrayList<>();
			EAIConfig eaiConfig = eaiProject.getEaiConfig();
			Long eaiConfigId = eaiConfig.getId();
			if(configIdMap.get(eaiConfigId) == null){
				bizProConfigService.buildData(Collections.singletonList(eaiConfig), configList, subMapperList, subExtendList, templateList);
				proProject.setConfigId(eaiConfig.getId());
				mapperList.addAll(subMapperList);
				extendList.addAll(subExtendList);
				configIdMap.put(eaiConfigId, eaiConfig.getId());
			}else {
				proProject.setConfigId(configIdMap.get(eaiConfigId));
			}

			// 成员
			List<ProExtend> userExtendList = subExtendList.stream().filter(e -> ProExtendScopeEnum.PES_1.equals(e.getScope())).collect(Collectors.toList());
			ProProjectUser projectUser = ProProjectUser.builder()
					.id(snowFlakeIdGen.nextId())
					.userId(userId)
					.projectId(projectId)
					.role(ProUserRoleEnum.PUR_0.getCode())
					.extend(JSON.toJSONString(userExtendList)).build();
			projectUserList.add(projectUser);

			// 数据源
			for(EAIDatasource eaiDatasource: eaiProject.getDatasourceList()) {
				ProDatasource datasource = ConvertUtil.convertOfEntity(eaiDatasource, ProDatasource.class);
				sourceId = snowFlakeIdGen.nextId();
				datasource.setId(sourceId);
				datasource.setProjectId(projectId);
				datasource.setCreateBy(userId);
				datasourceList.add(datasource);
				// 表
				for(EAITable eaiTable: eaiDatasource.getTableList()) {
					ProTable table = ConvertUtil.convertOfEntity(eaiTable, ProTable.class);
					tableId = snowFlakeIdGen.nextId();
					table.setId(tableId);
					table.setSourceId(sourceId);
					table.setProjectId(projectId);
					tableList.add(table);
					// 字段
					for(EAIField eaiField: eaiTable.getFieldList()) {
						ProField field = ConvertUtil.convertOfEntity(eaiField, ProField.class);
						field.setId(snowFlakeIdGen.nextId());
						field.setTableId(tableId);
						field.setSourceId(sourceId);
						field.setProjectId(projectId);
						fieldList.add(field);
					}
				}
			}

		}
		transactionTemplate.execute(status -> {
			if(ObjectUtil.isNotEmpty(configList)){
				proConfigService.saveBatch(configList);
				if(ObjectUtil.isNotEmpty(mapperList)){
					proMapperService.saveBatch(mapperList);
				}
				if(ObjectUtil.isNotEmpty(extendList)){
					proExtendService.saveBatch(extendList);
				}
				if(ObjectUtil.isNotEmpty(templateList)){
					proTemplateService.saveBatch(templateList);
				}
			}
			if(ObjectUtil.isNotEmpty(projectList)){
				proProjectService.saveBatch(projectList);
				if(ObjectUtil.isNotEmpty(datasourceList)){
					proDatasourceService.saveBatch(datasourceList);
					if(ObjectUtil.isNotEmpty(projectUserList)){
						proProjectUserService.saveBatch(projectUserList);
					}
					if(ObjectUtil.isNotEmpty(tableList)){
						proTableService.saveBatch(tableList);
					}
					if(ObjectUtil.isNotEmpty(fieldList)){
						proFieldService.saveBatch(fieldList);
					}
				}
			}

			return null;
		});

		return eaiProjectList.size();
	}
}
