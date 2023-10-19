package com.nimang.pupa.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.nimang.pupa.common.handler.MyMetaObjectHandler;
import com.nimang.pupa.common.interceptor.MyPaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis 配置
 */
@EnableTransactionManagement(
        proxyTargetClass = true
)
@Configuration
public class MybatisPlusConfig {

    public MybatisPlusConfig() {
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(this.paginationInnerInterceptor());
        interceptor.addInnerInterceptor(this.optimisticLockerInnerInterceptor());
        return interceptor;
    }

    public MyPaginationInterceptor paginationInnerInterceptor() {
        MyPaginationInterceptor myPaginationInterceptor = new MyPaginationInterceptor();
        myPaginationInterceptor.setDbType(DbType.MYSQL);
        myPaginationInterceptor.setOverflow(true);
        myPaginationInterceptor.setMaxLimit(-1L);
        return myPaginationInterceptor;
    }

    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MyMetaObjectHandler();
    }

}
