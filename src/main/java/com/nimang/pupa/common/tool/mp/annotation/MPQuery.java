package com.nimang.pupa.common.tool.mp.annotation;



import com.nimang.pupa.common.tool.mp.enums.Compare;

import java.lang.annotation.*;

/**
 * 查询规则
 * @date  2023-02-22
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MPQuery {

    String[] column() default ""; // 对应数据库列名，不填默认为当前字段名转下划线形式
    Compare[] rule() default Compare.EQ; // 规则，默认为EQ
    String after() default ""; // rule为BETWEEN、NOT_BETWEEN时，需指定后置参数字段名，否则不生效
    Compare logic() default Compare.AND; // 存在多个列名情况下，可选择使用or模式或者and模式，默认为and模式

}
