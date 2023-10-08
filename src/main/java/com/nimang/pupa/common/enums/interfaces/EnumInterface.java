package com.nimang.pupa.common.enums.interfaces;

public interface EnumInterface {
    Integer getCode();

    String getMsg();


    /**
     * 比较值是否相同
     * @param code
     * @return
     */
    default boolean equals(Integer code) {
        return this.getCode().equals(code);
    }

}
