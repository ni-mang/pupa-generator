package com.nimang.pupa.dbExtends;

import com.nimang.pupa.common.enums.interfaces.EnumInterface;
import com.nimang.pupa.common.tool.enumsTool.EnumTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 数据库配置
 */

@Getter
@AllArgsConstructor
@EnumTag(label = "datasource_brand")
public enum DatasourceBrandEnum implements EnumInterface {

    PDB_MYSQL(0, "Mysql"),
    PDB_MARIADB(1, "MariaDB"),
    PDB_SQL_SERVER(2, "SqlServer"),
    PDB_ORACLE(3, "Oracle"),
    ;

    private Integer code;

    private String msg;


    /**
     * 根据code获取枚举对象
     * @param code
     * @return
     */
    public static DatasourceBrandEnum getByCode(Integer code){
        return Stream.of(DatasourceBrandEnum.values()).filter(it -> it.getCode().equals(code)).findAny().orElse(null);
    }

    /**
     * 根据code获取枚举说明
     * @param code
     * @return
     */
    public static String getMsgByCode(Integer code){
        DatasourceBrandEnum findEnum = getByCode(code);
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
