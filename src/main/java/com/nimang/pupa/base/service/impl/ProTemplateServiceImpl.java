package com.nimang.pupa.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nimang.pupa.base.entity.ProTemplate;
import com.nimang.pupa.base.mapper.ProTemplateMapper;
import com.nimang.pupa.base.service.IProTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 模板-数据服务接口实现
 * @author LinLaichun
 * @date 2023-04-21
 */
@RequiredArgsConstructor
@Service
public class ProTemplateServiceImpl extends ServiceImpl<ProTemplateMapper, ProTemplate> implements IProTemplateService {

}
