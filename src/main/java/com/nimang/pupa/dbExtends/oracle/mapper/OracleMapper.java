package com.nimang.pupa.dbExtends.oracle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nimang.pupa.dbExtends.oracle.entity.OracleColumns;
import com.nimang.pupa.dbExtends.oracle.entity.OracleTables;
import com.nimang.pupa.dbExtends.sqlServer.entity.SqlServerColumns;
import com.nimang.pupa.dbExtends.sqlServer.entity.SqlServerTables;

import java.util.List;


/**
 * @author JustHuman
 * @date 2023-10-23
 */
public interface OracleMapper extends BaseMapper<OracleTables> {
    List<OracleTables> queryTable(List<String> tableNames);
    List<OracleColumns> queryColumns(List<String> tableNames);
}