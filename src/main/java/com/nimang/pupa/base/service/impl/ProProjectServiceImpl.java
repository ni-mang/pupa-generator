package com.nimang.pupa.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nimang.pupa.base.entity.ProProject;
import com.nimang.pupa.base.mapper.ProProjectMapper;
import com.nimang.pupa.base.service.IProProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 项目-数据服务接口实现
 * @author JustHuman
 * @date 2023-04-26
 */
@RequiredArgsConstructor
@Service
public class ProProjectServiceImpl extends ServiceImpl<ProProjectMapper, ProProject> implements IProProjectService {

}
