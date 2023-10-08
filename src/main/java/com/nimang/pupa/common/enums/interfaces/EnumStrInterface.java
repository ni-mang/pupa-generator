package com.nimang.pupa.common.enums.interfaces;

public interface EnumStrInterface {
    String getCode();

    String getMsg();

    /**
     * 比较值是否相同
     * @param code
     * @return
     */
    default boolean equals(String code) {
        return this.getCode().equals(code);
    }
}
