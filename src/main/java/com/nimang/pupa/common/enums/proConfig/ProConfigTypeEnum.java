package com.nimang.pupa.common.enums.proConfig;

import com.nimang.pupa.common.enums.interfaces.EnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 配置类型
 */

@Getter
@AllArgsConstructor
public enum ProConfigTypeEnum implements EnumInterface {

//    0:公共,1:私人
    PCT_0(0, "公共"),
    PCT_1(1, "私有"),
    ;

    private Integer code;

    private String msg;

    /**
     * 根据code获取枚举对象
     * @param code
     * @return
     */
    public static ProConfigTypeEnum getByCode(Integer code){
        return Stream.of(ProConfigTypeEnum.values()).filter(it -> it.getCode().equals(code)).findAny().orElse(null);
    }

    /**
     * 根据code获取枚举说明
     * @param code
     * @return
     */
    public static String getMsgByCode(Integer code){
        ProConfigTypeEnum findEnum = getByCode(code);
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
