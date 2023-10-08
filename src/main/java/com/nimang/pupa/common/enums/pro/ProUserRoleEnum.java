package com.nimang.pupa.common.enums.pro;

import com.nimang.pupa.common.enums.interfaces.EnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 项目成员角色
 */

@Getter
@AllArgsConstructor
public enum ProUserRoleEnum implements EnumInterface {

    // 0:所有者,1:普通成员
    PUR_0(0, "所有者"),
    PUR_1(1, "普通成员"),
    ;

    private Integer code;

    private String msg;

    /**
     * 根据code获取枚举对象
     * @param code
     * @return
     */
    public static ProUserRoleEnum getByCode(Integer code){
        return Stream.of(ProUserRoleEnum.values()).filter(it -> it.getCode().equals(code)).findAny().orElse(null);
    }

    /**
     * 根据code获取枚举说明
     * @param code
     * @return
     */
    public static String getMsgByCode(Integer code){
        ProUserRoleEnum findEnum = getByCode(code);
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
