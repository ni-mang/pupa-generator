package com.nimang.pupa.dbExtends.postgreSql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nimang.pupa.dbExtends.postgreSql.entity.PostgreColumns;
import com.nimang.pupa.dbExtends.postgreSql.entity.PostgreTables;
import com.nimang.pupa.dbExtends.sqlServer.entity.SqlServerColumns;
import com.nimang.pupa.dbExtends.sqlServer.entity.SqlServerTables;

import java.util.List;


/**
 * @author JustHuman
 * @date 2023-11-06
 */
public interface PostgreMapper extends BaseMapper<PostgreTables> {
    List<PostgreTables> queryTable(List<String> tableNames);
    List<PostgreColumns> queryColumns(List<String> tableNames);
}