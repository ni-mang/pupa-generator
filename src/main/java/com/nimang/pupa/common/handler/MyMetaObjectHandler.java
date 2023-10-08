package com.nimang.pupa.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.nimang.pupa.common.util.UserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("createTime") && metaObject.getValue("createTime") == null) {
            if("java.util.Date".equals(metaObject.getGetterType("createTime").getName())){
                this.setFieldValByName("createTime", new Date(), metaObject);
            }else{
                this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
            }
        }
        this.setFieldValByName("optLock", 1L, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("updateTime")){
            if("java.util.Date".equals(metaObject.getGetterType("updateTime").getName())){
                this.setFieldValByName("updateTime", new Date(), metaObject);
            }else{
                this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
            }
        }
    }
}
