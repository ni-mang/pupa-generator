package com.nimang.pupa.common.tool.typeFun;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * 函数式接口 -  反射 Lambda获取属性值
 *
 * @param <T>
 * @param <R>
 * @data 2023-03-03
 */
@FunctionalInterface
public interface TypeFunction<T, R> extends Serializable, Function<T, R> {

    /**
     * 获取字段名称
     * @param serializedLambda
     * @return String
     */
    static String getLambdaFieldName(SerializedLambda serializedLambda) {
        String getter = serializedLambda.getImplMethodName();
        return Introspector.decapitalize(getter.replace("get", ""));
    }

    /**
     * 获取列名称
     * @param serializedLambda
     * @return String
     */
    static String getLambdaColumnName(SerializedLambda serializedLambda) {
        String column = null;
        String fieldName = getLambdaFieldName(serializedLambda);
        Field field;
        try {
            Class<?> pClass= Class.forName(serializedLambda.getImplClass().replace("/","."));
            field = pClass.getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        TableId tableId = field.getAnnotation(TableId.class);
        if(tableId == null){
            TableField tableField = field.getAnnotation(TableField.class);
            if(tableField != null){
                column = tableField.value();
            }
        }else {
            column = tableId.value();
        }
        return column;
    }
    static SerializedLambda getSerializedLambda(Serializable lambda) {
        try {
            Method method = lambda.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            return (SerializedLambda) method.invoke(lambda);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
