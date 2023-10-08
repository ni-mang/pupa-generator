package com.nimang.pupa.common.tool.enumsTool;

import cn.hutool.core.util.StrUtil;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 枚举工具类
 * @date 2023-02-17
 */

@Component
public class EnumUtil {

    @Value("${spring.package.base:}")
    private String basePath;

    @Value("${spring.package.enum:}")
    private String enumPath;

    private Set<Class<?>> enums;

    @PostConstruct
    public void scanEnums() {
        String enumPackage = StrUtil.isBlank(enumPath) ? StrUtil.isBlank(basePath) ? "" : basePath : StrUtil.isBlank(basePath) ? enumPath : basePath + "." + enumPath;
        Reflections f = new Reflections(enumPackage);
        //扫描指定包下所有使用@EnumTag注解的类，未指定包则扫描当前项目目录
        enums = f.getTypesAnnotatedWith(EnumTag.class);
    }

    /**
     * 通过反射机制获取枚举信息
     * @param tagLabel 标签
     * @param sort 排序（V0：按值正序，V1：按值逆序，D0：按注释正序，D1：按注释逆序）
     * @return
     */
    public List<EnumVO> getEnums(String tagLabel, String sort) {
        List<EnumVO> result = new ArrayList<>();
        if (null == enums || enums.isEmpty()) {
            return result;
        }
        if(tagLabel == null || tagLabel.length()<1){
            return result;
        }
        try {
            for (Class<?> clazz : enums) {
                EnumTag enumTag = clazz.getAnnotation(EnumTag.class);
                String label = enumTag.label();
                String field = enumTag.field();
                String note = enumTag.note();
                if(!tagLabel.equals(label)){
                    continue;
                }
                Object[] enumConstants = clazz.getEnumConstants();
                for (Object enumConstant : enumConstants) {
                    Method evfMethod = enumConstant.getClass().getMethod(MessageFormat.format("get{0}", StrUtil.upperFirst(field)));
                    Object evf = evfMethod.invoke(enumConstant, null);
                    Method enfMethod = enumConstant.getClass().getMethod(MessageFormat.format("get{0}", StrUtil.upperFirst(note)));
                    Object enf = enfMethod.invoke(enumConstant, null);

                    EnumVO enumVO = new EnumVO();
                    enumVO.setValue(evf.toString());
                    enumVO.setDesc(enf.toString());
                    result.add(enumVO);
                }
            }
            switch (sort){
                case "V0":
                    result = result.stream().sorted(Comparator.comparing(EnumVO::getValue)).collect(Collectors.toList());
                    break;
                case "V1":
                    result = result.stream().sorted(Comparator.comparing(EnumVO::getValue).reversed()).collect(Collectors.toList());
                    break;
                case "D0":
                    result = result.stream().sorted(Comparator.comparing(EnumVO::getDesc)).collect(Collectors.toList());
                    break;
                case "D1":
                    result = result.stream().sorted(Comparator.comparing(EnumVO::getDesc).reversed()).collect(Collectors.toList());
                    break;
            }
            return result;
        } catch (Exception e) {
            return result;
        }
    }
}
