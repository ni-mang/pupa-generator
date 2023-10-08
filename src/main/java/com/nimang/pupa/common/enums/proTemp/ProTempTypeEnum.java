package com.nimang.pupa.common.enums.proTemp;

import com.nimang.pupa.common.enums.interfaces.EnumInterface;
import com.nimang.pupa.common.tool.enumsTool.EnumTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 模板引擎类型
 */

@Getter
@AllArgsConstructor
@EnumTag(label = "temp_type")
public enum ProTempTypeEnum implements EnumInterface {

    PTT_0(0, "Enjoy"),//(5.0.3)
    PTT_1(1, "Freemarker"),//(2.3.32)
    PTT_2(2, "Velocity"),//(1.7)
    ;

    private Integer code;

    private String msg;

    /**
     * 根据code获取枚举对象
     * @param code
     * @return
     */
    public static ProTempTypeEnum getByCode(Integer code){
        return Stream.of(ProTempTypeEnum.values()).filter(it -> it.getCode().equals(code)).findAny().orElse(null);
    }

    /**
     * 根据code获取枚举说明
     * @param code
     * @return
     */
    public static String getMsgByCode(Integer code){
        ProTempTypeEnum findEnum = getByCode(code);
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
