/**
 * 转换返回数据
 * @param res
 * @returns {{msg, code, data, count: *}}
 */
function parseData(res){ // res 即为原始返回的数据
    return {
        "code": res.code==200?0:500, // 解析接口状态
        "msg": res.msg, // 解析提示文本
        "count": res.total, // 解析数据长度
        "data": res.data // 解析数据列表
    };
}