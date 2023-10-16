package com.nimang.pupa.dbExtends.mysql;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nimang.pupa.base.entity.ProDatasource;
import com.nimang.pupa.base.entity.ProTable;
import com.nimang.pupa.base.service.IDatasourceService;
import com.nimang.pupa.dbExtends.DBConstants;
import com.nimang.pupa.dbExtends.DataTool;
import com.nimang.pupa.dbExtends.mysql.entity.MysqlColumns;
import com.nimang.pupa.dbExtends.mysql.entity.MysqlTables;
import com.nimang.pupa.dbExtends.mysql.mapper.MysqlColumnsMapper;
import com.nimang.pupa.base.entity.*;
import com.nimang.pupa.dbExtends.mysql.mapper.MysqlTablesMapper;
import com.nimang.pupa.dbExtends.IMetadataService;
import com.nimang.pupa.base.service.IProExtendService;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.SnowFlakeIdGen;
import com.nimang.pupa.base.entity.ProField;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MysqlServiceImpl implements IMetadataService {
    private final SnowFlakeIdGen snowFlakeIdGen;
    private final IDatasourceService datasourceService;
    private final IProExtendService proExtendService;

    @Override
    public DatasourceBrandEnum getBrand() {
        return DatasourceBrandEnum.PDB_MYSQL;
    }

    @Override
    public String getUrl(ProDatasource datasource) {
        // jdbc:mysql://localhost:3306/scheme?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
        StringBuilder url = new StringBuilder("jdbc:mysql://");
        url.append(datasource.getMainAddr()).append("/").append(datasource.getSchema());
        if (StrUtil.isNotBlank(datasource.getUrlSuffix())){
            url.append("?").append(datasource.getUrlSuffix());
        }
        return url.toString();
    }

    /**
     * 获取数据库连接SessionFactory
     * @param datasource ProDatasource 数据源配置
     * @return SessionFactory
     */
    @Override
    public SqlSessionFactory getSessionFactory(ProDatasource datasource) {
        ProDatasource datasourceCopy = ConvertUtil.convertOfEntity(datasource, ProDatasource.class);
        datasourceCopy.setSchema("information_schema");
        SourceInfo sourceInfo = new SourceInfo(
                "com.mysql.cj.jdbc.Driver",
                getUrl(datasourceCopy),
                datasourceCopy.getAccount(),
                datasourceCopy.getPassword()
        );
        return datasourceService.link(sourceInfo);
    }

    /**
     * 获取指定库的所有表
     * @param datasource ProDatasource 数据源配置
     * @param tableNames List<String> 表名集合
     * @return List<ProTable>
     */
    @Override
    public List<ProTable> findTables(ProDatasource datasource, List<String> tableNames) {
        SqlSessionFactory sessionFactory = getSessionFactory(datasource);
        if(ObjectUtil.isNull(sessionFactory)){
            return new ArrayList<>();
        }
        SqlSession sqlSession = sessionFactory.openSession();
        MysqlTablesMapper tbMapper = sqlSession.getMapper(MysqlTablesMapper.class);

        LambdaQueryWrapper<MysqlTables> wrapper = new LambdaQueryWrapper<MysqlTables>().eq(MysqlTables::getTableSchema, datasource.getSchema());
        if(ObjectUtil.isNotEmpty(tableNames)){
            wrapper.in(MysqlTables::getTableName, tableNames);
        }
        List<MysqlTables> tablesList = tbMapper.selectList(wrapper);
        sqlSession.close();

        // 获取当前项目是扩展配置
        String tableExtent = proExtendService.getJsonForScopeWithProId(datasource.getProjectId(), ProExtendScopeEnum.PES_3);
        List<ProTable> proTableList = new ArrayList<>();
        for(MysqlTables table:tablesList){
            ProTable proTable = ConvertUtil.convertOfEntity(table, ProTable.class);
            long tableId = snowFlakeIdGen.nextId();
            proTable.setId(tableId);
            proTable.setProjectId(datasource.getProjectId());
            proTable.setSourceId(datasource.getId());
            proTable.setInfixName(proTable.getTableName());
            proTable.setExtend(tableExtent);
            proTable.setExistFlag(true);
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

    /**
     * 获取指定库、表的所有列
     * @param datasource ProDatasource 数据源配置
     * @param proTableList List<ProTable> 表数据
     * @return List<ProField>
     */
    @Override
    public List<ProField> findColumns(ProDatasource datasource, List<ProTable> proTableList) {
        SqlSessionFactory sessionFactory = getSessionFactory(datasource);
        if(sessionFactory == null){
            return new ArrayList<>();
        }
        List<String> tableNames = proTableList.stream().map(ProTable::getTableName).collect(Collectors.toList());
        Map<String, Long> tableMap = proTableList.stream().collect(Collectors.toMap(ProTable::getTableName, ProTable::getId));

        SqlSession sqlSession = sessionFactory.openSession();
        MysqlColumnsMapper colMapper = sqlSession.getMapper(MysqlColumnsMapper.class);
        LambdaQueryWrapper<MysqlColumns> wrapper = new LambdaQueryWrapper<MysqlColumns>()
                .eq(MysqlColumns::getTableSchema, datasource.getSchema())
                .in(MysqlColumns::getTableName, tableNames);
        List<MysqlColumns> columnsList = colMapper.selectList(wrapper);
        sqlSession.close();

        // 获取当前项目是扩展配置
        String filedExtent = proExtendService.getJsonForScopeWithProId(datasource.getProjectId(), ProExtendScopeEnum.PES_4);

        List<ProField> proFieldList = new ArrayList<>();
        for(MysqlColumns column:columnsList){
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
            if(StrUtil.isNotBlank(proField.getColumnType())){
                proField.setUnsigned(proField.getColumnType().contains("unsigned"));
            }
            if(proField.getPrimary()){
                if("auto_increment".equals(proField.getExtra())){
                    proField.setIdType(IdType.AUTO.toString());
                }else {
                    proField.setIdType(IdType.ASSIGN_ID.toString());
                }
            }
            setBounds(proField);
            proFieldList.add(proField);
        }


        return proFieldList;
    }

    /**
     * 设置字段边界值
     * @param proField
     */
    @Override
    public void setBounds(ProField proField){
        // 字符最大长度、数字精度均为空，不需要计算取值范围
        if(proField.getCharacterMaximumLength() == null && proField.getNumericPrecision() == null){
            return;
        }
        if (proField.getCharacterMaximumLength() != null) {
            // 字符串类型
            proField.setBoundMax(proField.getCharacterMaximumLength().toString());
        }else {
            // 数值类型
            String cType = proField.getColumnType();
            if(!cType.contains("(")){
                return;
            }
            // 获取数值精度、刻度
            String first,second;
            int precision,scale = 0;
            if (cType.contains(",")){
                first = cType.substring(cType.indexOf("(") + 1, cType.indexOf(","));
                second = cType.substring(cType.indexOf(",") + 1, cType.indexOf(")"));
                scale = Integer.parseInt(second);
            }else {
                first = cType.substring(cType.indexOf("(") + 1, cType.indexOf(")"));
            }
            precision = Integer.parseInt(first);

            // 计算数值极限值
            DataTool.setBoundsForNum(proField, precision, scale);
        }
    }
}
