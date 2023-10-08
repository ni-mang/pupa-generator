package com.nimang.pupa.common.tool.enumsTool;

import java.lang.annotation.*;

/**
 * 枚举类标识，用于根据label获取枚举类信息
 * @date  2023-02-17
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumTag {

    String label() default ""; // 标签
    String field() default "code"; // 值字段,默认取getCode方法的值
    String note() default "msg"; // 注释字段,默认取getMsg方法的值

}
