package com.nimang.pupa.common.tool.typeFun;

import java.lang.invoke.SerializedLambda;

/**
 * 通过Lambda获取字段名、列名
 * 例：User::getUserId -> userId | user_id
 * @data 2023-03-03
 */
public class LambdaUtils {

    public static <T, R> String getFieldName(TypeFunction<T, R> typeFunction) {
        SerializedLambda serializedLambda = TypeFunction.getSerializedLambda(typeFunction);
        return TypeFunction.getLambdaFieldName(serializedLambda);
    }

    public static <T, R> String getColumnName(TypeFunction<T, R> typeFunction) {
        SerializedLambda serializedLambda = TypeFunction.getSerializedLambda(typeFunction);
        return TypeFunction.getLambdaColumnName(serializedLambda);
    }
}
