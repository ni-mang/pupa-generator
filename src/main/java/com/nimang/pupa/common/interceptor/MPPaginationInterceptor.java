package com.nimang.pupa.common.interceptor;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.AllArgsConstructor;

public class MPPaginationInterceptor extends PaginationInnerInterceptor {
    public MPPaginationInterceptor(DbType dbType) {
        super(dbType);
    }

    @Override
    protected void handlerOverflow(IPage<?> page) {
        //原来的逻辑是超出范围返回第一页
        //page.setCurrent(1L);

        //修改成返回最后一页
        page.setCurrent(page.getPages());
    }
}
