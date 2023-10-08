package com.nimang.pupa.dbExtends;

import com.nimang.pupa.base.entity.*;
import com.nimang.pupa.base.entity.ProDatasource;
import com.nimang.pupa.base.entity.ProField;
import com.nimang.pupa.base.entity.ProTable;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * 元数据处理
 */
public interface IMetadataService {
    /**
     * 数据库品牌
     * @param
     * @return String
     */
    DatasourceBrandEnum getBrand();

    /**
     * 获取指定库的所有表
     * @param sessionFactory SqlSessionFactory
     * @param datasource ProDatasource 数据源配置
     * @param tableNames List<String> 表名集合
     * @return List<ProTable>
     */
    List<ProTable> findTables(SqlSessionFactory sessionFactory, ProDatasource datasource, List<String> tableNames);

    /**
     * 获取指定库、表的所有列
     * @param sessionFactory SqlSessionFactory
     * @param datasource ProDatasource 数据源配置
     * @param proTableList List<ProTable> 表数据
     * @return List<ProField>
     */
    List<ProField> findColumns(SqlSessionFactory sessionFactory, ProDatasource datasource, List<ProTable> proTableList);

    /**
     * 是否匹配
     * @param brand
     * @return boolean
     */
    default boolean isMatch(Integer brand){
        if(brand ==null) return false;
        return getBrand().equals(brand);
    }
}
