layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/pro/field/edit");
    $("#reqType").val("PUT");
    $("#tableName").val("fieldTable");

    let configId = $("#configId").val();

    // 填充表单数据
    let reqData = {id:$("#id").val()};
    let url = serverPath + "/pro/field/get";
    ajax(url,"GET",reqData,
        {
            successFn:function(res) {
                let data = res["data"]
                process(data);
                idTypeHide(data.primary);
                form.val('editForm', data);

                // 获取扩展数据并渲染
                $("#extend").val(JSON.stringify(data.extendList));
                renderExtend(table,'#extendDiv',configId,4, data.extendList);
            }
        });

    form.on('radio(primary)', function (obj) {
        let val = obj.value;
        idTypeHide(val);
    });

    // 触发提交运行扩展函数-获取扩展数据、类型映射数据
    window.extendFun = function(data){
        data["extendList"] = table.cache["extendDiv"];
    }
});