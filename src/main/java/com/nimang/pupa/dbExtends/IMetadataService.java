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
     * 获取数据库链接
     * @param datasource ProDatasource
     * @return String
     */
    String getUrl(ProDatasource datasource);

    /**
     * 获取数据库连接SessionFactory
     * @param datasource ProDatasource 数据源配置
     * @return SessionFactory
     */
    SqlSessionFactory getSessionFactory(ProDatasource datasource);

    /**
     * 获取指定库的所有表
     * @param datasource ProDatasource 数据源配置
     * @param tableNames List<String> 表名集合
     * @return List<ProTable>
     */
    List<ProTable> findTables(ProDatasource datasource, List<String> tableNames);

    /**
     * 获取指定库、表的所有列
     * @param datasource ProDatasource 数据源配置
     * @param proTableList List<ProTable> 表数据
     * @return List<ProField>
     */
    List<ProField> findColumns(ProDatasource datasource, List<ProTable> proTableList);

    /**
     * 设置字段边界值
     * @param proField 表字段
     */
    void setBounds(ProField proField);

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
