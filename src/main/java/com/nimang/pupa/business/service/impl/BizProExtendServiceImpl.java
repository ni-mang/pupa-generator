package com.nimang.pupa.business.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.nimang.pupa.base.entity.ProExtend;
import com.nimang.pupa.base.model.proExtend.*;
import com.nimang.pupa.base.service.IProExtendService;
import com.nimang.pupa.business.service.BizProExtendService;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;
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
 * 扩展-业务接口实现
 * @author JustHuman
 * @date 2023-04-21
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class BizProExtendServiceImpl implements BizProExtendService {

	private final SnowFlakeIdGen snowFlakeIdGen;
	private final IProExtendService proExtendService;

	/**
	 * 新增
	 * @param addBO ProExtendAddBO 新增数据
	 * @return Long ID
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Long add(ProExtendAddBO addBO) {
		long exist = proExtendService.count(new LambdaQueryWrapper<ProExtend>().eq(ProExtend::getConfigId, addBO.getConfigId()).eq(ProExtend::getKey, addBO.getKey()));
		if(exist > 0){
			throw new ApiException("已存在名为“" + addBO.getKey() + "”的扩展项");
		}
		ProExtend proExtend = ConvertUtil.convertOfEntity(addBO, ProExtend.class);
		Long id = snowFlakeIdGen.nextId();
		proExtend.setId(id);
		proExtend.setStatus(StatusEnum.STATUS_1.getCode());
		proExtendService.save(proExtend);
		return id;
	}

	/**
	 * 修改
	 * @param editBO ProExtendEditBO 修改数据
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Boolean edit(ProExtendEditBO editBO) {
		ProExtend proExtend = proExtendService.getById(editBO.getId());
		ConvertUtil.convertOfEntity(editBO, proExtend);
		return proExtendService.updateById(proExtend);
	}

	/**
	 * 状态变更
	 * @param changeBO StatusChangeBO 修改数据
	 * @return
	 */
	@Override
	public Boolean change(StatusChangeBO changeBO) {
		ProExtend proExtend = ConvertUtil.convertOfEntity(changeBO, ProExtend.class);
		return proExtendService.updateById(proExtend);
	}

	/**
	 * 根据主键删除
	 * @param id Long 扩展-ID
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Boolean remove(Long id) {
		return proExtendService.removeById(id);
	}

	/**
	 * 根据主键批量删除
	 * @param ids List<Long> 扩展-ID集合
	 * @return Boolean
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Boolean removeBatch(List<Long> ids) {
		return proExtendService.removeByIds(ids);
	}

	/**
	 * 根据主键获取
	 * @param id Long 扩展-ID
	 * @return ProExtend
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public ProExtend get(Long id) {
		return proExtendService.getById(id);
	}

	/**
	 * 条件查询（可分页）
	 * @param queryBO ProExtendQueryBO 查询参数
	 * @return Page<ProExtend>
	 * @author JustHuman
	 * @date 2023-04-21
	 */
	@Override
	public Page<ProExtend> query(ProExtendQueryBO queryBO) {
		MPQueryWrapper<ProExtend> wrapper = new MPQueryWrapper<>();
		wrapper.build(queryBO);
		return proExtendService.page(queryBO.getPage(),wrapper);
	}

	/**
	 * 展示所有扩展信息
	 * @param configId 配置ID
	 * @return List<ProExtendShowVO>
	 */
	@Override
	public List<ProExtendShowVO> showAllExtend(Long configId) {
		List<ProExtendShowVO> showVOS = ProExtendShowVO.getDefaultExtend();
		List<ProExtend> extendList = proExtendService.listForScope(configId, null);
		showVOS.addAll(ConvertUtil.convertOfAll(extendList, ProExtendShowVO.class));
		return showVOS;
	}

	/**
	 * 数据装配
	 * @param proExtend 源对象
	 * @return ProExtendVO
	 */
	@Override
	public ProExtendVO assemble(ProExtend proExtend) {
		ProExtendVO vo = ConvertUtil.convertOfEntity(proExtend, ProExtendVO.class);
		return vo;
	}

	/**
	 * 数据装配-集合
	 * @param list 源对象集合
	 * @return List<ProExtendVO>
	 */
	@Override
	public List<ProExtendVO> assembleList(List<ProExtend> list) {
		if(ObjectUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		List<ProExtendVO> voList = new ArrayList<>();
		for (ProExtend proExtend:list){
			ProExtendVO vo = ConvertUtil.convertOfEntity(proExtend, ProExtendVO.class);
			vo.setScopeDesc(ProExtendScopeEnum.getMsgByCode(vo.getScope()));
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 数据装配-分页
	 * @param page 源分页对象
	 * @return Page<ProExtendVO>
	 */
	@Override
	public Page<ProExtendVO> assemblePage(Page<ProExtend> page) {
		Page<ProExtendVO> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
		List<ProExtendVO> t = assembleList(page.getRecords());
		tPage.setRecords(t);
		return tPage;
	}

}
