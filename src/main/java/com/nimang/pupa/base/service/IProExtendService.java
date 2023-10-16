package com.nimang.pupa.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nimang.pupa.base.entity.ProExtend;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;

import java.util.List;

/**
 * 扩展-数据服务接口
 * @author JustHuman
 * @date 2023-04-21
 */
public interface IProExtendService extends IService<ProExtend>{
    /**
     * 按作用域获取扩展配置集
     * @param configId 配置ID
     * @param scopeEnum 作用域
     * @return
     */
    List<ProExtend> listForScope(Long configId, ProExtendScopeEnum scopeEnum);

    /**
     * 按作用域获取扩展配置JSON字符串
     * @param configId 配置ID
     * @param scopeEnum 作用域
     * @return
     */
    String getJsonForScope(Long configId, ProExtendScopeEnum scopeEnum);

    /**
     * 按作用域获取扩展配置集
     * @param projectId 项目ID
     * @param scopeEnum 作用域
     * @return
     */
    List<ProExtend> listForScopeWithProId(Long projectId, ProExtendScopeEnum scopeEnum);

    /**
     * 按作用域获取扩展配置JSON字符串
     * @param projectId 项目ID
     * @param scopeEnum 作用域
     * @return
     */
    String getJsonForScopeWithProId(Long projectId, ProExtendScopeEnum scopeEnum);
}
