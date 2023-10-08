package com.nimang.pupa.common.enums;

import com.nimang.pupa.common.enums.interfaces.EnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 启用状态
 */

@Getter
@AllArgsConstructor
public enum StatusEnum implements EnumInterface {

    STATUS_1(1, "启用"),
    STATUS_0(0, "禁用"),

    ;

    private Integer code;

    private String msg;

    /**
     * 根据code获取枚举对象
     * @param code
     * @return
     */
    public static StatusEnum getByCode(Integer code){
        return Stream.of(StatusEnum.values()).filter(it -> it.getCode().equals(code)).findAny().orElse(null);
    }

    /**
     * 根据code获取枚举说明
     * @param code
     * @return
     */
    public static String getMsgByCode(Integer code){
        StatusEnum findEnum = getByCode(code);
        return findEnum==null?"":findEnum.getMsg();
    }

    /**
     * 比较值是否相同
     * @param code
     * @return
     */
    public boolean equals(Integer code) {
        return this.code.equals(code);
    }
}
