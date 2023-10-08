package com.nimang.pupa.common.enums.user;

import com.nimang.pupa.common.enums.interfaces.EnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 用户类型
 */

@Getter
@AllArgsConstructor
public enum UserTypeEnum implements EnumInterface {

//    0:超级管理员,1:普通用户
    SUPER_0(0, "超级管理员"),
    ORDINARY_1(1, "普通用户"),
    ;

    private Integer code;

    private String msg;

    /**
     * 根据code获取枚举对象
     * @param code
     * @return
     */
    public static UserTypeEnum getByCode(Integer code){
        return Stream.of(UserTypeEnum.values()).filter(it -> it.getCode().equals(code)).findAny().orElse(null);
    }

    /**
     * 根据code获取枚举说明
     * @param code
     * @return
     */
    public static String getMsgByCode(Integer code){
        UserTypeEnum findEnum = getByCode(code);
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
