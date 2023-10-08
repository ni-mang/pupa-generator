layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/pro/table/edit");
    $("#reqType").val("PUT");
    $("#tableName").val("dbTableTable");

    let configId = $("#configId").val();

    // 填充表单数据
    let reqData = {id:$("#id").val()};
    let url = serverPath + "/pro/table/get";
    ajax(url,"GET",reqData,
        {
            successFn:function(res) {
                let data = res["data"]
                form.val('editForm', data);

                // 获取扩展数据并渲染
                $("#extend").val(JSON.stringify(data.extendList));
                renderExtend(table,'#extendDiv',configId,3, data.extendList);
            }
        });

    // 触发提交运行扩展函数-获取扩展数据、类型映射数据
    window.extendFun = function(data){
        data["extendList"] = table.cache["extendDiv"];
    }

});