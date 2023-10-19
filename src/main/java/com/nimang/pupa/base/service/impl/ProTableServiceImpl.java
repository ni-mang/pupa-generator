package com.nimang.pupa.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nimang.pupa.base.entity.ProTable;
import com.nimang.pupa.base.mapper.ProTableMapper;
import com.nimang.pupa.base.service.IProTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 表单-数据服务接口实现
 * @author JustHuman
 * @date 2023-04-26
 */
@RequiredArgsConstructor
@Service
public class ProTableServiceImpl extends ServiceImpl<ProTableMapper, ProTable> implements IProTableService {

}
