package com.nimang.pupa.common.util;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class OldConvertUtil {

    public <T> T convert(Class<T> tClass){
        return convertOfEntity(this,tClass);
    }

    /**
     * 将对象A的属性值拷贝给对象B
     * @param source
     * @param target
     * @return
     * @param <T>
     */
    public static <T> T convertOfEntity(Object source, T target){
        if(source != null && target!=null){
            try {
                //拷贝对象，忽略空值
                BeanUtil.copyProperties(source,target,CopyOptions.create().setIgnoreNullValue(true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return target;
    }

    /**
     * 通过class生成对象B，并将对象A的属性值复制给对象B
     * @param source
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> T convertOfEntity(Object source, Class<T> tClass){
        T target = null;
        if(source != null){
            try {
                target = tClass.getConstructor().newInstance();
                //拷贝对象，忽略空值
                BeanUtil.copyProperties(source,target,CopyOptions.create().setIgnoreNullValue(true));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return target;
    }

    /**
     * 批量进行对象属性赋值拷贝
     * @param sources
     * @param tClass
     * @return
     * @param <T>
     * @param <O>
     */
    public static  <T,O> List<T> convertOfAll(Iterable<O> sources, Class<T> tClass){
        List<T> targets = new ArrayList<>();
        for(O source:sources){
            targets.add(convertOfEntity(source,tClass));
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
}
