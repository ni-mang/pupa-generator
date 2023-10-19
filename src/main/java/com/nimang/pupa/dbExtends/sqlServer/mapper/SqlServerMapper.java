package com.nimang.pupa.dbExtends.sqlServer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nimang.pupa.dbExtends.sqlServer.entity.SqlServerColumns;
import com.nimang.pupa.dbExtends.sqlServer.entity.SqlServerTables;

import java.util.List;


/**
 * @author JustHuman
 * @date 2023-10-16
 */
public interface SqlServerMapper extends BaseMapper<SqlServerTables> {
    List<SqlServerTables> queryTable(List<String> tableNames);
    List<SqlServerColumns> queryColumns(List<String> tableNames);
}