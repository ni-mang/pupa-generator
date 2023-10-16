package com.nimang.pupa.base.model.proExtend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 扩展-说明展示VO
 * @author JustHuman
 * @date 2023-04-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProExtendShowVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 键
     */
    @NotBlank
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 名称
     */
    @NotBlank
    private String name;

    /**
     * 释义
     */
    private String comments;

    /**
     * 获取系统内置扩展信息
     * @return
     */
    public static List<ProExtendShowVO> getDefaultExtend(){
        List<ProExtendShowVO> list = new ArrayList<>();
//        list.add(new ProExtendShowVO("a", "a", "a", "a"));
//        list.add(new ProExtendShowVO("b", "b", "b", "b"));
        return list;
    }

}