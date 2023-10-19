package com.nimang.pupa.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nimang.pupa.base.entity.ProDatasource;
import com.nimang.pupa.base.mapper.ProDatasourceMapper;
import com.nimang.pupa.base.service.IProDatasourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 数据源-数据服务接口实现
 * @author JustHuman
 * @date 2023-04-26
 */
@RequiredArgsConstructor
@Service
public class ProDatasourceServiceImpl extends ServiceImpl<ProDatasourceMapper, ProDatasource> implements IProDatasourceService {

}
