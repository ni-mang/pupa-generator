package com.nimang.pupa.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nimang.pupa.base.entity.SysUser;
import com.nimang.pupa.base.mapper.SysUserMapper;
import com.nimang.pupa.base.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户信息-数据服务接口实现
 * @date 2023-04-12
 */
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
