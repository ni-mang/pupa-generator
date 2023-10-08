layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/pro/field/add");
    $("#reqType").val("POST");
    $("#tableName").val("fieldTable");

    let configId = $("#configId").val();

    // 获取扩展数据并渲染
    renderExtend(table,'#extendDiv',configId,4);

    form.on('radio(primary)', function (obj) {
        let val = obj.value;
        if(val === "true"){
            $("#idTypeDiv").removeClass("layui-hide")
        }else {
            $("#idTypeDiv").addClass("layui-hide")
        }
    });

    // 触发提交运行扩展函数-获取扩展数据、类型映射数据
    window.extendFun = function(data){
        data["extendList"] = table.cache["extendDiv"];
    }


});