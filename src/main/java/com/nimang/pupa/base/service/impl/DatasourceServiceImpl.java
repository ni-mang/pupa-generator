package com.nimang.pupa.base.service.impl;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.nimang.pupa.base.entity.SourceInfo;
import com.nimang.pupa.base.service.IDatasourceService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatasourceServiceImpl implements IDatasourceService {

    /**
     * 链接数据库
     * @param sourceInfo
     * @return
     */
    @Override
    public SqlSessionFactory link(SourceInfo sourceInfo) {
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(sourceInfo.getDriver());
        dataSource.setUrl(sourceInfo.getUrl());
        dataSource.setUsername(sourceInfo.getUsername());
        dataSource.setPassword(sourceInfo.getPassword());
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resource = resolver.getResources("classpath:/mapper/**.xml");
            MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
            sqlSessionFactoryBean.setMapperLocations(resource);
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setConfiguration(new MybatisConfiguration());
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
