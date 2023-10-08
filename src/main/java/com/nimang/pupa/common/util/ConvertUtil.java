package com.nimang.pupa.common.util;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * LinLaichun
 * 2023-06-07
 */
@Log4j2
public class ConvertUtil {

    /**
     * 将源对象转为目标类型的对象-深拷贝
     * @param source 源
     * @param tClass 目标类型
     * @return
     * @param <T>
     */
    public static <T> T convertOfEntity(Object source, Class<T> tClass){
        return convertOfEntity(source, tClass, true, true, null);
    }

    /**
     * 将源对象填充至目标对象-深拷贝
     * @param source 源
     * @param target 目标
     * @return
     * @param <T>
     */
    public static <T> T convertOfEntity(Object source, T target){
        return convertOfEntity(source, target, true, true, null);
    }

    /**
     * 将源对象转为目标类型的对象-深拷贝
     * @param source 源
     * @param tClass 目标类型
     * @param ignoreFields 忽略字段
     * @return
     * @param <T>
     */
    public static <T> T convertOfEntity(Object source, Class<T> tClass, List<String> ignoreFields){
        return convertOfEntity(source, tClass, true, true, ignoreFields);
    }

    /**
     * 将源对象填充至目标对象-深拷贝
     * @param source 源
     * @param target 目标
     * @param ignoreFields 忽略字段
     * @return
     * @param <T>
     */
    public static <T> T convertOfEntity(Object source, T target, List<String> ignoreFields){
        return convertOfEntity(source, target, true, true, ignoreFields);
    }

    public static <T> T convertOfEntity(Object source, Class<T> tClass, Boolean deep){
        return convertOfEntity(source, tClass, deep, true, null);
    }

    public static <T> T convertOfEntity(Object source, T target, Boolean deep){
        return convertOfEntity(source, target, deep, true, null);
    }


    /**
     * 将源对象转为目标类型的对象
     * @param source 源
     * @param tClass 目标类型
     * @param deep 是否深拷贝（默认是）
     * @param nullIgnore 是否忽略空值（默认是）
     * @param ignoreFields 忽略字段
     * @return
     * @param <T>
     */
    public static <T> T convertOfEntity(Object source, Class<T> tClass, Boolean deep, Boolean nullIgnore, List<String> ignoreFields){
        T target = null;
        try {
            target = tClass.getConstructor().newInstance();
            convertOfEntity(source, target, deep, nullIgnore, ignoreFields);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }


    /**
     * 将源对象填充至目标对象
     * @param source 源
     * @param target 目标
     * @param deep 是否深拷贝（默认是）
     * @param nullIgnore 是否忽略空值（默认是）
     * @param ignoreFields 忽略字段
     * @return
     * @param <T>
     */
    public static <T> T convertOfEntity(Object source, T target, Boolean deep, Boolean nullIgnore, List<String> ignoreFields){
        if(source == null){
            return target;
        }
        // 是否深拷贝
        deep = deep == null || deep;
        // 是否忽略空值
        nullIgnore = nullIgnore == null || nullIgnore;
        // 是否过滤字段
        boolean check = ignoreFields != null && !ignoreFields.isEmpty();
        // 如需深拷贝，通过json与对象转换，生成全新对象，与源对象解除耦合
        if(deep){
            source = JSON.parseObject(JSON.toJSONString(source), (Type) source.getClass());
        }
        // 获取源对象字段map
        Map<String, Field> sourceFieldMap = getAllField(source.getClass()).stream().collect(Collectors.toMap(Field::getName, Function.identity()));
        // 获取目标对象的字段集合
        List<Field> allFields = getAllField(target.getClass());
        Field sourceField;
        Object val;
        for (Field field:allFields) {
            // 设置类的私有字段属性可访问.
            field.setAccessible(true);
            if(check && ignoreFields.contains(field.getName())){
                continue;
            }
//            if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
//                continue;
//            }
            sourceField = sourceFieldMap.get(field.getName());
            if(sourceField == null){
                continue;
            }
            sourceField.setAccessible(true);
            try {
                val = sourceField.get(source);
                if(val==null){
                    if(nullIgnore){
                        continue;
                    }
                }
                field.set(target, val);
            } catch (IllegalArgumentException e){
                log.error("---------------  类转换过程中，字段 " + field.getName() + " 类型不匹配: " + sourceField.getType().getName() + " ==> " + field.getType().getName());
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return target;
    }

    /**
     * 克隆对象
     * @param source
     * @return
     * @param <T>
     */
    public static <T> T clone(T source){
        if(source == null){
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(source), (Type) source.getClass());
    }


    /**
     * 批量进行对象属性赋值拷贝-深拷贝
     * @param sources 源
     * @param tClass 目标类型
     * @return
     * @param <T>
     * @param <O>
     */
    public static  <T,O> List<T> convertOfAll(Iterable<O> sources, Class<T> tClass){
        return convertOfAll(sources, tClass, true, true, null);
    }

    /**
     * 批量进行对象属性赋值拷贝-深拷贝
     * @param sources 源
     * @param tClass 目标类型
     * @param ignoreFields 忽略字段
     * @return
     * @param <T>
     * @param <O>
     */
    public static  <T,O> List<T> convertOfAll(Iterable<O> sources, Class<T> tClass, List<String> ignoreFields){
        return convertOfAll(sources, tClass, true, true, ignoreFields);
    }

    public static  <T,O> List<T> convertOfAll(Iterable<O> sources, Class<T> tClass, Boolean deep){
        return convertOfAll(sources, tClass, deep, true, null);
    }

    /**
     * 批量进行对象属性赋值拷贝
     * @param sources 源
     * @param tClass 目标类型
     * @param deep 是否深拷贝（默认是）
     * @param nullIgnore 是否忽略空值（默认是）
     * @param ignoreFields 忽略字段
     * @return
     * @param <T>
     * @param <O>
     */
    public static  <T,O> List<T> convertOfAll(Iterable<O> sources, Class<T> tClass, Boolean deep, Boolean nullIgnore, List<String> ignoreFields){
        List<T> targets = new ArrayList<>();
        for(O source:sources){
            targets.add(convertOfEntity(source,tClass, deep, nullIgnore, ignoreFields));
        }
        return targets;
    }

    /**
     * 分页对象转换
     * @param page 源查询结果
     * @param function 对结果集进行转换的函数
     * @return
     * @param <T>
     * @param <O>
     */
    public static  <T,O> Page<T> convertOfPage(Page<O> page, Function<List<O>,List<T>> function){
        Page<T> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
        List<T> t = function.apply(page.getRecords());
        tPage.setRecords(t);
        return tPage;
    }

    /**
     * 获取类的所有属性（含父类）
     * @param clazz
     * @return
     */
    private static List<Field> getAllField(Class clazz){
        List<Field> fields = new ArrayList<>();
        while (clazz!=null){
            for(Field field:clazz.getDeclaredFields()){
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                fields.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
