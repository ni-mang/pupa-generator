package com.nimang.pupa.dbExtends;

import cn.hutool.core.util.StrUtil;
import com.nimang.pupa.base.entity.ProTable;

public class DataTool {
    private static final String FILED_NAME_START = "[";
    private static final String FILED_NAME_END = "]";
    private static final String ENUM_START = "{";
    private static final String ENUM_END = "}";
    public static final String SP_STR = "/";
    /**
     * 获取注释中的字段中文名
     * @param comments
     * @return
     */
    public static String getDesc(String comments) {
        if(StrUtil.isBlank(comments)){
            return "";
        }
        if (comments.contains(FILED_NAME_START) && comments.contains(FILED_NAME_END)){
            return comments.substring(comments.indexOf(FILED_NAME_START) + 1,comments.indexOf(FILED_NAME_END));
        }else {
            return comments;
        }
    }

    /**
     * 获取注释中的枚举名
     * @param comments
     * @return
     */
    public static String getEnum(String comments) {
        if(StrUtil.isBlank(comments)){
            return "";
        }
        if (comments.contains(ENUM_START) && comments.contains(ENUM_END)){
            return comments.substring(comments.indexOf(ENUM_START) + 1,comments.indexOf(ENUM_END));
        }else {
            return "";
        }
    }

    /**
     * 获取注释中的额外注释
     * @param comments
     * @return
     */
    public static String getNotes(String comments) {
        if(StrUtil.isBlank(comments)){
            return "";
        }
        String notes = comments;
        if (notes.contains(FILED_NAME_START) && notes.contains(FILED_NAME_END)){
            notes = notes.substring(notes.indexOf(FILED_NAME_END) + 1);
        }
        if (notes.contains(ENUM_START) && notes.contains(ENUM_END)){
            notes = notes.substring(notes.indexOf(ENUM_END) + 1);
        }
        if (notes.equals(comments)){
            notes = "";
        }
        return notes;
    }

    public static void setModule(ProTable proTable) {
        String comment = proTable.getTableComment();
        if(comment.contains(SP_STR)){
            proTable.setModule(comment.substring(0, comment.lastIndexOf(SP_STR)));
            proTable.setCnName(comment.substring(comment.lastIndexOf(SP_STR)+1));
        }else {
            proTable.setModule("");
            proTable.setCnName(comment);
        }
    }
}
