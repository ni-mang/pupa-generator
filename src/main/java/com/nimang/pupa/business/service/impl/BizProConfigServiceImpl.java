package com.nimang.pupa.business.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.nimang.pupa.base.entity.*;
import com.nimang.pupa.base.model.exportAndImport.EAIConfig;
import com.nimang.pupa.base.model.exportAndImport.EAIExtend;
import com.nimang.pupa.base.model.exportAndImport.EAIMapper;
import com.nimang.pupa.base.model.exportAndImport.EAITemplate;
import com.nimang.pupa.base.model.gen.*;
import com.nimang.pupa.base.service.*;
import com.nimang.pupa.business.service.BizProConfigService;
import com.nimang.pupa.common.constants.ExceptionConstants;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.enums.proConfig.ProConfigTypeEnum;
import com.nimang.pupa.common.pojo.StatusChangeBO;
import com.nimang.pupa.common.tool.mp.query.MPQueryWrapper;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.FileUtil;
import com.nimang.pupa.common.util.SnowFlakeIdGen;
import com.nimang.pupa.common.util.UserUtil;
import com.nimang.pupa.base.model.proConfig.ProConfigAddBO;
import com.nimang.pupa.base.model.proConfig.ProConfigEditBO;
import com.nimang.pupa.base.model.proConfig.ProConfigQueryBO;
import com.nimang.pupa.base.model.proConfig.ProConfigVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 配置-业务接口实现
 * @author JustHuman
 * @date 2023-04-21
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class BizProConfigServiceImpl implements BizProConfigService {

	private final SnowFlakeIdGen snowFlakeIdGen;
	private final TransactionTemplate transactionTemplate;
	private final IProConfigService proConfigService;
	private final IProMapperService proMapperService;
	private final IProExtendService proExtendService;
	private final IProTemplateService proTemplateService;
	private final IProProjectService proProjectService;
	private final ISysUserService sysUserService;

	/**
	 * 新增
	 * @param addBO ProConfigAddBO 新增数据
	 * @return Long ID
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Long add(ProConfigAddBO addBO) {
		SysUser user = UserUtil.get();
		long exist = proConfigService.count(new LambdaQueryWrapper<ProConfig>().eq(ProConfig::getUserId, user.getId()).eq(ProConfig::getName, addBO.getName()));
		if(exist > 0){
			throw new ApiException("已存在名为“" + addBO.getName() + "”的配置");
		}
		ProConfig proConfig = ConvertUtil.convertOfEntity(addBO, ProConfig.class);
		Long id = snowFlakeIdGen.nextId();
		proConfig.setId(id);
		proConfig.setUserId(user.getId());
		proConfig.setStatus(StatusEnum.STATUS_1.getCode());
		proConfigService.save(proConfig);
		return id;
	}

	/**
	 * 修改
	 * @param editBO ProConfigEditBO 修改数据
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Boolean edit(ProConfigEditBO editBO) {
		ProConfig proConfig = proConfigService.getById(editBO.getId());
		ConvertUtil.convertOfEntity(editBO, proConfig);
		return proConfigService.updateById(proConfig);
	}

	/**
	 * 状态变更
	 * @param changeBO StatusChangeBO 修改数据
	 * @return
	 */
	@Override
	public Boolean change(StatusChangeBO changeBO) {
		ProConfig proConfig = ConvertUtil.convertOfEntity(changeBO, ProConfig.class);
		return proConfigService.updateById(proConfig);
	}

	/**
	 * 克隆
	 * @param configId Long 配置ID
	 * @return Long ID
	 */
	@Override
	public Long clone(Long configId) {
		SysUser user = UserUtil.get();
		ProConfig targetConfig = proConfigService.getById(configId);
		if(ObjectUtil.isNull(targetConfig)){
			throw new ApiException(ExceptionConstants.NOT_FIND_POINT_DATA);
		}
		String newName = targetConfig.getName() + "_copy";
		long count = proConfigService.count(new LambdaQueryWrapper<ProConfig>().eq(ProConfig::getUserId, user.getId()).eq(ProConfig::getName, newName));
		if(count > 0){
			throw new ApiException(MessageFormat.format(ExceptionConstants.EXISTED_FOR_COPY, newName));
		}
		List<ProMapper> targetMapperList = proMapperService.list(new LambdaQueryWrapper<ProMapper>().eq(ProMapper::getConfigId, configId));
		List<ProExtend> targetExtendList = proExtendService.list(new LambdaQueryWrapper<ProExtend>().eq(ProExtend::getConfigId, configId));
		List<ProTemplate> targetTemplateList = proTemplateService.list(new LambdaQueryWrapper<ProTemplate>().eq(ProTemplate::getConfigId, configId));
		// 拷贝配置
		long newConfigId = snowFlakeIdGen.nextId();
		ProConfig newConfig = ConvertUtil.convertOfEntity(targetConfig, ProConfig.class);
		newConfig.setId(newConfigId);
		newConfig.setUserId(user.getId());
		newConfig.setName(newName);
		newConfig.setGenTimes(0);
		newConfig.setStatus(StatusEnum.STATUS_1.getCode());
		newConfig.setType(ProConfigTypeEnum.PCT_1.getCode());

		// 拷贝映射关系
		List<ProMapper> newMapperList = new ArrayList<>();
		for (ProMapper targetMapper: targetMapperList){
			ProMapper newMapper = ConvertUtil.convertOfEntity(targetMapper, ProMapper.class);
			newMapper.setId(snowFlakeIdGen.nextId());
			newMapper.setConfigId(newConfigId);
			newMapperList.add(newMapper);
		}

		// 拷贝扩展
		List<ProExtend> newExtendList = new ArrayList<>();
		for (ProExtend targetExtend: targetExtendList){
			ProExtend newExtend = ConvertUtil.convertOfEntity(targetExtend, ProExtend.class);
			newExtend.setId(snowFlakeIdGen.nextId());
			newExtend.setConfigId(newConfigId);
			newExtend.setStatus(StatusEnum.STATUS_1.getCode());
			newExtendList.add(newExtend);
		}
		// 拷贝模板
		List<ProTemplate> newTemplateList = new ArrayList<>();
		for (ProTemplate targetTemplate: targetTemplateList){
			ProTemplate newTemplate = ConvertUtil.convertOfEntity(targetTemplate, ProTemplate.class);
			newTemplate.setId(snowFlakeIdGen.nextId());
			newTemplate.setConfigId(newConfigId);
			newTemplate.setStatus(StatusEnum.STATUS_1.getCode());
			newTemplateList.add(newTemplate);
		}
		transactionTemplate.execute(status -> {
			proConfigService.save(newConfig);
			proMapperService.saveBatch(newMapperList);
			proExtendService.saveBatch(newExtendList);
			proTemplateService.saveBatch(newTemplateList);
			return null;
		});
		return newConfigId;
	}

	/**
	 * 根据主键删除
	 * @param id Long 配置-ID
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean remove(Long id) {
		transactionTemplate.execute(status -> {
			proExtendService.remove(new LambdaQueryWrapper<ProExtend>().eq(ProExtend::getConfigId, id));
			proTemplateService.remove(new LambdaQueryWrapper<ProTemplate>().eq(ProTemplate::getConfigId, id));
			proMapperService.remove(new LambdaQueryWrapper<ProMapper>().eq(ProMapper::getConfigId, id));
			proConfigService.removeById(id);
			return null;
		});
		return true;
	}

	/**
	 * 根据主键获取
	 * @param id Long 配置-ID
	 * @return ProConfig
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public ProConfig get(Long id) {
		ProConfig proConfig = proConfigService.getById(id);
		if(ObjectUtil.isNull(proConfig)){
			throw new ApiException(ExceptionConstants.NOT_FIND_POINT_DATA);
		}
		return proConfig;
	}

	/**
	 * 获取参数说明
	 * @param id Long 配置-ID
	 * @return String
	 */
	@Override
	public List<GenDataInfo> showConfigInfo(Long id) {
		List<GenDataInfo> infoList = new ArrayList<>();
		List<ProExtend> extendList = proExtendService.listForScope(id, null);
		GenCommon.showInfo(infoList);
		GenProject.showInfo(infoList,extendList);
		GenTable.showInfo(infoList,extendList);
		GenField.showInfo(infoList,extendList);
		return infoList;
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProConfigQueryBO 查询参数
	 * @return Page<ProConfig>
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Page<ProConfig> query(ProConfigQueryBO queryBO) {
		MPQueryWrapper<ProConfig> wrapper = new MPQueryWrapper<>();
		wrapper.build(queryBO);
		Long userId = UserUtil.getId();
		wrapper.and(w -> w.eq(ProConfig::getUserId, userId).or().eq(ProConfig::getType, ProConfigTypeEnum.PCT_0.getCode()));
		return proConfigService.page(queryBO.getPage(),wrapper);
	}

	/**
	 * 下拉选择
	 * @return List<ProConfig>
	 * @author JustHuman
	 * @date 2023-04-24
	 */
	public List<ProConfig> listForSelect() {
		MPQueryWrapper<ProConfig> wrapper = new MPQueryWrapper<>();
		wrapper.eq(ProConfig::getStatus, StatusEnum.STATUS_1.getCode());
		Long userId = UserUtil.getId();
		wrapper.and(w -> w.eq(ProConfig::getUserId, userId).or().eq(ProConfig::getType, ProConfigTypeEnum.PCT_0.getCode()));
		wrapper.orderBy(true, true, ProConfig::getType);
		return proConfigService.list(wrapper);
	}



	/**
	 * 数据装配
	 * @param proConfig 源对象
	 * @return ProConfigVO
	 */
	@Override
	public ProConfigVO assemble(ProConfig proConfig) {
		Long userId = UserUtil.getId();
		ProConfigVO vo = ConvertUtil.convertOfEntity(proConfig, ProConfigVO.class);
		vo.setReadOnly(!userId.equals(vo.getUserId()));
		long useNum = proProjectService.count(new LambdaQueryWrapper<ProProject>().eq(ProProject::getConfigId, proConfig.getId()));
		vo.setUseNum((int) useNum);
		return vo;
	}

	/**
	 * 数据装配-集合
	 * @param list 源对象集合
	 * @return List<ProConfigVO>
	 */
	@Override
	public List<ProConfigVO> assembleList(List<ProConfig> list) {
		if(ObjectUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		// 统计被引用次数
		List<Long> configIds = list.stream().map(ProConfig::getId).collect(Collectors.toList());
		List<Map<String, Object>> countList = proProjectService.listMaps(new QueryWrapper<ProProject>()
				.select("config_id as configId,count(*) as count")
				.groupBy("config_id")
				.in("config_id", configIds));
		Map<Long, Integer> countMap = countList.stream().collect(Collectors.toMap(c -> Long.parseLong(c.get("configId").toString()), c -> Integer.parseInt(c.get("count").toString())));

		List<Long> userIds = list.stream().map(ProConfig::getUserId).collect(Collectors.toList());
		Map<Long, String> userMap = sysUserService.listByIds(userIds).stream().collect(Collectors.toMap(SysUser::getId, SysUser::getNickName));

		List<ProConfigVO> voList = new ArrayList<>();
		Long userId = UserUtil.getId();
		for (ProConfig proConfig:list){
			ProConfigVO vo = ConvertUtil.convertOfEntity(proConfig, ProConfigVO.class);
			vo.setReadOnly(!userId.equals(vo.getUserId()));
			Integer count = countMap.get(proConfig.getId());
			int useNum = 0;
			if(ObjectUtil.isNotNull(count)){
				useNum = count;
			}
			vo.setUseNum(useNum);
			vo.setUserName(userMap.get(proConfig.getUserId()));
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 数据装配-分页
	 * @param page 源分页对象
	 * @return Page<ProConfigVO>
	 */
	@Override
	public Page<ProConfigVO> assemblePage(Page<ProConfig> page) {
		Page<ProConfigVO> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
		List<ProConfigVO> t = assembleList(page.getRecords());
		tPage.setRecords(t);
		return tPage;
	}

	/**
	 * 按配置ID导出相应配置
	 * @param configIds
	 */
	@Override
	public void export(List<Long> configIds){
		List<ProConfig> configList = proConfigService.listByIds(configIds);
		List<EAIConfig> eaiConfigList = doExport(configList);
		FileUtil.download(Base64.encode(JSON.toJSONString(eaiConfigList)).getBytes(), "config_export.pupa");
	}

	/**
	 * 按搜索条件导出配置
	 * @param queryBO
	 */
	@Override
	public void export(ProConfigQueryBO queryBO){
		queryBO.setPageNum(null);
		queryBO.setPageSize(null);
		Page<ProConfig> configPage = query(queryBO);
		List<EAIConfig> eaiConfigList = doExport(configPage.getRecords());
		FileUtil.download(Base64.encode(JSON.toJSONString(eaiConfigList)).getBytes(), "config_export.pupa");
	}

	/**
	 * 生成导出数据
	 * @param configList
	 */
	@Override
	public List<EAIConfig> doExport(List<ProConfig> configList){
		if(ObjectUtil.isEmpty(configList)){
			throw new ApiException(ExceptionConstants.NOT_FIND_POINT_DATA);
		}
		List<Long> configIds = configList.stream().map(ProConfig::getId).collect(Collectors.toList());
		List<ProMapper> mapperList = proMapperService.list(new LambdaQueryWrapper<ProMapper>().in(ProMapper::getConfigId, configIds));
		List<ProExtend> extendList = proExtendService.list(new LambdaQueryWrapper<ProExtend>().in(ProExtend::getConfigId, configIds));
		List<ProTemplate> templateList = proTemplateService.list(new LambdaQueryWrapper<ProTemplate>().in(ProTemplate::getConfigId, configIds));

		List<EAIConfig> eaiConfigList = new ArrayList<>();
		for(ProConfig config:configList){
			EAIConfig eaiConfig = ConvertUtil.convertOfEntity(config, EAIConfig.class);
			eaiConfig.getMapperList().addAll(ConvertUtil.convertOfAll(mapperList.stream().filter(e -> e.getConfigId().equals(config.getId())).collect(Collectors.toList()), EAIMapper.class));
			eaiConfig.getExtendList().addAll(ConvertUtil.convertOfAll(extendList.stream().filter(e -> e.getConfigId().equals(config.getId())).collect(Collectors.toList()), EAIExtend.class));
			eaiConfig.getTemplateList().addAll(ConvertUtil.convertOfAll(templateList.stream().filter(t -> t.getConfigId().equals(config.getId())).collect(Collectors.toList()), EAITemplate.class));
			eaiConfigList.add(eaiConfig);
		}
		return eaiConfigList;
	}

	/**
	 * 导入文件
	 * @param file
	 * @return
	 */
	@Override
	public Integer importAll(MultipartFile file) {

		String importText = FileUtil.read(file);
		List<EAIConfig> eaiConfigList = JSON.parseArray(Base64.decodeStr(importText),EAIConfig.class);
		if(ObjectUtil.isEmpty(eaiConfigList)){
			throw new ApiException("文件数据不匹配");
		}

		List<ProConfig> configList = new ArrayList<>();
		List<ProMapper> mapperList = new ArrayList<>();
		List<ProExtend> extendList = new ArrayList<>();
		List<ProTemplate> templateList = new ArrayList<>();

		buildData(eaiConfigList, configList, mapperList, extendList, templateList);

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
			return null;
		});
		return eaiConfigList.size();
	}

	/**
	 * 组装数据
	 * @param eaiConfigList
	 * @param mapperList
	 * @param configList
	 * @param extendList
	 * @param templateList
	 */
	@Override
	public void buildData(List<EAIConfig> eaiConfigList, List<ProConfig> configList, List<ProMapper> mapperList, List<ProExtend> extendList, List<ProTemplate> templateList) {
		Long userId = UserUtil.getId();

		for(EAIConfig eaiConfig: eaiConfigList){
			ProConfig proConfig = ConvertUtil.convertOfEntity(eaiConfig, ProConfig.class);
			Long configId = snowFlakeIdGen.nextId();
			eaiConfig.setId(configId);
			proConfig.setId(configId);
			proConfig.setUserId(userId);
			configList.add(proConfig);

			for(EAIMapper eaiMapper:eaiConfig.getMapperList()){
				ProMapper proMapper = ConvertUtil.convertOfEntity(eaiMapper, ProMapper.class);
				proMapper.setId(snowFlakeIdGen.nextId());
				proMapper.setConfigId(configId);
				mapperList.add(proMapper);
			}
			for(EAIExtend eaiExtend:eaiConfig.getExtendList()){
				ProExtend proExtend = ConvertUtil.convertOfEntity(eaiExtend, ProExtend.class);
				proExtend.setId(snowFlakeIdGen.nextId());
				proExtend.setConfigId(configId);
				extendList.add(proExtend);
			}
			for(EAITemplate eaiTemplate:eaiConfig.getTemplateList()){
				ProTemplate proTemplate = ConvertUtil.convertOfEntity(eaiTemplate, ProTemplate.class);
				proTemplate.setId(snowFlakeIdGen.nextId());
				proTemplate.setConfigId(configId);
				templateList.add(proTemplate);
			}
		}

	}
}
