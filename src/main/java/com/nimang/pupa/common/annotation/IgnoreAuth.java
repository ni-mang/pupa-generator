package com.nimang.pupa.common.annotation;

import java.lang.annotation.*;

/**
 * 忽略授权
 */
@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreAuth {

}
