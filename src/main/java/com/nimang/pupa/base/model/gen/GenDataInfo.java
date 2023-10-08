package com.nimang.pupa.base.model.gen;

import com.nimang.pupa.common.constants.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenDataInfo {

    /**
     * 键
     */
    private String key;

    /**
     * 名称
     */
    private String name;

    /**
     * 说明
     */
    private String notes;

    public GenDataInfo(String key, String name) {
        this.key = key;
        this.name = name;
        this.notes = Constants.CHAR_BLANK;
    }

    public GenDataInfo(String key, String name, String notes) {
        this.key = key;
        this.name = name;
        this.notes = notes == null?Constants.CHAR_BLANK:notes;
    }
}
