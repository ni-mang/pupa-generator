package com.nimang.pupa.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nimang.pupa.base.entity.ProColumnType;
import com.nimang.pupa.base.mapper.ProColumnTypeMapper;
import com.nimang.pupa.base.service.IProColumnTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 列类型-数据服务接口实现
 * @author LinLaichun
 * @date 2023-09-08
 */
@RequiredArgsConstructor
@Service
public class ProColumnTypeServiceImpl extends ServiceImpl<ProColumnTypeMapper, ProColumnType> implements IProColumnTypeService {

}
