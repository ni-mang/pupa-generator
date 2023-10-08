package com.nimang.pupa.common.constants;

/**
 * 正则
 */
public class RegexpConstants {

    //大陆身份证号码规则
    public static final String IDENTITY_CARD = "^([1-6][1-9]|50)\\d{4}(18|19|20)\\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

    // 护照
    // 规则： 14/15开头 + 7位数字, G + 8位数字, P + 7位数字, S/D + 7或8位数字,等
    // 样本： 141234567, G12345678, P1234567
    public static final String PASSPORT = "^([a-zA-z]|[0-9]){5,17}$";

    // 港澳居民来往内地通行证
    // 规则： H/M + 10位或6位数字
    // 样本： H1234567890
    public static final String PERMIT_FOR_HK_MACAO = "^([A-Z]\\d{6,10}(\\(\\w{1}\\))?)$";

    // 台湾居民来往大陆通行证
    // 规则： 新版8位或18位数字， 旧版10位数字 + 英文字母
    // 样本： 12345678 或 1234567890B
    public static final String PERMIT_FOR_TAIWAN = "^\\d{8}|^[a-zA-Z0-9]{10}|^\\d{18}$";

    // 军官证
    // 规则： 军/兵/士/文/职/广/（其他中文） + "字第" + 4到8位字母或数字 + "号"
    // 样本： 军字第2001988号, 士字第P011816X号
    public static final String PERMIT_FOR_OFFICER = "^[\\u4E00-\\u9FA5](字第)([0-9a-zA-Z]{4,8})(号?)$";

    // 户口本
    // 规则： 15位数字, 18位数字, 17位数字 + X
    // 样本： 441421999707223115
    public static final String PERMIT_FOR_ = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";

    //手机号码规则
    public static final String MOBILE_NO = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$";

    //邮箱规则
    public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    //统一社会信用代码规则
    public static final String CREDIT_CODE = "[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}";

    //票据号码规则
    public static final String BILL_NO = "^[1-9]\\d{29}$";

    //弱密码规则
    //长度在6~16之间，只能包含字母、数字
    public static final String SIMPLE_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    //域名
    public static final String DOMAIN  = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";






}
