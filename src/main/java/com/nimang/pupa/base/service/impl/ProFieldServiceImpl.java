package com.nimang.pupa.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nimang.pupa.base.entity.ProField;
import com.nimang.pupa.base.mapper.ProFieldMapper;
import com.nimang.pupa.base.service.IProFieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 表字段-数据服务接口实现
 * @author JustHuman
 * @date 2023-04-26
 */
@RequiredArgsConstructor
@Service
public class ProFieldServiceImpl extends ServiceImpl<ProFieldMapper, ProField> implements IProFieldService {

}
