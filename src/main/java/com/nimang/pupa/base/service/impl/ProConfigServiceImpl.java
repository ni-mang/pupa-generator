package com.nimang.pupa.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nimang.pupa.base.entity.ProConfig;
import com.nimang.pupa.base.mapper.ProConfigMapper;
import com.nimang.pupa.base.service.IProConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 配置-数据服务接口实现
 * @author JustHuman
 * @date 2023-04-21
 */
@RequiredArgsConstructor
@Service
public class ProConfigServiceImpl extends ServiceImpl<ProConfigMapper, ProConfig> implements IProConfigService {

}
