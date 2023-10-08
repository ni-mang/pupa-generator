package com.nimang.pupa.common.tool.mp.annotation;

import java.lang.annotation.*;

/**
 * 查询规则-忽略字段
 * @date  2023-02-22
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MPIgnore {


}
