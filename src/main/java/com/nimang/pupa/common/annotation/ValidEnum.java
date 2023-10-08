package com.nimang.pupa.common.annotation;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

/**
 * 判断取值是否为枚举类内的值
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidEnum.Validator.class)
@ConstraintComposition(CompositionType.AND)
public @interface ValidEnum {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Enum<?>> enumClass(); // 枚举类
    boolean allowNull() default false; // 是否允许为空
    String message() default "不在枚举类范围内"; // 错误信息
    String field() default "code"; // 校验值字段,默认取getCode方法的值

    class Validator implements ConstraintValidator<ValidEnum, Object> {
        private Class<? extends Enum<?>> enumClass;
        private boolean allowNull;
        private String field;
        @Override
        public void initialize(ValidEnum enumValue) {
            enumClass = enumValue.enumClass();
            allowNull = enumValue.allowNull();
            field = enumValue.field();
        }

        @SneakyThrows
        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            // 是否允许为null 或者 “”
            if (value == null || value.equals("")) {
                return allowNull;
            }
            // 参照枚举类为空说明不校验直接返回true
            if (enumClass == null ) {
                return Boolean.TRUE;
            }

            //校验是否为枚举类中的值
            Boolean flag = false;
            Enum<?>[] enumConstants = enumClass.getEnumConstants();
            for (Enum<?> enumConstant : enumConstants) {
                Method method = null;
                try {
                    method = enumConstant.getClass().getMethod(MessageFormat.format("get{0}", StrUtil.upperFirst(field)));
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                Class<?> returnType = method.getReturnType();
                String s = value.getClass().toString();
                // 如果方法返回值不同，立即返回错误
                if (!returnType.toString().equals(s)){
                    return false;
                }
                // 如果有值相等则返回true
                Object status = null;
                try {
                    status = method.invoke(enumConstant, null);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                if (status.equals(value)){
                    flag = true;
                    return flag;
                }
            }
            return flag;
        }

    }

}

