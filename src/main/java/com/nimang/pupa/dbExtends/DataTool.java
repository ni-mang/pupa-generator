package com.nimang.pupa.dbExtends;

import cn.hutool.core.util.StrUtil;
import com.nimang.pupa.base.entity.ProField;
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

    /**
     * 设置模块信息
     * @param proTable
     */
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

    /**
     * 设置数值类型字段的取值范围
     * @param proField
     * @param precision
     * @param scale
     */
    public static void setBoundsForNum(ProField proField, Integer precision, Integer scale){
        precision = Math.min(precision, DBConstants.PRECISION_LIMIT_FLOAT);
        StringBuilder max = new StringBuilder();
        StringBuilder min = new StringBuilder();
        if(!proField.getUnsigned()){
            min = new StringBuilder("-");
        }
        if (scale > 0) {
            // 浮点数
            precision = Math.min(precision, DBConstants.PRECISION_LIMIT_FLOAT);
            for(int i=1;i<=precision;i++){
                if(proField.getUnsigned()){
                    if(i>=precision-scale){
                        min.append("0");
                    }
                }else {
                    min.append("9");
                }
                max.append("9");
                if(i==precision-scale){
                    min.append(".");
                    max.append(".");
                }
            }
        } else {
            // 整型
            precision = Math.min(precision, DBConstants.PRECISION_LIMIT);
            for(int i=1;i<=precision;i++){
                max.append("9");
            }
            if(proField.getUnsigned()){
                min.append("0");
            }else {
                min.append(max);
            }
        }
        proField.setBoundMin(min.toString());
        proField.setBoundMax(max.toString());
    }
}
