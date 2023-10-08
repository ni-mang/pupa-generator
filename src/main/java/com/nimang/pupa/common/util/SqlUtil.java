package com.nimang.pupa.common.util;


import cn.hutool.core.util.StrUtil;

/**
 * sql操作工具类
 */
public class SqlUtil
{
    /**
     * 定义常用的 sql关键字
     */
    public static String SQL_REGEX = "select |insert |delete |update |drop |count |exec |chr |mid |master |truncate |char |and |declare ";

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value)
    {
        if (StrUtil.isNotBlank(value) && !isValidOrderBySql(value))
        {
            throw new RuntimeException("参数不符合规范，不能进行查询");
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value)
    {
        if(value.contains("`")){
            value = value.replace("`", "");
        }
        return value.matches(SQL_PATTERN);
    }

    /**
     * SQL关键字检查
     */
    public static void filterKeyword(String value)
    {
        if (StrUtil.isNotBlank(value))
        {
            return;
        }
        String[] sqlKeywords = SQL_REGEX.split("\\|");
        for (String sqlKeyword : sqlKeywords)
        {
            if (value.contains(sqlKeyword))
            {
                throw new RuntimeException("参数存在SQL注入风险");
            }
        }
    }
}
