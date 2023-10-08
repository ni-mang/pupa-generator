package com.nimang.pupa.common.constants;

public class ExceptionConstants {

    //********************* NOT_FIND *********************
    public static final String NOT_FIND_POINT_DATA = "未找到指定数据";

    //********************* EXISTED *********************
    public static final String EXISTED_ACCOUNT = "账户已存在";
    public static final String EXISTED_PHONE = "手机号已注册，请重新输入";
    public static final String EXISTED_TABLE = "该数据库中已存在同名表";
    public static final String EXISTED_FIELD = "该表中已存在同名字段";
    public static final String EXISTED_FOR_COPY = "已存在同名的拷贝： {0}";
    public static final String EXISTED_MAPPER = "已存在相同列名的映射";
    public static final String EXISTED_COLUMN = "已存在相同列名";


    //********************* LACK *********************
    public static final String LACK_AGY_CODE = "缺少平台识别代码";


    //********************* CANT *********************
    public static final String CANT_DEL_ACC_AUDIT = "当前账户状态不允许删除";


    //********************* OTHER *********************
    public static final String DATA_STATUS_ERROR = "{0}不符合要求，允许：{1}，当前：{2}";
    public static final String INVALID_PWD_CONFIRM = "两次输入的密码不一致，请重新输入";
    public static final String INVALID_ACC_PWD_ERROR = "用户名或密码输入错误，请重新输入";
    public static final String INVALID_PWD_ERROR = "原密码错误，请重新输入";
    public static final String INVALID_ACC_ERROR = "未找到此手机号匹配的账户";
    public static final String INVALID_PHONE_ERROR = "当前修改手机号为当前手机号，无需修改";
    public static final String INVALID_USER_STATUS_ERROR = "当前用户状态异常";
    public static final String INVALID_USER_AUTH = "无权限";
    public static final String INVALID_DATA_ERROR = "数据异常";
    public static final String INVALID_EXPORT = "导出失败";

    public static final String INVALID_FILE = "无效文件";



}
