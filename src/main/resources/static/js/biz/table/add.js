layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/pro/table/add");
    $("#reqType").val("POST");
    $("#tableName").val("dbTableTable");

    let configId = $("#configId").val();

    // 获取扩展数据并渲染
    renderExtend(table,'#extendDiv',configId,3);

    // 触发提交运行扩展函数-获取扩展数据、类型映射数据
    window.extendFun = function(data){
        data["extendList"] = table.cache["extendDiv"];
    }
});