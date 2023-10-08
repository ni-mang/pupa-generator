package com.nimang.pupa.base.service;

import com.nimang.pupa.base.entity.SourceInfo;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 数据源操作接口
 * @author LinLaichun
 * @date 2023-04-27
 */
public interface IDatasourceService {

    /**
     * 链接数据库
     * @param sourceInfo SourceInfo 数据库连接信息
     * @return SqlSessionFactory
     */
    SqlSessionFactory link(SourceInfo sourceInfo);
}
