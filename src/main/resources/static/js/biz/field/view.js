layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.$;

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
                form.val('viewForm', data);

                // 获取扩展数据并渲染
                renderExtend(table, '#extendDiv', configId, 4, data.extendList, true);
            }
        });
});