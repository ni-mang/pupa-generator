package com.nimang.pupa.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nimang.pupa.base.entity.ProMapper;
import com.nimang.pupa.base.mapper.ProMapperMapper;
import com.nimang.pupa.base.service.IProMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 数据映射-数据服务接口实现
 * @author LinLaichun
 * @date 2023-08-09
 */
@RequiredArgsConstructor
@Service
public class ProMapperServiceImpl extends ServiceImpl<ProMapperMapper, ProMapper> implements IProMapperService {

}