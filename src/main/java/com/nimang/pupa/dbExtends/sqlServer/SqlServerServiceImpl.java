package com.nimang.pupa.dbExtends.sqlServer;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nimang.pupa.base.entity.ProDatasource;
import com.nimang.pupa.base.entity.ProField;
import com.nimang.pupa.base.entity.ProTable;
import com.nimang.pupa.base.entity.SourceInfo;
import com.nimang.pupa.base.service.IDatasourceService;
import com.nimang.pupa.base.service.IProExtendService;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.LDTUtils;
import com.nimang.pupa.common.util.SnowFlakeIdGen;
import com.nimang.pupa.dbExtends.DataTool;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import com.nimang.pupa.dbExtends.IMetadataService;
import com.nimang.pupa.dbExtends.sqlServer.entity.SqlServerColumns;
import com.nimang.pupa.dbExtends.sqlServer.entity.SqlServerTables;
import com.nimang.pupa.dbExtends.sqlServer.mapper.SqlServerMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SqlServerServiceImpl implements IMetadataService {
    private final SnowFlakeIdGen snowFlakeIdGen;
    private final IDatasourceService datasourceService;
    private final IProExtendService proExtendService;
    @Override
    public DatasourceBrandEnum getBrand() {
        return DatasourceBrandEnum.PDB_SQL_SERVER;
    }

    @Override
    public String getUrl(ProDatasource datasource) {
        // jdbc:sqlserver://192.168.0.1:1433;DatabaseName=schema
        String url = "jdbc:sqlserver://" + datasource.getMainAddr() +
                ";DatabaseName=" + datasource.getSchema();
        return url;
    }


    @Override
    public SqlSessionFactory getSessionFactory(ProDatasource datasource) {
        SourceInfo sourceInfo = new SourceInfo(
                "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                getUrl(datasource),
                datasource.getAccount(),
                datasource.getPassword()
        );
        return datasourceService.link(sourceInfo);
    }

    @Override
    public List<ProTable> findTables(ProDatasource datasource, List<String> tableNames) {
        SqlSessionFactory sessionFactory = getSessionFactory(datasource);
        if(ObjectUtil.isNull(sessionFactory)){
            return new ArrayList<>();
        }
        SqlSession sqlSession = sessionFactory.openSession();
        SqlServerMapper tbMapper = sqlSession.getMapper(SqlServerMapper.class);

        List<SqlServerTables> tablesList = tbMapper.queryTable(tableNames);
        sqlSession.close();

        // 获取当前项目是扩展配置
        String tableExtent = proExtendService.getJsonForScopeWithProId(datasource.getProjectId(), ProExtendScopeEnum.PES_3);
        List<ProTable> proTableList = new ArrayList<>();
        for(SqlServerTables table:tablesList){
            ProTable proTable = new ProTable();
            proTable.setId(snowFlakeIdGen.nextId());
            proTable.setTableName(table.getTableName());
            proTable.setTableComment(table.getTableComment());
            proTable.setCreateTime(LDTUtils.convertDateToLDT(table.getCreateTime()));
            proTable.setProjectId(datasource.getProjectId());
            proTable.setSourceId(datasource.getId());
            proTable.setInfixName(proTable.getTableName());
            proTable.setExtend(tableExtent);
            proTable.setExistFlag(true);
            proTable.setTableSchema(datasource.getSchema());
            DataTool.setModule(proTable);
            // 去除前缀
            if(StrUtil.isNotBlank(datasource.getPrefix())){
                String[] prefixes = datasource.getPrefix().split(",");
                for(String prefix:prefixes){
                    if(proTable.getInfixName().startsWith(prefix)){
                        proTable.setInfixName(proTable.getInfixName().replace(prefix, ""));
                    }
                }
            }
            proTableList.add(proTable);
        }
        return proTableList;
    }

    @Override
    public List<ProField> findColumns(ProDatasource datasource, List<ProTable> proTableList) {
        SqlSessionFactory sessionFactory = getSessionFactory(datasource);
        if(sessionFactory == null){
            return new ArrayList<>();
        }
        List<String> tableNames = proTableList.stream().map(ProTable::getTableName).collect(Collectors.toList());
        Map<String, Long> tableMap = proTableList.stream().collect(Collectors.toMap(ProTable::getTableName, ProTable::getId));

        SqlSession sqlSession = sessionFactory.openSession();
        SqlServerMapper mapper = sqlSession.getMapper(SqlServerMapper.class);
        List<SqlServerColumns> columnsList = mapper.queryColumns(tableNames);
        sqlSession.close();

        // 获取当前项目是扩展配置
        String filedExtent = proExtendService.getJsonForScopeWithProId(datasource.getProjectId(), ProExtendScopeEnum.PES_4);

        List<ProField> proFieldList = new ArrayList<>();
        for(SqlServerColumns column:columnsList){
            ProField proField = ConvertUtil.convertOfEntity(column, ProField.class);
            proField.setId(snowFlakeIdGen.nextId());
            proField.setProjectId(datasource.getProjectId());
            proField.setSourceId(datasource.getId());
            proField.setTableId(tableMap.get(column.getTableName()));
            proField.setExtend(filedExtent);
            proField.setExistFlag(true);
            proField.setAttrName(StrUtil.toCamelCase(column.getColumnName().toLowerCase()));
            proField.setColumnCn(DataTool.getDesc(column.getColumnComment()));
            proField.setColumnNotes(DataTool.getNotes(column.getColumnComment()));
            proField.setEnumName(DataTool.getEnum(column.getColumnComment()));
            proField.setPrimary("PRI".equals(column.getColumnKey()));
            proField.setRequiredFlag("NO".equals(column.getIsNullable()));
            proField.setInsertFlag(!proField.getPrimary());
            proField.setViewFlag(true);
            proField.setQueryFlag(!proField.getPrimary());
            proField.setIdType(IdType.ASSIGN_ID.toString());
            proField.setUnsigned(false);
            setBounds(proField);
            proFieldList.add(proField);
        }
        return proFieldList;
    }

    @Override
    public void setBounds(ProField proField) {
        // 字符最大长度、数字精度均为空，不需要计算取值范围
        if(ObjectUtil.isNull(proField.getCharacterMaximumLength()) && ObjectUtil.isNull(proField.getNumericPrecision())){
            return;
        }
        if (ObjectUtil.isNotNull(proField.getCharacterMaximumLength())) {
            // 字符串类型
            proField.setBoundMax(proField.getCharacterMaximumLength().toString());
        }else {
            // 获取数值精度、刻度
            if(ObjectUtil.isNotNull(proField.getNumericPrecision())){
                int precision = proField.getNumericPrecision().intValue();
                int scale = ObjectUtil.isNotNull(proField.getNumericScale())?proField.getNumericScale().intValue():0;
                // 设置取值范围
                DataTool.setBoundsForNum(proField, precision, scale);
            }
        }
    }
}
