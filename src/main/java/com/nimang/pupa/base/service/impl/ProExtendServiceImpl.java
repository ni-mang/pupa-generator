package com.nimang.pupa.base.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nimang.pupa.base.entity.ProExtend;
import com.nimang.pupa.base.entity.ProProject;
import com.nimang.pupa.base.mapper.ProExtendMapper;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import com.nimang.pupa.base.service.IProExtendService;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;
import com.nimang.pupa.common.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 扩展-数据服务接口实现
 * @author LinLaichun
 * @date 2023-04-21
 */
@RequiredArgsConstructor
@Service
public class ProExtendServiceImpl extends ServiceImpl<ProExtendMapper, ProExtend> implements IProExtendService {

    @Override
    public List<ProExtend> listForScope(Long configId, ProExtendScopeEnum scopeEnum) {
        LambdaQueryWrapper<ProExtend> wrapper = new LambdaQueryWrapper<ProExtend>()
                .eq(ProExtend::getConfigId, configId)
                .eq(ProExtend::getStatus, StatusEnum.STATUS_1.getCode());
        if(ObjectUtil.isNotNull(scopeEnum)){
            wrapper.eq(ProExtend::getScope, scopeEnum.getCode());
        }
        return this.list(wrapper);
    }

    @Override
    public String getJsonForScope(Long configId, ProExtendScopeEnum scopeEnum) {
        List<ProExtend> extendList = listForScope(configId, scopeEnum);
        if(ObjectUtil.isEmpty(extendList)){
            return null;
        }
        List<ProExtendValueBO> evList = ConvertUtil.convertOfAll(extendList, ProExtendValueBO.class);
        return JSON.toJSONString(evList);
    }

    @Override
    public List<ProExtend> listForScopeWithProId(Long projectId, ProExtendScopeEnum scopeEnum) {
        MPJLambdaWrapper<ProExtend> wrapper = new MPJLambdaWrapper<ProExtend>()
                .selectAll(ProExtend.class)
                .leftJoin(ProProject.class, ProProject::getConfigId, ProExtend::getConfigId)
                .eq(ProProject::getId, projectId)
                .eq(ProExtend::getStatus, StatusEnum.STATUS_1);
        if(ObjectUtil.isNotNull(scopeEnum)){
            wrapper.eq(ProExtend::getScope, scopeEnum.getCode());
        }
        return this.list(wrapper);
    }

    @Override
    public String getJsonForScopeWithProId(Long projectId, ProExtendScopeEnum scopeEnum) {
        List<ProExtend> extendList = listForScopeWithProId(projectId, scopeEnum);
        if(ObjectUtil.isEmpty(extendList)){
            return null;
        }
        List<ProExtendValueBO> evList = ConvertUtil.convertOfAll(extendList, ProExtendValueBO.class);
        return JSON.toJSONString(evList);
    }
}
