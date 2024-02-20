package com.nimang.pupa.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 穿梭框数据VO
 * @author JustHuman
 * @date 2024-02-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 选项数据
     */
    private List<transferData> data;

    /**
     * 选中ID
     */
    private List<String> value;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class transferData {
        /**
         * 值
         */
        private String value;

        /**
         * 名称
         */
        private String title;

        /**
         * 是否禁用
         */
        private Boolean disabled;
    }

}