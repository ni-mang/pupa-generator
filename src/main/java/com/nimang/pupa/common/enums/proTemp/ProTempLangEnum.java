package com.nimang.pupa.common.enums.proTemp;

import com.nimang.pupa.common.enums.interfaces.EnumStrInterface;
import com.nimang.pupa.common.tool.enumsTool.EnumTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 模板语言类型
 */

@Getter
@AllArgsConstructor
@EnumTag(label = "temp_lang")
public enum ProTempLangEnum implements EnumStrInterface {

    JAVA("java", "java"),
    XML("xml", "xml"),
    HTML("html", "html"),
    JAVASCRIPT("javascript", "javascript"),
    CSS("css", "css"),
    PHP("php", "php"),
    GO("go", "go"),
    PYTHON("python", "python"),
    MYSQL("mysql", "mysql"),
    YAML("yaml", "yaml"),
    ;

    private String code;

    private String msg;

    /**
     * 根据code获取枚举对象
     * @param code
     * @return
     */
    public static ProTempLangEnum getByCode(String code){
        return Stream.of(ProTempLangEnum.values()).filter(it -> it.getCode().equals(code)).findAny().orElse(null);
    }

    /**
     * 根据code获取枚举说明
     * @param code
     * @return
     */
    public static String getMsgByCode(String code){
        ProTempLangEnum findEnum = getByCode(code);
        return findEnum==null?"":findEnum.getMsg();
    }
}
