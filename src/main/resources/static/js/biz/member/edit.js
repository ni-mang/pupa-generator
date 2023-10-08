layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/pro/project-user/edit");
    $("#reqType").val("PUT");
    $("#tableName").val("memberTable");

    let configId = $("#configId").val();
    // 填充表单数据
    let reqData = {id:$("#id").val()};
    let url = serverPath + "/pro/project-user/get";
    ajax(url,"GET",reqData,
        {
            successFn:function(res) {
                let data = res["data"]
                form.val('editForm', data);

                // 获取扩展数据并渲染
                $("#extend").val(JSON.stringify(data.extendList));
                renderExtend(table,'#extendDiv',configId,1, data.extendList);
            }
        });

    // 触发提交运行扩展函数-获取扩展数据
    window.extendFun = function(data){
        data["extendList"] = table.cache["extendDiv"];
    }

});