package com.nimang.pupa.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nimang.pupa.base.entity.ProProjectUser;
import com.nimang.pupa.base.mapper.ProProjectUserMapper;
import com.nimang.pupa.base.service.IProProjectUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 项目成员-数据服务接口实现
 * @author LinLaichun
 * @date 2023-04-26
 */
@RequiredArgsConstructor
@Service
public class ProProjectUserServiceImpl extends ServiceImpl<ProProjectUserMapper, ProProjectUser> implements IProProjectUserService {

}
