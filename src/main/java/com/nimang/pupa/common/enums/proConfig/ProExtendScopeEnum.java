package com.nimang.pupa.common.enums.proConfig;

import com.nimang.pupa.common.enums.interfaces.EnumInterface;
import com.nimang.pupa.common.tool.enumsTool.EnumTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 作用域
 */

@Getter
@AllArgsConstructor
@EnumTag(label = "extend_scope")
public enum ProExtendScopeEnum implements EnumInterface {

    // 0:项目,1:项目成员,2:数据源,3:表单,4:字段,
    PES_0(0, "项目"),
    PES_1(1, "项目成员"),
    PES_2(2, "数据源"),
    PES_3(3, "表单"),
    PES_4(4, "字段"),
    ;

    private Integer code;

    private String msg;

    /**
     * 根据code获取枚举对象
     * @param code
     * @return
     */
    public static ProExtendScopeEnum getByCode(Integer code){
        return Stream.of(ProExtendScopeEnum.values()).filter(it -> it.getCode().equals(code)).findAny().orElse(null);
    }

    /**
     * 根据code获取枚举说明
     * @param code
     * @return
     */
    public static String getMsgByCode(Integer code){
        ProExtendScopeEnum findEnum = getByCode(code);
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
